package cl.sourcecode.kubernetes.microservice.course.service;

import cl.sourcecode.kubernetes.microservice.course.dto.CourseDTO;
import cl.sourcecode.kubernetes.microservice.course.dto.StudentDTO;

import java.util.List;

public interface CourseService {

    List<CourseDTO> getAllCourses();

    CourseDTO getCourse(Long courseId);

    CourseDTO saveCourse(CourseDTO courseDTO);

    void deleteCourse(Long courseId);

    StudentDTO assignStudentCourse(StudentDTO studentDTO, Long courseId);

    StudentDTO saveStudentCourse(StudentDTO studentDTO, Long courseId);

    StudentDTO deleteStudentCourse(Long studentId, Long courseId);

    CourseDTO getFullCourseById(Long courseId);
}
