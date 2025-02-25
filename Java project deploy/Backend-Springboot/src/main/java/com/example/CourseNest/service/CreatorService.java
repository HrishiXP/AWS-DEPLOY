package com.example.CourseNest.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.CourseNest.controller.CreatorController;
import com.example.CourseNest.exception.CreatorNotFoundException;
import com.example.CourseNest.model.Creator;
import com.example.CourseNest.repository.CreatorRepository;

import jakarta.validation.Valid;

@Service
public class CreatorService {

    @Autowired
    private CreatorRepository creatorRepository;

    public Creator validateCreator(Long creatorId) {
        return creatorRepository.findById(creatorId)
                .orElseThrow(() -> new CreatorNotFoundException("Creator with id : " + creatorId + " doesn't exist"));
    }

    public List<Creator> findAll() {
        return creatorRepository.findAll();
    }

    public ResponseEntity<Creator> save(@Valid Creator creator) {
        Creator savedCreator = creatorRepository.save(creator);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCreator.getCreatorId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    public EntityModel<Creator> findById(long id) {
        Creator creator = validateCreator(id);
        EntityModel<Creator> entityModel = EntityModel.of(creator);
        WebMvcLinkBuilder link = linkTo(methodOn(CreatorController.class).retrieveAllCreators());
        entityModel.add(link.withRel("all-creators"));

        return entityModel;
    }

    public ResponseEntity<Creator> deleteCreatorById(Long id) {
        validateCreator(id);
        creatorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public Creator updateCreator(Long id, Creator creator) {
        Creator existingCreator = validateCreator(id);
        existingCreator.setName(creator.getName());
        existingCreator.setEmail(creator.getEmail());
        existingCreator.setBio(creator.getBio());
        existingCreator.setPassword(creator.getPassword());
        return creatorRepository.save(existingCreator);
    }

}
