package com.kasi.service.impl;

import com.kasi.domain.OrderStatus;
import com.kasi.domain.PaymentType;
import com.kasi.mapper.OrderMapper;
import com.kasi.model.*;
import com.kasi.payload.dto.OrderDTO;
import com.kasi.repository.OrderItemRespository;
import com.kasi.repository.OrderRepository;
import com.kasi.repository.ProductRepository;
import com.kasi.service.OrderService;
import com.kasi.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductRepository productRepository;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) throws Exception {
        User cashier = userService.getCurrentUser();
        Branch branch = cashier.getBranch();
        if (branch == null) {
            throw new Exception("cashier branch Not Found");
        }
        Order order = Order.builder()
                .branch(branch)
                .cashier(cashier)
                .customer(orderDTO.getCustomer())
                .paymentType(orderDTO.getPaymentType())
                .build();
        List<OrderItem> orderItems = orderDTO.getItems()
                .stream().map(itemDto -> {
                    Product product = productRepository.findById(itemDto.getProductId()).orElseThrow(
                            () -> new EntityNotFoundException("Product Not Found"));
                    return OrderItem.builder()
                            .product(product)
                            .quantity(itemDto.getQuantity())
                            .price(product.getSellingPrice() * itemDto.getQuantity())
                            .order(order)
                            .build();
                }).toList();
        double total = orderItems.stream().mapToDouble(
                OrderItem::getPrice
        ).sum();
        order.setItems(orderItems);
        order.setTotalAmount(total);
        Order savedOrder = orderRepository.save(order);
        return OrderMapper.toDTO(savedOrder);
    }

    @Override
    public OrderDTO getOrderById(Long id) throws Exception {

        return orderRepository.findById(id)
                .map(OrderMapper::toDTO)
                .orElseThrow(
                        () -> new Exception("Order Not found"));
    }

    @Override
    public List<OrderDTO> getOrderByBranch(Long branchId, Long customerId,
                                           Long cashierId, PaymentType paymentType,
                                           OrderStatus orderStatus) {
        return orderRepository.findByBranchId(branchId).stream()
                .filter(order -> customerId == null || (order.getCustomer() != null
                        && order.getId().equals(customerId)))
                .filter(order -> cashierId == null || order.getCashier() != null
                        && order.getCashier().getId().equals(cashierId))
                .filter(order -> paymentType == null || order.getPaymentType() != null
                        && order.getPaymentType() == paymentType)
                .map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrderByCashierId(Long cashierId) {
        return orderRepository.findByCashierId(cashierId)
                .stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Long id) throws Exception {
        Order order = orderRepository.findById(id).orElseThrow(() -> new Exception("Id no found"));
        orderRepository.delete(order);
    }

    @Override
    public List<OrderDTO> getTodayOrdersByBranch(Long branchId) {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();
        return orderRepository.findByBranchIdAndCreatedAtBetween(branchId, start, end)
                .stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId)
                .stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getTop5RecentOrdersByBranchId(Long branchId) {
        return orderRepository.findTop5ByBranchIdOrderByCreatedAtDesc(branchId)
                .stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }
}
