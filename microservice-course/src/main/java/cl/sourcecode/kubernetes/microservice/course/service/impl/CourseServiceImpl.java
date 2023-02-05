package cl.sourcecode.kubernetes.microservice.course.service.impl;

import cl.sourcecode.kubernetes.microservice.course.dto.CourseDTO;
import cl.sourcecode.kubernetes.microservice.course.dto.StudentDTO;
import cl.sourcecode.kubernetes.microservice.course.dto.StudentIdDTO;
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
    public List<CourseDTO> getAllCourses() {
        List<CourseEntity> coursesEntities = (List<CourseEntity>) courseRepository.findAll();
        return coursesEntities.stream().map(courseEntity -> new CourseDTO(courseEntity.getId(), courseEntity.getName(),
                courseEntity.getStudentsIds().stream().
                        map(courseStudentEntity -> new StudentIdDTO(courseStudentEntity.getId(),
                                courseStudentEntity.getStudentId())).toList(), new ArrayList<>())).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public CourseDTO getCourse(Long courseId) {
        CourseEntity courseEntity = courseRepository.findById(courseId).get();
        return new CourseDTO(courseEntity.getId(), courseEntity.getName(), courseEntity.getStudentsIds().stream()
                .map(courseStudentEntity -> new StudentIdDTO(courseStudentEntity.getId(),
                        courseStudentEntity.getStudentId())).toList(), new ArrayList<>());
    }

    @Override
    @Transactional
    public CourseDTO saveCourse(CourseDTO courseDTO) {
        CourseEntity courseEntity = courseRepository.
                save(new CourseEntity(null, courseDTO.getName(), new ArrayList<>()));
        return new CourseDTO(courseEntity.getId(), courseEntity.getName(), courseEntity.getStudentsIds().stream()
                .map(courseStudentEntity -> new StudentIdDTO(courseStudentEntity.getId(),
                        courseStudentEntity.getStudentId())).toList(), new ArrayList<>());
    }

    @Override
    @Transactional
    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);

    }

    @Override
    @Transactional
    public StudentDTO assignStudentCourse(StudentDTO studentDTO, Long courseId) {
        CourseDTO courseDTO = getCourse(courseId);
        StudentDTO serviceStudentDTO = studentClientRest.getStudent(studentDTO.getId());
        CourseEntity courseEntity = modelMapper.map(courseDTO, CourseEntity.class);
        CourseStudentEntity courseStudentEntity = new CourseStudentEntity();
        courseStudentEntity.setStudentId(serviceStudentDTO.getId());
        List<CourseStudentEntity> courseStudentEntityList = courseEntity.getStudentsIds();
        courseStudentEntityList.add(courseStudentEntity);
        courseEntity.setStudentsIds(courseStudentEntityList);
        courseRepository.save(courseEntity);
        return serviceStudentDTO;
    }

    @Override
    @Transactional
    public StudentDTO saveStudentCourse(StudentDTO studentDTO, Long courseId) {
        CourseDTO courseDTO = getCourse(courseId);
        StudentDTO serviceStudentDTO = studentClientRest.saveStudent(studentDTO);
        CourseEntity courseEntity = modelMapper.map(courseDTO, CourseEntity.class);
        CourseStudentEntity courseStudentEntity = new CourseStudentEntity();
        courseStudentEntity.setStudentId(serviceStudentDTO.getId());
        List<CourseStudentEntity> courseStudentEntityList = courseEntity.getStudentsIds();
        courseStudentEntityList.add(courseStudentEntity);
        courseEntity.setStudentsIds(courseStudentEntityList);
        courseRepository.save(courseEntity);
        return serviceStudentDTO;
    }

    @Override
    @Transactional
    public StudentDTO deleteStudentCourse(Long studentId, Long courseId) {
        CourseDTO courseDTO = getCourse(courseId);
        StudentDTO studentDTO = studentClientRest.getStudent(studentId);
        CourseEntity courseEntity = modelMapper.map(courseDTO, CourseEntity.class);
        CourseStudentEntity courseStudentEntity = new CourseStudentEntity();
        courseStudentEntity.setStudentId(studentDTO.getId());
        List<CourseStudentEntity> courseStudentEntityList = courseEntity.getStudentsIds();
        for (CourseStudentEntity entity : courseStudentEntityList) {
            if (entity.getStudentId() == courseStudentEntity.getStudentId()) {
                courseStudentEntity.setId(entity.getId());
            }
        }
        courseEntity.removeStudent(courseStudentEntity);
        courseRepository.save(courseEntity);
        return studentDTO;
    }

    @Override
    public CourseDTO getCourseById(Long courseId) {
        CourseDTO courseDTO = modelMapper.map(courseRepository.findById(courseId).get(), CourseDTO.class);
        List<Long> listIds = courseDTO.getStudentsIds().stream().map(StudentIdDTO::getStudentId).toList();
        List<StudentDTO> listStudentDTOs = studentClientRest.getStudentsByIds(listIds);
        courseDTO.setStudents(listStudentDTOs);
        return courseDTO;
    }

}
