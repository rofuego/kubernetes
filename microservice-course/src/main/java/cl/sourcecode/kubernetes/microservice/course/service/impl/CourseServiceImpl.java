package cl.sourcecode.kubernetes.microservice.course.service.impl;

import cl.sourcecode.kubernetes.microservice.course.dto.CourseDto;
import cl.sourcecode.kubernetes.microservice.course.dto.StudentDto;
import cl.sourcecode.kubernetes.microservice.course.dto.StudentIdDto;
import cl.sourcecode.kubernetes.microservice.course.entity.CourseEntity;
import cl.sourcecode.kubernetes.microservice.course.entity.CourseStudentEntity;
import cl.sourcecode.kubernetes.microservice.course.feign.StudentClientRest;
import cl.sourcecode.kubernetes.microservice.course.repository.CourseRepository;
import cl.sourcecode.kubernetes.microservice.course.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final StudentClientRest studentClientRest;

    private final ModelMapper modelMapper = new ModelMapper();

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
        CourseEntity courseEntity = courseRepository.findById(courseId).get();
        return new CourseDto(courseEntity.getId(), courseEntity.getName(), courseEntity.getStudentsIds().stream()
                .map(courseStudentEntity -> new StudentIdDto(courseStudentEntity.getId(),
                        courseStudentEntity.getStudentId())).toList(), new ArrayList<>());
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
        CourseDto courseDTO = getCourse(courseId);
        StudentDto serviceStudentDto = studentClientRest.getStudent(studentDTO.getId());
        CourseEntity courseEntity = modelMapper.map(courseDTO, CourseEntity.class);
        CourseStudentEntity courseStudentEntity = new CourseStudentEntity();
        courseStudentEntity.setStudentId(serviceStudentDto.getId());
        List<CourseStudentEntity> courseStudentEntityList = courseEntity.getStudentsIds();
        courseStudentEntityList.add(courseStudentEntity);
        courseEntity.setStudentsIds(courseStudentEntityList);
        courseRepository.save(courseEntity);
        return serviceStudentDto;
    }

    @Override
    @Transactional
    public StudentDto saveStudentCourse(StudentDto studentDTO, Long courseId) {
        CourseDto courseDTO = getCourse(courseId);
        StudentDto serviceStudentDto = studentClientRest.saveStudent(studentDTO);
        CourseEntity courseEntity = modelMapper.map(courseDTO, CourseEntity.class);
        CourseStudentEntity courseStudentEntity = new CourseStudentEntity();
        courseStudentEntity.setStudentId(serviceStudentDto.getId());
        List<CourseStudentEntity> courseStudentEntityList = courseEntity.getStudentsIds();
        courseStudentEntityList.add(courseStudentEntity);
        courseEntity.setStudentsIds(courseStudentEntityList);
        courseRepository.save(courseEntity);
        return serviceStudentDto;
    }

    @Override
    @Transactional
    public StudentDto deleteStudentCourse(Long studentId, Long courseId) {
        CourseDto courseDTO = getCourse(courseId);
        StudentDto studentDTO = studentClientRest.getStudent(studentId);
        CourseEntity courseEntity = modelMapper.map(courseDTO, CourseEntity.class);
        CourseStudentEntity courseStudentEntity = new CourseStudentEntity();
        courseStudentEntity.setStudentId(studentDTO.getId());
        List<CourseStudentEntity> courseStudentEntityList = courseEntity.getStudentsIds();
        for (CourseStudentEntity entity : courseStudentEntityList) {
            if (entity.getStudentId() == courseStudentEntity.getStudentId()) {
                courseStudentEntity.setId(entity.getId());
            }
        }
        courseEntity.getStudentsIds().remove(courseStudentEntity);
        courseRepository.save(courseEntity);
        return studentDTO;
    }

    @Override
    public CourseDto getCourseById(Long courseId) {
        CourseDto courseDTO = modelMapper.map(courseRepository.findById(courseId).get(), CourseDto.class);
        List<Long> listIds = courseDTO.getStudentsIds().stream().map(StudentIdDto::getStudentId).toList();
        List<StudentDto> listStudentDtos = studentClientRest.getStudentsByIds(listIds);
        courseDTO.setStudents(listStudentDtos);
        return courseDTO;
    }

}
