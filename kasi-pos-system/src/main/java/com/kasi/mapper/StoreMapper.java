package com.kasi.mapper;

import com.kasi.model.Store;
import com.kasi.model.User;
import com.kasi.payload.dto.StoreDTO;
import com.kasi.service.StoreService;

public class StoreMapper {
    public static StoreDTO toDTO(Store store) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setId(store.getId());
        storeDTO.setBrand(store.getBrand());
        storeDTO.setStoreAdmin(UserMapper.toDTO(store.getStoreAdmin()));
        storeDTO.setDescription(store.getDescription());
        storeDTO.setStoreType(store.getStoreType());
        storeDTO.setContact(store.getStoreContact());
        storeDTO.setCreateAt(store.getCreateAt());
        storeDTO.setUpdateAt(store.getUpdateAt());
        storeDTO.setStatus(store.getStatus());
        return storeDTO;
    }
    public static Store toEntity(StoreDTO storeDTO, User storeAdmin){
        Store store=new Store();
        store.setId(storeDTO.getId());
        store.setBrand(storeDTO.getBrand());
        store.setDescription(storeDTO.getDescription());
        store.setStoreAdmin(storeAdmin);
        store.setStoreType(storeDTO.getStoreType());
        store.setStoreContact(storeDTO.getContact());
        store.setCreateAt(storeDTO.getCreateAt());
        store.setUpdateAt(storeDTO.getUpdateAt());
        return store;
    }
}
