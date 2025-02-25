package com.example.CourseNest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CourseNest.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	List<Payment> findByUserId(Long userId);

	Payment findByOrderId(String orderId);
}
