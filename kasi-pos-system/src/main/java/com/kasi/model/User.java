package com.kasi.model;

import com.kasi.domain.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private Long id;

    @Column(name = "Full_Name", nullable = false)
    private String fullname;
    @Column(nullable = false, unique = true)
    @Email(message = "Email should be valid")
    private String email;
    private String phone;

    private UserRole role;
    @Column(nullable = false)
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;


}
