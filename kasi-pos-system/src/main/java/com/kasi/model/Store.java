package com.kasi.model;

import com.kasi.domain.StoreStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String brand;
    @OneToOne
    private User storeAdmin;
    private LocalDateTime createAt;

    private LocalDateTime updateAt;
    private String description;
    private String storeType;
    private StoreStatus status;
    @Embedded
    private StoreContact storeContact = new StoreContact();

    @PrePersist
    protected void onCreate() {
        createAt = LocalDateTime.now();
        status = StoreStatus.PENDING;
    }
    @PreUpdate
    protected void onUpdate() {
        updateAt = LocalDateTime.now();
    }

}
