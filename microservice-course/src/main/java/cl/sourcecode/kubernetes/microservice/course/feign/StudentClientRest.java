package cl.sourcecode.kubernetes.microservice.course.feign;

import cl.sourcecode.kubernetes.microservice.course.dto.StudentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice-student", url = "localhost:8002")
public interface StudentClientRest {

    @GetMapping("/students/{studentId}")
    public StudentDTO getStudent(@PathVariable Long studentId);

    @PostMapping("/students")
    public StudentDTO saveStudent(@RequestBody StudentDTO studentDTO);

    @GetMapping("/students/by-ids")
    public List<StudentDTO> getStudentsByIds(@RequestParam List<Long> ids);
}
