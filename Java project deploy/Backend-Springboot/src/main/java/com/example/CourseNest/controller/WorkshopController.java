package com.example.CourseNest.controller;

import com.example.CourseNest.model.Workshop;
import com.example.CourseNest.service.WorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WorkshopController {

    @Autowired
    private WorkshopService workshopService;

    @GetMapping("/workshops")
    public List<Workshop> retrieveAllWorkshops() {
        return workshopService.findAll();
    }

    @GetMapping("/{creatorId}/workshops")
    public List<Workshop> retrieveWorkshopsForCreator(@PathVariable Long creatorId) {
        return workshopService.findWorkshopsForCreator(creatorId);
    }

    @PostMapping("/{creatorId}/workshops")
    public ResponseEntity<Workshop> createWorkshopForCreator(@PathVariable Long creatorId,
            @Valid @RequestBody Workshop workshop) {
        return workshopService.save(creatorId, workshop);
    }

    @GetMapping("/{creatorId}/workshops/{workshopId}")
    public Workshop retrieveWorkshopForCreator(@PathVariable Long creatorId, @PathVariable Long workshopId) {
        return workshopService.findWorkshopByCreatorId(creatorId, workshopId);
    }

    @DeleteMapping("/{creatorId}/workshops/{workshopId}")
    public ResponseEntity<Workshop> deleteWorkshopForCreator(@PathVariable Long creatorId,
            @PathVariable Long workshopId) {
        return workshopService.deleteWorkshopForCreator(creatorId, workshopId);
    }

    @PutMapping("/{creatorId}/workshops/{workshopId}")
    public Workshop updateWorkshopForCreator(@PathVariable Long creatorId, @PathVariable Long workshopId,
            @Valid @RequestBody Workshop workshop) {
        return workshopService.updateWorkshopForCreator(creatorId, workshopId, workshop);
    }
}
