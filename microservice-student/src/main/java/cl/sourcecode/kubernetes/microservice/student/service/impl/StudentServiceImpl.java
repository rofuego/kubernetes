package cl.sourcecode.kubernetes.microservice.student.service.impl;

import cl.sourcecode.kubernetes.microservice.student.dto.StudentDTO;
import cl.sourcecode.kubernetes.microservice.student.entity.StudentEntity;
import cl.sourcecode.kubernetes.microservice.student.repository.StudentRepository;
import cl.sourcecode.kubernetes.microservice.student.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final ModelMapper modelMapper = new ModelMapper();

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDTO> getAllStudents() {
        List<StudentEntity> studentsEntities = (List<StudentEntity>) studentRepository.findAll();
        return studentsEntities.stream().map(studentEntity -> modelMapper.map(studentEntity, StudentDTO.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDTO getStudent(Long studentId) {
        return modelMapper.map(studentRepository.findById(studentId).get(), StudentDTO.class);
    }

    @Override
    @Transactional
    public StudentDTO saveStudent(StudentDTO studentDTO) {
        return modelMapper.map(studentRepository.save(modelMapper.map(studentDTO, StudentEntity.class)),
                StudentDTO.class);
    }

    @Override
    @Transactional
    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    @Override
    public List<StudentDTO> getStudentsByIdsList(List<Long> studentIdsList) {
        List<StudentEntity> listStudentsEntities = (List<StudentEntity>) studentRepository.findAllById(studentIdsList);
        return listStudentsEntities.stream().map(studentEntity -> modelMapper.map(studentEntity, StudentDTO.class))
                .toList();
    }
}
