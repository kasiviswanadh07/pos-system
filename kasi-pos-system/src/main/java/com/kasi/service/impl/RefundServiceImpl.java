package com.kasi.service.impl;

import com.kasi.mapper.RefundMapper;
import com.kasi.model.Branch;
import com.kasi.model.Order;
import com.kasi.model.Refund;
import com.kasi.model.User;
import com.kasi.payload.dto.RefundDTO;
import com.kasi.repository.OrderRepository;
import com.kasi.repository.RefundRepository;
import com.kasi.service.RefundService;
import com.kasi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final RefundRepository refundRepository;

    @Override
    public RefundDTO createRefund(RefundDTO refund) throws Exception {
        User cashier = userService.getCurrentUser();
        Order order = orderRepository.findById(refund.getOrderId()).orElseThrow(
                () -> new Exception("Order Not Found")
        );
        if (!refundRepository.findByOrderId(order.getId()).isEmpty()) {
            throw new Exception("Refund Already Placed for This order");
        }

        Branch branch = order.getBranch();
        Refund createdRefund = Refund.builder()
                .order(order)
                .cashier(cashier)
                .branch(branch)
                .reason(refund.getReason())
                .amount(refund.getAmount())
                .paymentType(order.getPaymentType())
                .createdAt(refund.getCreatedAt())
                .build();
        Refund saveRefund = refundRepository.save(createdRefund);
        return RefundMapper.toDTO(saveRefund);
    }


    @Override
    public List<RefundDTO> getAllRefunds() {
        return refundRepository.findAll()
                .stream().map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RefundDTO> getRefundByCashier(Long cashierId) {
        return refundRepository.findByCashierId(cashierId)
                .stream().map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RefundDTO> getRefundByBranch(Long branchId) {
        return refundRepository.findByBranchId(branchId)
                .stream().map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RefundDTO> getRefundByShiftReport(Long shiftReportId) {
        return refundRepository.findByShiftReportId(shiftReportId)
                .stream().map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RefundDTO> getRefundByCashierAndDateRange(Long cashierId, LocalDateTime startDate, LocalDateTime endDate) {

        return refundRepository.findByCashierIdAndCreatedAtBetween(cashierId, startDate, endDate)
                .stream().map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public RefundDTO getRefundById(Long refundId) throws Exception {
        return refundRepository.findById(refundId)
                .map(RefundMapper::toDTO).orElseThrow(() -> new Exception("Refund Not found"));
    }

    @Override
    public void deleteRefundById(Long refundId) throws Exception {
        this.getRefundById(refundId);
        refundRepository.deleteById(refundId);

    }
}
