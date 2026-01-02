package com.kasi.repository;

import com.kasi.domain.UserRole;
import com.kasi.model.Inventory;
import com.kasi.model.Store;
import com.kasi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String username);

    List<User> findByStore(Store store);

    List<User> findByBranchId(Long brachId);

}
