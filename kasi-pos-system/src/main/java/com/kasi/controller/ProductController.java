package com.kasi.controller;

import com.kasi.model.User;
import com.kasi.payload.dto.ProductDTO;
import com.kasi.payload.response.ApiResponse;
import com.kasi.service.ProductService;
import com.kasi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO,
                                                    @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.getUserFromJwtToken(jwt);

        return ResponseEntity.ok(productService.createProduct(productDTO, user));
    }

    @GetMapping("store/{storeId}")
    public ResponseEntity<List<ProductDTO>> getStoreById(@PathVariable Long storeId,
                                                         @RequestHeader("Authorization") String jwt) throws Exception {

        return ResponseEntity.ok(productService.getProductsByStoreId(storeId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,
                                                    @RequestBody ProductDTO productDTO,
                                                    @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(productService.updateProduct(id, productDTO, user));
    }

    @GetMapping("store/{storeId}/search")
    public ResponseEntity<List<ProductDTO>> searchByKeyword(@PathVariable Long storeId,
                                                            @RequestParam String keyword,
                                                            @RequestHeader("Authorization") String jwt) throws Exception {

        return ResponseEntity.ok(productService.searchByKeyword(storeId, keyword));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id,
                                                     @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.getUserFromJwtToken(jwt);
        productService.deleteProduct(id, user);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Product Delete Successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
