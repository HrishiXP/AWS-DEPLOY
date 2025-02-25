package com.example.CourseNest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CourseNest.model.Modules;
import com.example.CourseNest.service.ModuleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ModuleController {

	@Autowired
	private ModuleService moduleService;

	@GetMapping("/{courseId}/modules")
	private List<Modules> retrieveAllModulesForCourse(@PathVariable Long courseId) {
		return this.moduleService.findAllModulesByCourseId(courseId);
	}

	@PostMapping("/{courseId}/modules")
	public ResponseEntity<Modules> createModulesForCourse(@PathVariable Long courseId,
			@Valid @RequestBody Modules module) {
		return this.moduleService.save(courseId, module);
	}

	@GetMapping("/{courseId}/modules/{moduleId}")
	public Modules retrieveModuleForCourse(@PathVariable Long courseId, @PathVariable Long moduleId) {
		return this.moduleService.findModuleByIdForCourse(courseId, moduleId);
	}

	@DeleteMapping("/{courseId}/modules/{moduleId}")
	public ResponseEntity<Modules> deleteModuleForCourse(@PathVariable Long courseId, @PathVariable Long moduleId) {
		return this.moduleService.deleteModuleByIdForCourse(courseId, moduleId);
	}

	@PutMapping("/{courseId}/modules/{moduleId}")
	public Modules updateModuleForCourse(@PathVariable Long courseId, @PathVariable Long moduleId,
			@Valid @RequestBody Modules module) {
		return this.moduleService.updateModuleByIdForCourse(courseId, moduleId, module);
	}
}
