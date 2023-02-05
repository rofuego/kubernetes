package cl.sourcecode.kubernetes.microservice.course.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private List<CourseStudentEntity> studentsIds;

    public void addStudent(CourseStudentEntity courseStudentEntity) {
        studentsIds.add(courseStudentEntity);
    }

    public void removeStudent(CourseStudentEntity courseStudentEntity) {
        studentsIds.remove(courseStudentEntity);
    }

}
