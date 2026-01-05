package com.kasi.controller;

import com.kasi.payload.dto.RefundDTO;
import com.kasi.payload.response.ApiResponse;
import com.kasi.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refunds")
public class RefundContoller {
    private final RefundService refundService;

    @PostMapping()
    public ResponseEntity<RefundDTO> createRefund(@RequestBody RefundDTO refundDTO) throws Exception {

        return ResponseEntity.ok(refundService.createRefund(refundDTO));
    }

    @GetMapping()
    public ResponseEntity<List<RefundDTO>> getAllRefunds() throws Exception {
        List<RefundDTO> refund = refundService.getAllRefunds();
        return ResponseEntity.ok(refund);
    }

    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<RefundDTO>> getRefundByCashier(@PathVariable Long cashierId) throws Exception {
        List<RefundDTO> refund = refundService.getRefundByCashier(cashierId);
        return ResponseEntity.ok(refund);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<RefundDTO>> getRefundByBranch(@PathVariable Long branchId) throws Exception {
        List<RefundDTO> refund = refundService.getRefundByBranch(branchId);
        return ResponseEntity.ok(refund);
    }

    @GetMapping("/shift/{shiftReportId}")
    public ResponseEntity<List<RefundDTO>> getRefundByShiftReport(@PathVariable Long shiftReportId) throws Exception {
        List<RefundDTO> refund = refundService.getRefundByShiftReport(shiftReportId);
        return ResponseEntity.ok(refund);
    }

    @GetMapping("/cashier/{cashierId}/range")
    public ResponseEntity<List<RefundDTO>> getRefundByCashierAndDateRange(@PathVariable Long cashierId,
                                                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) throws Exception {
        List<RefundDTO> refund = refundService.getRefundByCashierAndDateRange(cashierId, start, end);
        return ResponseEntity.ok(refund);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRefund(@PathVariable Long id) throws Exception {
        refundService.deleteRefundById(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Successfully deleted Refund");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{refundId}")
    public ResponseEntity<RefundDTO> getRefundById(@PathVariable Long refundId) throws Exception {
        RefundDTO refund = refundService.getRefundById(refundId);
        return ResponseEntity.ok(refund);
    }


}
