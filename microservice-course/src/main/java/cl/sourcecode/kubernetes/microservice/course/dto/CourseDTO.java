package cl.sourcecode.kubernetes.microservice.course.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

    private Long id;

    private String name;

    private List<StudentIdDTO> studentsIds;

    private List<StudentDTO> students;
}
