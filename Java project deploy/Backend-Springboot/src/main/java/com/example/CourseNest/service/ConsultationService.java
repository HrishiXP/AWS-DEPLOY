package com.example.CourseNest.service;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.CourseNest.exception.ConsultationNotFoundException;
import com.example.CourseNest.exception.CreatorNotFoundException;
import com.example.CourseNest.model.Consultation;
import com.example.CourseNest.model.Creator;
import com.example.CourseNest.repository.ConsultationRepository;

import jakarta.validation.Valid;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;
    @Autowired
    private CreatorService creatorService;

    private Consultation validateConsultation(Long creatorId, Long consultationId) {
        Consultation consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new ConsultationNotFoundException(
                        "Consultation with id: " + consultationId + " does not exist"));

        if (!consultation.getCreator().getCreatorId().equals(creatorId)) {
            throw new CreatorNotFoundException("Creator with id: " + creatorId + " doesn't exist");
        }
        return consultation;
    }

    public List<Consultation> findConsultationForCreator(Long creatorId) {
        return consultationRepository.findByCreator_CreatorId(creatorId);
    }

    public ResponseEntity<Consultation> save(Long creatorId, @Valid Consultation consultation) {
        Creator creator = creatorService.validateCreator(creatorId);
        consultation.setCreator(creator);

        Consultation savedConsultation = consultationRepository.save(consultation);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedConsultation.getConsultationId()).toUri();
        return ResponseEntity.created(location).body(savedConsultation);
    }

    public Consultation findConsultationByCreatorId(Long creatorId, Long consultationId) {
        return validateConsultation(creatorId, consultationId);
    }

    public ResponseEntity<Consultation> deleteConsultationForCreator(Long creatorId, Long consultationId) {
        validateConsultation(creatorId, consultationId);
        consultationRepository.deleteById(consultationId);
        return ResponseEntity.noContent().build();
    }

    public Consultation updateConsultationForCreator(Long creatorId, Long consultationId,
            @Valid Consultation consultation) {
        Consultation existingConsultation = validateConsultation(creatorId, consultationId);
        existingConsultation.setTitle(consultation.getTitle());
        existingConsultation.setDescription(consultation.getDescription());
        existingConsultation.setSlotsPerUser(consultation.getSlotsPerUser());
        existingConsultation.setDuration(consultation.getDuration());
        existingConsultation.setCallPlatform(consultation.getCallPlatform());
        existingConsultation.setExternalLink(consultation.getExternalLink());
        existingConsultation.setPrice(consultation.getPrice());
        existingConsultation.setStartDate(consultation.getStartDate());
        existingConsultation.setEndDate(consultation.getEndDate());
        existingConsultation.setStartTime(consultation.getStartTime());
        existingConsultation.setEndTime(consultation.getEndTime());
        return consultationRepository.save(existingConsultation);
    }

    public List<Consultation> findAll() {
        return consultationRepository.findAll();
    }
}
