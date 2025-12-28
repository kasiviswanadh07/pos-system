package com.kasi.repository;

import com.kasi.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface InventoryRespository extends JpaRepository<Inventory, Long> {
    Inventory findByProductIdAndBranchId(Long ProductId, Long branchId);

    List<Inventory> findByBranchId(Long branchId);
}
