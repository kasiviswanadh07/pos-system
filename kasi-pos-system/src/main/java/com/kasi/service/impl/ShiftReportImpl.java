package com.kasi.service.impl;

import com.kasi.domain.PaymentType;
import com.kasi.mapper.ShiftReportMapper;
import com.kasi.model.*;
import com.kasi.payload.dto.ShiftReportDTO;
import com.kasi.repository.OrderRepository;
import com.kasi.repository.RefundRepository;
import com.kasi.repository.ShiftReportRepository;
import com.kasi.repository.UserRepository;
import com.kasi.service.ShiftReportService;
import com.kasi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftReportImpl implements ShiftReportService {
    private final ShiftReportRepository shiftReportRepository;
    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public ShiftReportDTO startShift() throws Exception {
        User currentUser = userService.getCurrentUser();
        LocalDateTime shiftStart = LocalDateTime.now();
        LocalDateTime startOfDay = shiftStart.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = shiftStart.withHour(23).withMinute(59).withSecond(0);

        Optional<ShiftReport> existing = shiftReportRepository.findByCashierAndShiftStartBetween(
                currentUser, startOfDay, endOfDay);
        if (existing.isPresent()) {
            throw new Exception("Shift Already started today");
        }
        Branch branch = currentUser.getBranch();

        ShiftReport shiftReport = ShiftReport.builder()
                .cashier(currentUser)
                .shiftStart(shiftStart)
                .branch(branch)
                .build();
        ShiftReport savedReport = shiftReportRepository.save(shiftReport);
        return ShiftReportMapper.toDTO(savedReport);
    }

    @Override
    public ShiftReportDTO endShift() throws Exception {
        User currentUser = userService.getCurrentUser();
        ShiftReport shiftReport = shiftReportRepository.findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(currentUser)
                .orElseThrow(() -> new Exception("Shift Not found"));
        LocalDateTime shiftEnd = LocalDateTime.now();
        shiftReport.setShiftEnd(shiftEnd);
        List<Refund> refunds = refundRepository.findByCashierIdAndCreatedAtBetween(currentUser.getId(),
                shiftReport.getShiftStart(), shiftReport.getShiftEnd());
        double totalRefunds = refunds.stream().mapToDouble(refund ->
                refund.getAmount() != null ? refund.getAmount() : 0.0).sum();
        List<Order> orders = orderRepository.findByCashierAndCreatedAtBetween(currentUser
                , shiftReport.getShiftStart(), shiftReport.getShiftEnd());

        double totalSales = orders.stream().mapToDouble(Order::getTotalAmount).sum();
        int totalOrders = orders.size();
        double netSales = totalSales - totalRefunds;
        shiftReport.setTotalSales(totalSales);
        shiftReport.setTotalRefunds(totalRefunds);
        shiftReport.setTotalOrders(totalOrders);
        shiftReport.setNetSales(netSales);
        shiftReport.setRecentOrders(getRecentOrders(orders));
        shiftReport.setTopSellingProducts(getTopSellingProducts(orders));
        shiftReport.setPaymentSummaries(getPaymentSummaries(orders, totalSales));
        shiftReport.setRefunds(refunds);

        ShiftReport savedReport = shiftReportRepository.save(shiftReport);
        return ShiftReportMapper.toDTO(savedReport);
    }

    @Override
    public ShiftReportDTO getShiftReportById(Long id) throws Exception {
        return ShiftReportMapper.toDTO(shiftReportRepository.findById(id).orElseThrow(() ->
                new Exception("Shift Report Not Found with given ID " + id)));
    }

    @Override
    public List<ShiftReportDTO> getAllShiftReports() {
        List<ShiftReport> reports = shiftReportRepository.findAll();
        return reports.stream().map(ShiftReportMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShiftReportDTO> getAllShiftReportsByBranchId(Long branchId) {
        return shiftReportRepository.findByBranchId(branchId).stream().
                map(ShiftReportMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ShiftReportDTO> getAllShiftReportsByCashierId(Long cashierId) {

        return shiftReportRepository.findByCashierId(cashierId).stream().
                map(ShiftReportMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ShiftReportDTO getCurrentShiftProgress(Long cashierId) throws Exception {
        User user = userService.getCurrentUser();
        ShiftReport shiftReport = shiftReportRepository.findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(user)
                .orElseThrow(() -> new Exception("Could not found current shift process"));
        LocalDateTime now = LocalDateTime.now();
        List<Order> orders = orderRepository.findByCashierAndCreatedAtBetween
                (user, shiftReport.getShiftStart(), now);

        List<Refund> refunds = refundRepository.findByCashierIdAndCreatedAtBetween(user.getId(),
                shiftReport.getShiftStart(), now);

        double totalRefunds = refunds.stream().mapToDouble(refund ->
                refund.getAmount() != null ? refund.getAmount() : 0.0).sum();

        double totalSales = orders.stream().mapToDouble(Order::getTotalAmount).sum();

        int totalOrders = orders.size();

        double netSales = totalSales - totalRefunds;
        shiftReport.setTotalSales(totalSales);
        shiftReport.setTotalRefunds(totalRefunds);
        shiftReport.setTotalOrders(totalOrders);
        shiftReport.setNetSales(netSales);
        shiftReport.setRecentOrders(getRecentOrders(orders));
        shiftReport.setTopSellingProducts(getTopSellingProducts(orders));
        shiftReport.setPaymentSummaries(getPaymentSummaries(orders, totalSales));
        shiftReport.setRefunds(refunds);

        ShiftReport savedReport = shiftReportRepository.save(shiftReport);
        return ShiftReportMapper.toDTO(savedReport);
    }

    @Override
    public ShiftReportDTO getShiftReportsByCashierAndDate(Long cashierId, LocalDateTime dateTime) throws Exception {
        User cashier = userRepository.findById(cashierId).orElseThrow(() ->
                new Exception("Cashier Not found with given Id " + cashierId));
        LocalDateTime start = dateTime.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end = dateTime.withHour(23).withMinute(59).withSecond(59);
        ShiftReport report = shiftReportRepository.findByCashierAndShiftStartBetween(cashier, start, end)
                .orElseThrow(() -> new Exception("Shift Report not found for cashier"));
        return ShiftReportMapper.toDTO(report);
    }

    private List<Order> getRecentOrders(List<Order> orders) {
        return orders.stream().sorted(Comparator.comparing(Order::getCreatedAt).reversed())
                .limit(5).collect(Collectors.toList());
    }

    private List<PaymentSummary> getPaymentSummaries(List<Order> orders, double totalSales) {
        Map<PaymentType, List<Order>> grouped = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getPaymentType() != null ? order.getPaymentType() : PaymentType.CASH));
        List<PaymentSummary> summaries = new ArrayList<>();
        for (Map.Entry<PaymentType, List<Order>> entry : grouped.entrySet()) {
            double amount = entry.getValue().stream().mapToDouble(
                    Order::getTotalAmount).sum();
            int transactions = entry.getValue().size();
            double percentage = (amount / totalSales) * 100;

            PaymentSummary paymentSummary = new PaymentSummary();
            paymentSummary.setType(entry.getKey());
            paymentSummary.setTotalAmount(amount);
            paymentSummary.setTransactionCount(transactions);
            paymentSummary.setPercentage(percentage);
            summaries.add(paymentSummary);
        }
        return summaries;
    }

    private List<Product> getTopSellingProducts(List<Order> orders) {
        Map<Product, Integer> productSalesMap = new HashMap<>();
        for (Order order : orders) {
            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();
                productSalesMap.put(product, productSalesMap.getOrDefault(product, 0) + item.getQuantity());
            }
        }
        return productSalesMap.entrySet().stream().sorted((a, b)
                        -> b.getValue().compareTo(a.getValue()))
                .limit(5).map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}