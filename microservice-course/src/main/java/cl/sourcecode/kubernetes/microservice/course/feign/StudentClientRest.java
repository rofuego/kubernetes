package cl.sourcecode.kubernetes.microservice.course.feign;

import cl.sourcecode.kubernetes.microservice.course.dto.StudentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice-student", url = "localhost:8002")
public interface StudentClientRest {

    @GetMapping("/students/{studentId}")
    public StudentDto getStudent(@PathVariable Long studentId);

    @PostMapping("/students")
    public StudentDto saveStudent(@RequestBody StudentDto studentDTO);

    @GetMapping("/students/by-ids")
    public List<StudentDto> getStudentsByIds(@RequestParam List<Long> ids);
}
