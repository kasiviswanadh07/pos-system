package com.kasi.model;

import com.kasi.domain.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Full_Name", nullable = false)
    private String fullname;
    @Column(nullable = false, unique = true)
    @Email(message = "Email should be valid")
    private String email;
    @ManyToOne
    private Store store;
    private String phone;

    @Column(nullable = false)
    private UserRole role;
    @Column(nullable = false)
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;


}
