package cl.sourcecode.kubernetes.microservice.course.service;

import cl.sourcecode.kubernetes.microservice.course.dto.CourseDto;
import cl.sourcecode.kubernetes.microservice.course.dto.StudentDto;

import java.util.List;

public interface CourseService {

    List<CourseDto> getAllCourses();

    CourseDto getCourse(Long courseId);

    CourseDto saveCourse(CourseDto courseDTO);

    void deleteCourse(Long courseId);

    StudentDto assignStudentCourse(StudentDto studentDTO, Long courseId);

    StudentDto saveStudentCourse(StudentDto studentDTO, Long courseId);

    StudentDto deleteStudentCourse(Long studentId, Long courseId);
}
