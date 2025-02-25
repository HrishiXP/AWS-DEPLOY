package com.example.CourseNest.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "user_details")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, message = "Name should at least have 3 characters")
    private String name;

    @Size(min = 3, message = "Email should at least have 4 characters")
    @Email(message = "Email should be valid")
    private String email;

    @Size(min = 5, message = "Password should at least have 5 characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[@!#$%^&*]).+$", message = "Password must start with an uppercase letter and contain at least one special character (@!#$%^&*)")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Purchase> purchases;

    @Column(nullable = false)
    private BigDecimal credits = BigDecimal.ZERO;

    @Column(nullable = false)
    private String role = "USER";

    @Column(nullable = false)
    private boolean enabled = true;
}
