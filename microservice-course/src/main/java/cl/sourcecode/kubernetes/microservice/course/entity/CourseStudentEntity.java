package cl.sourcecode.kubernetes.microservice.course.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course_student", uniqueConstraints = { @UniqueConstraint(columnNames = { "student_id", "course_id" }) })
public class CourseStudentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "student_id")
	private Long studentId;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof CourseStudentEntity)) return false;
		CourseStudentEntity courseStudentEntity = (CourseStudentEntity) obj;
		return this.studentId != null && this.studentId.equals(courseStudentEntity.studentId);
	}
}
