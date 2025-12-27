package com.kasi.service;

import com.kasi.model.User;
import com.kasi.payload.dto.BranchDTO;

import java.util.List;

public interface BranchService {
    BranchDTO createBranch(BranchDTO branchDTO) throws Exception;

    List<BranchDTO> getAllBranchesByStoreId(Long storeId);

    BranchDTO updateBranch(Long id, BranchDTO branchDTO, User user) throws Exception;

    void deleteBranch(Long id) throws Exception;

    BranchDTO getBranchById(Long id) throws Exception;
}
