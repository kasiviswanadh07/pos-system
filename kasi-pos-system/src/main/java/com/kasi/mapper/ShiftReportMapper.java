package com.kasi.mapper;

import com.kasi.model.Order;
import com.kasi.model.Product;
import com.kasi.model.Refund;
import com.kasi.model.ShiftReport;
import com.kasi.payload.dto.OrderDTO;
import com.kasi.payload.dto.ProductDTO;
import com.kasi.payload.dto.RefundDTO;
import com.kasi.payload.dto.ShiftReportDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ShiftReportMapper {
    public static ShiftReportDTO toDTO(ShiftReport shiftReport) {

        return ShiftReportDTO.builder()
                .id(shiftReport.getId())
                .shiftEnd(shiftReport.getShiftEnd())
                .shiftStart(shiftReport.getShiftStart())
                .netSales(shiftReport.getNetSales())
                .totalSales(shiftReport.getTotalSales())
                .paymentSummaries(shiftReport.getPaymentSummaries())
                .totalOrders(shiftReport.getTotalOrders())
                .totalRefunds(shiftReport.getTotalRefunds())
                .cashier(UserMapper.toDTO(shiftReport.getCashier()))
                .cashierId(shiftReport.getCashier().getId())
                .branchId(shiftReport.getId())
                .recentOrders(mapOrders(shiftReport.getRecentOrders()))
                .topSellingProducts(mapProducts(shiftReport.getTopSellingProducts()))
                .refunds(mapRefunds(shiftReport.getRefunds()))
                .build();
    }

    private static List<RefundDTO> mapRefunds(List<Refund> refunds) {
        if (refunds == null || refunds.isEmpty()) {
            return null;
        }
        return refunds.stream().map(RefundMapper::toDTO)
                .collect(Collectors.toList());
    }

    private static List<ProductDTO> mapProducts(List<Product> topSellingProducts) {
        if (topSellingProducts == null || topSellingProducts.isEmpty()) {
            return null;
        }
        return topSellingProducts.stream().map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    private static List<OrderDTO> mapOrders(List<Order> recentOrders) {
        if (recentOrders == null || recentOrders.isEmpty()) {
            return null;
        }
        return recentOrders.stream().map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

}