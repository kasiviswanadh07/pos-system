package com.kasi.service;

import com.kasi.payload.dto.ShiftReportDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ShiftReportService {
    ShiftReportDTO startShift() throws Exception;

    ShiftReportDTO endShift() throws Exception;

    ShiftReportDTO getShiftReportById(Long id) throws Exception;

    List<ShiftReportDTO> getAllShiftReports();

    List<ShiftReportDTO> getAllShiftReportsByBranchId(Long branchId);

    List<ShiftReportDTO> getAllShiftReportsByCashierId(Long cashierId);

    ShiftReportDTO getCurrentShiftProgress(Long cashierId) throws Exception;

    ShiftReportDTO getShiftReportsByCashierAndDate(Long cashierId, LocalDateTime dateTime) throws Exception;
}
