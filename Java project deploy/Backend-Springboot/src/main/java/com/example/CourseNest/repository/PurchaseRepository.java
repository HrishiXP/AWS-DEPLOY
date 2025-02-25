package com.example.CourseNest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CourseNest.model.Payment;
import com.example.CourseNest.model.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUserIdAndCourseIsNotNull(Long userId);

    List<Purchase> findByUserIdAndConsultationIsNotNull(Long userId);

    List<Purchase> findByUserIdAndWorkshopIsNotNull(Long userId);

    Purchase findByPayment(Payment payment);
}
