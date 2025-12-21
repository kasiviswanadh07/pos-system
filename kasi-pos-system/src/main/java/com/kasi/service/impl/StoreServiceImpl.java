package com.kasi.service.impl;

import com.kasi.domain.StoreStatus;
import com.kasi.exception.UserException;
import com.kasi.mapper.StoreMapper;
import com.kasi.model.Store;
import com.kasi.model.StoreContact;
import com.kasi.model.User;
import com.kasi.payload.dto.StoreDTO;
import com.kasi.repository.StoreRepository;
import com.kasi.repository.UserRepository;
import com.kasi.service.StoreService;
import com.kasi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public StoreDTO createStore(StoreDTO storeDTO, User user) {
        Store store = StoreMapper.toEntity(storeDTO, user);
        return StoreMapper.toDTO(storeRepository.save(store));
    }

    @Override
    public StoreDTO getStoreById(Long id) throws Exception {
        Store store = storeRepository.findById(id).orElseThrow(() ->
                new Exception("Store Not Found..."));
        return StoreMapper.toDTO(store);
    }

    @Override
    public List<StoreDTO> getAllStores() {
        List<Store> soreDto = storeRepository.findAll();
        return soreDto.stream().map(StoreMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Store getStoreByAdmin() throws UserException {
        User admin = userService.getCurrentUser();
        return storeRepository.findByStoreAdminId(admin.getId());
    }

    @Override
    public StoreDTO updateStore(Long id, StoreDTO storeDTO) throws UserException {
        User currentUser = userService.getCurrentUser();
        Store existing = storeRepository.findByStoreAdminId(currentUser.getId());
        if (existing == null) {
            throw new UserException("You are not access to this store");
        }
        existing.setBrand(storeDTO.getBrand());
        existing.setDescription(storeDTO.getDescription());
        existing.setStatus(storeDTO.getStatus());
        if (storeDTO.getStoreType() != null) {
            existing.setStoreType(storeDTO.getStoreType());
        }
        if (storeDTO.getContact() != null) {
            StoreContact storeContact = StoreContact.builder()
                    .address(storeDTO.getContact().getAddress())
                    .phone(storeDTO.getContact().getPhone())
                    .email(storeDTO.getContact().getEmail())
                    .build();
            existing.setStoreContact(storeContact);
        }
        Store updatedStore = storeRepository.save(existing);
        return StoreMapper.toDTO(updatedStore);
    }

    @Override
    public void deleteStore(Long id) throws UserException {
        Store store = getStoreByAdmin();
        storeRepository.delete(store);
    }

    @Override
    public StoreDTO getStoreByEmployee() throws UserException {
        User currentuser = userService.getCurrentUser();
        if (currentuser == null) {
            throw new UserException("You are not access to this store");
        }
        return StoreMapper.toDTO(currentuser.getStore());
    }

    @Override
    public StoreDTO moderateStore(Long id, StoreStatus storeStatus) throws Exception {
        Store store=storeRepository.findById(id).orElseThrow(()-> new Exception("Store not Found"));
        store.setStatus(storeStatus);
        Store updateStore=storeRepository.save(store);
        return StoreMapper.toDTO(updateStore);
    }
}
