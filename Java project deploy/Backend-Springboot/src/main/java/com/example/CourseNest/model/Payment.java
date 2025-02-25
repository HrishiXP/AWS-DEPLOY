package com.example.CourseNest.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Course course;

	@OneToMany(mappedBy = "payment", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnore
	private List<Purchase> purchases;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Consultation consultation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Workshop workshop;

	@Column(nullable = false)
	private BigDecimal amount;

	@Column(nullable = false)
	private String status;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@Column(nullable = false)
	private String orderId;
}
