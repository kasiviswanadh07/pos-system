package com.kasi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer quantity;
    private Double price;
    @ManyToOne
    private Product product;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
