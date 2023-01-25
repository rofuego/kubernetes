package cl.sourcecode.kubernetes.microservice.course.repository;

import org.springframework.data.repository.CrudRepository;

import cl.sourcecode.kubernetes.microservice.course.entity.CourseEntity;

public interface CourseRepository extends CrudRepository<CourseEntity, Long> {

}
