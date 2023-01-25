package cl.sourcecode.kubernetes.microservice.course.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseStudentDTO {

    private Long id;

    private Long studentId;
}
