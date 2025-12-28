package com.kasi.service.impl;

import com.kasi.mapper.InventoryMapper;
import com.kasi.model.Branch;
import com.kasi.model.Inventory;
import com.kasi.model.Product;
import com.kasi.payload.dto.InventoryDTO;
import com.kasi.repository.BranchRepository;
import com.kasi.repository.InventoryRespository;
import com.kasi.repository.ProductRepository;
import com.kasi.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final BranchRepository branchRepository;
    private final InventoryRespository inventoryRespository;
    private final ProductRepository productRepository;

    @Override
    public InventoryDTO createInventory(InventoryDTO inventoryDTO) throws Exception {
        Branch branch = branchRepository.findById(inventoryDTO.getBranchId()).orElseThrow(
                () -> new Exception("Branch Not Exist........"));
        Product product = productRepository.findById(inventoryDTO.getProductId())
                .orElseThrow(() -> new Exception("Product Not Exist"));
        Inventory inventory = inventoryRespository.save(InventoryMapper.toEntity(inventoryDTO, branch, product));

        return InventoryMapper.toDto(inventory);
    }

    @Override
    public InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO) throws Exception {
        Inventory inventory = inventoryRespository.findById(id).orElseThrow(
                () -> new Exception("Inventory Not found"));
        inventory.setQuantity(inventoryDTO.getQuantity());
        return InventoryMapper.toDto(inventoryRespository.save(inventory));
    }

    @Override
    public void deleteInventory(Long id) throws Exception {
        Inventory inventory = inventoryRespository.findById(id).orElseThrow(
                () -> new Exception("Inventory Not found"));
        inventoryRespository.delete(inventory);
    }

    @Override
    public InventoryDTO getInventoryById(Long id) throws Exception {
        Inventory inventory = inventoryRespository.findById(id).orElseThrow(
                () -> new Exception("Inventory Not found"));
        return InventoryMapper.toDto(inventory);
    }

    @Override
    public InventoryDTO getInventoryByProductIdAndBranchId(Long productId, Long branchId) throws Exception {
        Inventory inventory = inventoryRespository.findByProductIdAndBranchId(productId, branchId);

        return InventoryMapper.toDto(inventory);
    }

    @Override
    public List<InventoryDTO> getAllInventoryByBranchId(Long branchId) throws Exception {
        List<Inventory> inventories = inventoryRespository.findByBranchId(branchId);

        return inventories.stream().map(
                InventoryMapper::toDto).collect(Collectors.toList());
    }
}
