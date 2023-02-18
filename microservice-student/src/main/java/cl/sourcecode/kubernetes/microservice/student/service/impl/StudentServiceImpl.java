package cl.sourcecode.kubernetes.microservice.student.service.impl;

import cl.sourcecode.kubernetes.microservice.student.dto.StudentDTO;
import cl.sourcecode.kubernetes.microservice.student.entity.StudentEntity;
import cl.sourcecode.kubernetes.microservice.student.repository.StudentRepository;
import cl.sourcecode.kubernetes.microservice.student.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDTO> getAllStudents() {
        List<StudentEntity> studentsEntities = (List<StudentEntity>) studentRepository.findAll();
        return studentsEntities.stream().map(studentEntity -> new StudentDTO(studentEntity.getId(),
                        studentEntity.getName(), studentEntity.getEmail(), studentEntity.getPassword()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDTO getStudent(Long studentId) {
        StudentEntity entity = studentRepository.findById(studentId).get();
        return new StudentDTO(entity.getId(), entity.getName(), entity.getEmail(), entity.getPassword());
    }

    @Override
    @Transactional
    public StudentDTO saveStudent(StudentDTO studentDTO) {
        StudentEntity entity = studentRepository.save(new StudentEntity(null, studentDTO.getName(),
                studentDTO.getEmail(), studentDTO.getPassword()));
        return new StudentDTO(entity.getId(), entity.getName(), entity.getEmail(), entity.getPassword());
    }

    @Override
    @Transactional
    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    @Override
    public List<StudentDTO> getAllStudentsByIds(List<Long> studentIdsList) {
        List<StudentEntity> listStudentsEntities = (List<StudentEntity>) studentRepository.findAllById(studentIdsList);
        return listStudentsEntities.stream().map(entity -> new StudentDTO(entity.getId(), entity.getName(),
                entity.getEmail(), entity.getPassword())).toList();
    }
}
