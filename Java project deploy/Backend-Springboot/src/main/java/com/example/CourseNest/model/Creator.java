package com.example.CourseNest.model;

import java.util.ArrayList;
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
@Entity
public class Creator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creatorId;

    @Size(min = 3, message = "Name should at least have 3 characters")
    private String name;

    @Size(min = 4, message = "Email should at least have 4 characters")
    @Email(message = "Email should be valid")
    private String email;

    @Size(min = 5, message = "Password should at least have 5 characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[@!#$%^&*]).+$", message = "Password must start with an uppercase letter and contain at least one special character (@!#$%^&*)")
    private String password;

    @Size(min = 20, message = "Bio should at least have 20 characters")
    private String bio;

    @Column(nullable = false)
    private String role = "CREATOR";

    @Column(nullable = false)
    private boolean enabled = true;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Course> courses = new ArrayList<Course>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Consultation> consultations = new ArrayList<Consultation>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Workshop> workshops = new ArrayList<Workshop>();

}
