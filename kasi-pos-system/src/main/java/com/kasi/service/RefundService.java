package com.kasi.service;

import com.kasi.model.Refund;
import com.kasi.payload.dto.RefundDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RefundService {
    RefundDTO createRefund(RefundDTO refund) throws Exception;

    List<RefundDTO> getAllRefunds();

    List<RefundDTO> getRefundByCashier(Long cashierId);

    List<RefundDTO> getRefundByBranch(Long branchId);

    List<RefundDTO> getRefundByShiftReport(Long shiftReportId);

    List<RefundDTO> getRefundByCashierAndDateRange(Long cashierId, LocalDateTime startDate,
                                                   LocalDateTime endDate);

    RefundDTO getRefundById(Long refundId) throws Exception;

    void deleteRefundById(Long refundId) throws Exception;
}
