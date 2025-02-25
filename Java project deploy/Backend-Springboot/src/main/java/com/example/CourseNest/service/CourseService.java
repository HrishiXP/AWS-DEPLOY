package com.example.CourseNest.service;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.CourseNest.exception.CourseNotFoundException;
import com.example.CourseNest.model.Course;
import com.example.CourseNest.model.Creator;
import com.example.CourseNest.repository.CourseRepository;

import jakarta.validation.Valid;

@RestController
public class CourseService {

	@Autowired
	private CreatorService creatorService;

	@Autowired
	private CourseRepository courseRepository;

	private final String uploadDir = "C:\\A Java CourseNest\\CourseNest\\src\\main\\resources\\static\\thumbnails\\";

	public Course validateCourse(Long creatorId, Long courseId) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(
						() -> new CourseNotFoundException("Course with courseId : " + courseId + " does not exist"));

		if (course.getCreator().getCreatorId() != creatorId) {
			throw new CourseNotFoundException("CourseId : " + courseId + " does not belong to Creator id " + creatorId);
		}
		return course;
	}

	public Course validateCourseById(Long courseId) {
		return courseRepository.findById(courseId).orElseThrow(
				() -> new CourseNotFoundException("Course with courseId : " + courseId + " does not exist"));
	}

	public List<Course> findCoursesForCreator(Long creatorId) {
		Creator creator = creatorService.validateCreator(creatorId);
		return creator.getCourses();
	}

	public ResponseEntity<Course> save(Long creatorId, @Valid Course course, MultipartFile thumbnailImage)
			throws IOException {
		Creator creator = creatorService.validateCreator(creatorId);
		course.setCreator(creator);

		if (thumbnailImage != null && !thumbnailImage.isEmpty()) {
			String fileName = UUID.randomUUID().toString() + "_" + thumbnailImage.getOriginalFilename();
			Path filePath = Paths.get(uploadDir, fileName);
			Files.createDirectories(filePath.getParent()); // Ensure the directory exists
			Files.copy(thumbnailImage.getInputStream(), filePath);
			course.setThumbnailImage(filePath.toString());
		}

		Course savedCourse = courseRepository.save(course);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedCourse.getCourseId()).toUri();
		return ResponseEntity.created(location).build();
	}

	public Course findCourseByCourseId(Long creatorId, Long courseId) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(
						() -> new CourseNotFoundException("Course with courseId : " + courseId + " does not exist"));

		if (!course.getCreator().getCreatorId().equals(creatorId)) {
			throw new CourseNotFoundException("CourseId : " + courseId + " does not belong to Creator id " + creatorId);
		}
		return course;
	}

	public ResponseEntity<Course> deleteCourseByCourseId(Long creatorId, Long courseId) {
		validateCourse(creatorId, courseId);
		courseRepository.deleteById(courseId);
		return ResponseEntity.noContent().build();
	}

	public Course updateCourseByCourseId(Long creatorId, Long courseId, Course course, MultipartFile thumbnailImage)
			throws IOException {
		Course existingCourse = validateCourse(creatorId, courseId);

		if (thumbnailImage != null && !thumbnailImage.isEmpty()) {
			// Delete the old thumbnail if it exists
			if (existingCourse.getThumbnailImage() != null) {
				Path oldFilePath = Paths.get(existingCourse.getThumbnailImage());
				Files.deleteIfExists(oldFilePath);
			}

			// Save the new thumbnail
			String fileName = UUID.randomUUID().toString() + "_" + thumbnailImage.getOriginalFilename();
			Path newFilePath = Paths.get(uploadDir, fileName);
			Files.createDirectories(newFilePath.getParent()); // Ensure the directory exists
			Files.copy(thumbnailImage.getInputStream(), newFilePath);

			existingCourse.setThumbnailImage(newFilePath.toString());
		}

		// Update other course fields
		existingCourse.setTitle(course.getTitle());
		existingCourse.setDescription(course.getDescription());
		existingCourse.setPrice(course.getPrice());

		return courseRepository.save(existingCourse);
	}

	public List<Course> findAll() {
		return courseRepository.findAll();
	}

}
