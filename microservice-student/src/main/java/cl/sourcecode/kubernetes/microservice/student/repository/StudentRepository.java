package cl.sourcecode.kubernetes.microservice.student.repository;

import org.springframework.data.repository.CrudRepository;

import cl.sourcecode.kubernetes.microservice.student.entity.StudentEntity;

public interface StudentRepository extends CrudRepository<StudentEntity, Long> {
}
