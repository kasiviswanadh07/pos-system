package com.kasi.controller;

import com.kasi.domain.StoreStatus;
import com.kasi.exception.UserException;
import com.kasi.mapper.StoreMapper;
import com.kasi.model.User;
import com.kasi.payload.dto.StoreDTO;
import com.kasi.payload.response.ApiResponse;
import com.kasi.service.StoreService;
import com.kasi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {
    private final StoreService storeService;
    private final UserService userSertvice;

    @PostMapping("")
    public ResponseEntity<StoreDTO> createSore(@RequestBody StoreDTO storeDTO,
                                               @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userSertvice.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(storeService.createStore(storeDTO, user));

    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDTO> getStoreById(@PathVariable Long stroreId,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        return ResponseEntity.ok(storeService.getStoreById(stroreId));

    }

    @GetMapping("")
    public ResponseEntity<List<StoreDTO>> getAllStore(
            @RequestHeader("Authorization") String jwt) throws Exception {
        return ResponseEntity.ok(storeService.getAllStores());

    }

    @GetMapping("/admin")
    public ResponseEntity<StoreDTO> getStoreByAdmin(
            @RequestHeader("Authorization") String jwt) throws Exception {
        return ResponseEntity.ok(StoreMapper.toDTO(storeService.getStoreByAdmin()));

    }

    @GetMapping("/employee")
    public ResponseEntity<StoreDTO> getStoreByEmployee(
            @RequestHeader("Authorization") String jwt) throws Exception {
        return ResponseEntity.ok(storeService.getStoreByEmployee());

    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreDTO> updateStore(@PathVariable Long id, @RequestBody StoreDTO storeDTO,
                                               @RequestHeader("Authorization") String jwt) throws UserException {
        return ResponseEntity.ok(storeService.updateStore(id, storeDTO));

    }

    @PutMapping("/{id}/moderate")
    public ResponseEntity<StoreDTO> moderateStore(@PathVariable Long id, @RequestParam StoreStatus storeStatus
    ) throws Exception {
        return ResponseEntity.ok(storeService.moderateStore(id, storeStatus));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStore(@PathVariable Long id) throws Exception {
        storeService.deleteStore(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Successfully deleted store");
        return ResponseEntity.ok(apiResponse);

    }
}
