package cl.sourcecode.kubernetes.microservice.course.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course_student", uniqueConstraints = {@UniqueConstraint(columnNames = {"student_id", "course_id"})})
public class CourseStudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id")
    private Long studentId;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CourseStudentEntity courseStudentEntity)) return false;
        return this.studentId != null && this.studentId.equals(courseStudentEntity.studentId);
    }
}
