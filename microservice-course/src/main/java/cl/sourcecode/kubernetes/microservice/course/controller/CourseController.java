package cl.sourcecode.kubernetes.microservice.course.controller;

import cl.sourcecode.kubernetes.microservice.course.dto.CourseDTO;
import cl.sourcecode.kubernetes.microservice.course.dto.StudentDTO;
import cl.sourcecode.kubernetes.microservice.course.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable Long courseId) {
        return new ResponseEntity<>(courseService.getFullCourseById(courseId), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<CourseDTO> saveCourse(@RequestBody CourseDTO courseDTO) {
        return new ResponseEntity<>(courseService.saveCourse(courseDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/assign-student/{courseId}")
    public ResponseEntity<StudentDTO> assignStudentCourse(@PathVariable Long courseId, @RequestBody StudentDTO studentDTO) {
        return new ResponseEntity<>(courseService.assignStudentCourse(studentDTO, courseId), HttpStatus.CREATED);
    }

    @PostMapping("/save-student/{courseId}")
    public ResponseEntity<StudentDTO> saveStudentCourse(@PathVariable Long courseId, @RequestBody StudentDTO studentDTO) {
        return new ResponseEntity<>(courseService.saveStudentCourse(studentDTO, courseId), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-student/{courseId}")
    public ResponseEntity<StudentDTO> deleteStudentCourse(@PathVariable Long courseId, @RequestBody StudentDTO studentDTO) {
        return new ResponseEntity<>(courseService.deleteStudentCourse(studentDTO.getId(), courseId), HttpStatus.NO_CONTENT);
    }

}
