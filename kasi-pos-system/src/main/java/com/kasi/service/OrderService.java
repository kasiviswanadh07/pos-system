package com.kasi.service;

import com.kasi.domain.OrderStatus;
import com.kasi.domain.PaymentType;
import com.kasi.payload.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO) throws Exception;

    OrderDTO getOrderById(Long id) throws Exception;

    List<OrderDTO> getOrderByBranch(Long branchId, Long customerId,
                                      Long cashierId, PaymentType paymentType, OrderStatus orderStatus);

    List<OrderDTO> getOrderByCashierId(Long cashierId);

    void deleteOrder(Long id) throws Exception;

    List<OrderDTO> getTodayOrdersByBranch(Long branchId);

    List<OrderDTO> getOrdersByCustomerId(Long customerId);

    List<OrderDTO> getTop5RecentOrdersByBranchId(Long branchId);

}
