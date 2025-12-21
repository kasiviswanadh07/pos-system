package com.kasi.payload.dto;

import com.kasi.domain.StoreStatus;
import com.kasi.model.StoreContact;
import com.kasi.model.User;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class StoreDTO {
    private Long id;
    private String brand;

    private UserDTO storeAdmin;
    private LocalDateTime createAt;

    private LocalDateTime updateAt;
    private String description;
    private String storeType;
    private StoreStatus status;
    private StoreContact contact = new StoreContact();
}
