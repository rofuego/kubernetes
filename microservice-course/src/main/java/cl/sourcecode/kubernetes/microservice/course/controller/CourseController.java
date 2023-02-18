package cl.sourcecode.kubernetes.microservice.course.controller;

import cl.sourcecode.kubernetes.microservice.course.dto.CourseDto;
import cl.sourcecode.kubernetes.microservice.course.dto.StudentDto;
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
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable Long courseId) {
        return new ResponseEntity<>(courseService.getCourse(courseId), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<CourseDto> saveCourse(@RequestBody CourseDto courseDTO) {
        return new ResponseEntity<>(courseService.saveCourse(courseDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/assign-student/{courseId}")
    public ResponseEntity<StudentDto> assignStudentCourse(@PathVariable Long courseId,
                                                          @RequestBody StudentDto studentDTO) {
        return new ResponseEntity<>(courseService.assignStudentCourse(studentDTO, courseId), HttpStatus.CREATED);
    }

    @PostMapping("/save-student/{courseId}")
    public ResponseEntity<StudentDto> saveStudentCourse(@PathVariable Long courseId,
                                                        @RequestBody StudentDto studentDTO) {
        return new ResponseEntity<>(courseService.saveStudentCourse(studentDTO, courseId), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-student/{courseId}")
    public ResponseEntity<StudentDto> deleteStudentCourse(@PathVariable Long courseId,
                                                          @RequestBody StudentDto studentDTO) {
        return new ResponseEntity<>(courseService.deleteStudentCourse(studentDTO.getId(), courseId),
                HttpStatus.NO_CONTENT);
    }

}
