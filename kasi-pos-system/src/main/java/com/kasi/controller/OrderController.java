package com.kasi.controller;

import com.kasi.domain.OrderStatus;
import com.kasi.domain.PaymentType;
import com.kasi.payload.dto.OrderDTO;
import com.kasi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) throws Exception {

        return ResponseEntity.ok(orderService.createOrder(orderDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) throws Exception {

        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<OrderDTO>> getOrderByBranch(@PathVariable Long branchId,
                                                           @RequestParam(required = false) Long customerId,
                                                           @RequestParam(required = false) Long cashierId,
                                                           @RequestParam(required = false) PaymentType paymentType,
                                                           @RequestParam(required = false) OrderStatus status) throws Exception {

        return ResponseEntity.ok(orderService.getOrderByBranch(branchId, customerId, cashierId, paymentType, status));
    }

    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<OrderDTO>> getOrderByCashierId(@PathVariable Long cashierId) throws Exception {

        return ResponseEntity.ok(orderService.getOrderByCashierId(cashierId));
    }

    @GetMapping("today/branch/{branchId}")
    public ResponseEntity<List<OrderDTO>> getTodayOrdersByBranch(@PathVariable Long branchId) throws Exception {

        return ResponseEntity.ok(orderService.getTodayOrdersByBranch(branchId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomerId(@PathVariable Long customerId) throws Exception {

        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
    }

    @GetMapping("/recent/{branchId}")
    public ResponseEntity<List<OrderDTO>> getTop5RecentOrdersByBranchId(@PathVariable Long branchId) throws Exception {

        return ResponseEntity.ok(orderService.getTop5RecentOrdersByBranchId(branchId));
    }
}
