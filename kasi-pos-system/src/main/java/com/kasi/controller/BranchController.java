package com.kasi.controller;

import com.kasi.payload.dto.BranchDTO;
import com.kasi.payload.response.ApiResponse;
import com.kasi.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branches")
public class BranchController {
    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<BranchDTO> createBranch(@RequestBody BranchDTO branchDTO) throws Exception {
        BranchDTO createdBranch = branchService.createBranch(branchDTO);
        return ResponseEntity.ok(createdBranch);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<BranchDTO>> getAllBranchesByStoreId(@PathVariable Long storeId) throws Exception {
        return ResponseEntity.ok(branchService.getAllBranchesByStoreId(storeId));
    }

    @GetMapping("{id}")
    public ResponseEntity<BranchDTO> getBranchById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(branchService.getBranchById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteBranch(@PathVariable Long id) throws Exception {
        branchService.deleteBranch(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Branch Delete Successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<BranchDTO> updateBranch(@PathVariable Long id, @RequestBody BranchDTO branchDTO) throws Exception {
        return ResponseEntity.ok(branchService.updateBranch(id, branchDTO, null));
    }
}
