package com.kasi.model;

import com.kasi.payload.dto.ProductDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class ShiftReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    private Double totalSales;
    private Double totalRefunds;
    private Double netSales;
    private int totalOrders;
    @ManyToOne
    private User cashier;
    @ManyToOne
    private Branch branch;
    @Transient
    private List<PaymentSummary> paymentSummaries;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> topSellingProducts;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Order> recentOrders;
    @OneToMany(mappedBy = "shiftReport", cascade = CascadeType.ALL)
    private List<Refund> refunds;


}
