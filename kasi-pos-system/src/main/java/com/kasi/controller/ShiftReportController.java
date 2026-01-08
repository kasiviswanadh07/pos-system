package com.kasi.controller;

import com.kasi.payload.dto.ShiftReportDTO;
import com.kasi.service.ShiftReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/shift-reports")
@RequiredArgsConstructor
public class ShiftReportController {
    private final ShiftReportService shiftReportService;

    @PostMapping()
    public ResponseEntity<ShiftReportDTO> startShift() throws Exception {
        return ResponseEntity.ok(shiftReportService.startShift());
    }

    @PatchMapping("/end")
    public ResponseEntity<ShiftReportDTO> endShift() throws Exception {
        return ResponseEntity.ok(shiftReportService.endShift());
    }

    @GetMapping("/current")
    public ResponseEntity<ShiftReportDTO> getCurrentShiftProgress() throws Exception {
        return ResponseEntity.ok(shiftReportService.getCurrentShiftProgress(null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShiftReportDTO> getShiftReportById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(shiftReportService.getShiftReportById(id));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<ShiftReportDTO>> getAllShiftReportsByBranchId(@PathVariable Long branchId) throws Exception {
        return ResponseEntity.ok(shiftReportService.getAllShiftReportsByBranchId(branchId));
    }

    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<ShiftReportDTO>> getAllShiftReportsByCashierId(@PathVariable Long cashierId) throws Exception {
        return ResponseEntity.ok(shiftReportService.getAllShiftReportsByCashierId(cashierId));
    }

    @GetMapping("/cashier/{cashierId}/by-date")
    public ResponseEntity<ShiftReportDTO> getShiftReportsByCashierAndDate(
            @PathVariable Long cashierId,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy", iso = DateTimeFormat.ISO.DATE) LocalDateTime date) throws Exception {
        return ResponseEntity.ok(shiftReportService.getShiftReportsByCashierAndDate(cashierId, date));
    }


}
