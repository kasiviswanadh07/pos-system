package com.kasi.mapper;

import com.kasi.model.Refund;
import com.kasi.payload.dto.RefundDTO;

public class RefundMapper {
    public static RefundDTO toDTO(Refund refund) {

        return RefundDTO.builder()
                .id(refund.getId())
                .orderId(refund.getOrder().getId())
                .reason(refund.getReason())
                .amount(refund.getAmount())
                .cashierName(refund.getCashier().getFullname()).branchId(refund.getBranch().getId())
                .shiftReportId(refund.getShiftReport() != null ? refund.getShiftReport().getId() : null)
                .createdAt(refund.getCreatedAt())
                .build();
    }
}
