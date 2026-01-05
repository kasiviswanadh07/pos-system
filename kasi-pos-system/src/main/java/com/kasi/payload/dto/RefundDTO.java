package com.kasi.payload.dto;

import com.kasi.domain.PaymentType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RefundDTO {
    private Long id;
    private OrderDTO order;
    private Long orderId;
    private String reason;
    private Double amount;
    //    private ShiftReport shiftReport;
    private Long shiftReportId;
    private UserDTO cashier;
    private String cashierName;
    private BranchDTO branch;
    private Long branchId;
    private PaymentType paymentType;
    private LocalDateTime createdAt;

}
