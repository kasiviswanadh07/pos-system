package com.kasi.controller;

import com.kasi.payload.dto.InventoryDTO;
import com.kasi.payload.response.ApiResponse;
import com.kasi.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/inventories")
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping()
    public ResponseEntity<InventoryDTO> createInventory(@RequestBody InventoryDTO inventoryDTO) throws Exception {
        return ResponseEntity.ok(inventoryService.createInventory(inventoryDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<InventoryDTO> updateInventory(@PathVariable Long id,
                                                        @RequestBody InventoryDTO inventoryDTO) throws Exception {
        return ResponseEntity.ok(inventoryService.updateInventory(id, inventoryDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteInventory(@PathVariable Long id) throws Exception {
        inventoryService.deleteInventory(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Inventory Deleted Successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<InventoryDTO> getInventoryById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(inventoryService.getInventoryById(id));
    }

    @GetMapping("branch/{branchId}/product/{productId}")
    public ResponseEntity<InventoryDTO> getInventoryByProductIdAndBranchId(@PathVariable Long productId,
                                                                           @PathVariable Long branchId) throws Exception {

        return ResponseEntity.ok(inventoryService.getInventoryByProductIdAndBranchId(productId, branchId));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<InventoryDTO>> getInventoryByBranchId(@PathVariable Long branchId) throws Exception {
        return ResponseEntity.ok(inventoryService.getAllInventoryByBranchId(branchId));
    }
}
