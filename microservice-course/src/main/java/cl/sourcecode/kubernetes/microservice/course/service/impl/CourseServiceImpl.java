package cl.sourcecode.kubernetes.microservice.course.service.impl;

import cl.sourcecode.kubernetes.microservice.course.dto.CourseDto;
import cl.sourcecode.kubernetes.microservice.course.dto.StudentDto;
import cl.sourcecode.kubernetes.microservice.course.dto.StudentIdDto;
import cl.sourcecode.kubernetes.microservice.course.entity.CourseEntity;
import cl.sourcecode.kubernetes.microservice.course.entity.CourseStudentEntity;
import cl.sourcecode.kubernetes.microservice.course.feign.StudentClientRest;
import cl.sourcecode.kubernetes.microservice.course.repository.CourseRepository;
import cl.sourcecode.kubernetes.microservice.course.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final StudentClientRest studentClientRest;

    public CourseServiceImpl(CourseRepository courseRepository, StudentClientRest studentClientRest) {
        this.courseRepository = courseRepository;
        this.studentClientRest = studentClientRest;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseDto> getAllCourses() {
        List<CourseEntity> coursesEntities = (List<CourseEntity>) courseRepository.findAll();
        return coursesEntities.stream().map(courseEntity -> new CourseDto(courseEntity.getId(), courseEntity.getName(),
                courseEntity.getStudentsIds().stream().
                        map(courseStudentEntity -> new StudentIdDto(courseStudentEntity.getId(),
                                courseStudentEntity.getStudentId())).toList(), new ArrayList<>())).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public CourseDto getCourse(Long courseId) {
        CourseEntity entity = courseRepository.findById(courseId).get();
        List<StudentIdDto> studentIds = entity.getStudentsIds().stream()
                .map(courseStudentEntity ->
                        new StudentIdDto(courseStudentEntity.getId(), courseStudentEntity.getStudentId())).collect(Collectors.toList());
        CourseDto course = new CourseDto(entity.getId(), entity.getName(), studentIds, new ArrayList<>());
        List<Long> listIds = course.getStudentsIds().stream().map(StudentIdDto::getStudentId)
                .collect(Collectors.toList());
        List<StudentDto> listStudentDtos = studentClientRest.getAllStudentsByIds(listIds);
        course.setStudents(listStudentDtos);
        return course;
    }

    @Override
    @Transactional
    public CourseDto saveCourse(CourseDto courseDTO) {
        CourseEntity courseEntity = courseRepository.
                save(new CourseEntity(null, courseDTO.getName(), new ArrayList<>()));
        return new CourseDto(courseEntity.getId(), courseEntity.getName(), courseEntity.getStudentsIds().stream()
                .map(courseStudentEntity -> new StudentIdDto(courseStudentEntity.getId(),
                        courseStudentEntity.getStudentId())).toList(), new ArrayList<>());
    }

    @Override
    @Transactional
    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);

    }

    @Override
    @Transactional
    public StudentDto assignStudentCourse(StudentDto studentDTO, Long courseId) {
        StudentDto studentDto = studentClientRest.getStudent(studentDTO.getId());
        CourseEntity course = courseRepository.findById(courseId).get();
        CourseStudentEntity courseStudent = new CourseStudentEntity(null, studentDto.getId());
        course.getStudentsIds().add(courseStudent);
        courseRepository.save(course);
        return studentDto;
    }

    @Override
    @Transactional
    public StudentDto saveStudentCourse(StudentDto studentDTO, Long courseId) {
        StudentDto studentDto = studentClientRest.saveStudent(studentDTO);
        CourseEntity course = courseRepository.findById(courseId).get();
        CourseStudentEntity courseStudent = new CourseStudentEntity(null, studentDto.getId());
        course.getStudentsIds().add(courseStudent);
        courseRepository.save(course);
        return studentDto;
    }

    @Override
    @Transactional
    public StudentDto deleteStudentCourse(Long studentId, Long courseId) {
        StudentDto studentDTO = studentClientRest.getStudent(studentId);
        CourseStudentEntity studentEntity = new CourseStudentEntity(null, courseId);
        CourseEntity course = courseRepository.findById(courseId).get();
        course.getStudentsIds().remove(studentEntity);
        courseRepository.save(course);
        return studentDTO;
    }
}
