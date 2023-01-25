package cl.sourcecode.kubernetes.microservice.student.service;

import cl.sourcecode.kubernetes.microservice.student.dto.StudentDTO;

import java.util.List;

public interface StudentService {

    List<StudentDTO> getAllStudents();

    StudentDTO getStudent(Long studentId);

    StudentDTO saveStudent(StudentDTO studentDTO);

    void deleteStudent(Long studentId);

    List<StudentDTO> getStudentsByIdsList(List<Long> studentIdsList);
}
