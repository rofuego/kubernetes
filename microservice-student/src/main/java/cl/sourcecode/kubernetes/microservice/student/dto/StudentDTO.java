package cl.sourcecode.kubernetes.microservice.student.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

	private Long id;

	private String name;

	private String email;

	private String password;
}
