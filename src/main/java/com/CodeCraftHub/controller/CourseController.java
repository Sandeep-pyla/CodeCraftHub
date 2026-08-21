package com.CodeCraftHub.controller;

import com.CodeCraftHub.model.Course;
import com.CodeCraftHub.service.CourseService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    /*
     * Constructor injection.
     *
     * Spring automatically provides CourseService.
     */
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * POST /api/courses
     *
     * Creates a new course.
     */
    @PostMapping
    public ResponseEntity<Course> createCourse(
            @Valid @RequestBody Course course) {

        Course createdCourse = courseService.createCourse(course);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdCourse);
    }

    /**
     * GET /api/courses
     *
     * Returns all courses.
     */
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {

        List<Course> courses = courseService.getAllCourses();

        return ResponseEntity.ok(courses);
    }

    /**
     * GET /api/courses/{id}
     *
     * Returns one course.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(
            @PathVariable Long id) {

        Course course = courseService.getCourseById(id);

        return ResponseEntity.ok(course);
    }

    /**
     * PUT /api/courses/{id}
     *
     * Updates an existing course.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody Course course) {

        Course updatedCourse =
                courseService.updateCourse(id, course);

        return ResponseEntity.ok(updatedCourse);
    }

    /**
     * DELETE /api/courses/{id}
     *
     * Deletes an existing course.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(
            @PathVariable Long id) {

        courseService.deleteCourse(id);

        /*
         * 204 means the operation succeeded
         * and there is no response body.
         */
        return ResponseEntity.noContent().build();
    }
    /**
     * GET /api/courses/stats
     *
     * Returns statistics about all courses:
     * - Total number of courses
     * - Number of Not Started courses
     * - Number of In Progress courses
     * - Number of Completed courses
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getCourseStats() {

        // Get all courses from the JSON file
        List<Course> courses = courseService.getAllCourses();

        // Count courses by status
        long notStarted = courses.stream()
                .filter(course -> "Not Started".equals(course.getStatus()))
                .count();

        long inProgress = courses.stream()
                .filter(course -> "In Progress".equals(course.getStatus()))
                .count();

        long completed = courses.stream()
                .filter(course -> "Completed".equals(course.getStatus()))
                .count();

        // Create the response
        Map<String, Object> stats = new LinkedHashMap<>();

        stats.put("totalCourses", courses.size());
        stats.put("Not Started", notStarted);
        stats.put("In Progress", inProgress);
        stats.put("Completed", completed);

        return ResponseEntity.ok(stats);
    }
}