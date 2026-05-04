package ec.edu.espe.springlab.web.controller;
import ec.edu.espe.springlab.dto.StudentCreateRequest;
import ec.edu.espe.springlab.dto.StudentResponse;
import ec.edu.espe.springlab.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    //Inyeccion de dependencia
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    //Crear un estudiante
    @PostMapping
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    //Obtener un estudiante por ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    //Obtener todos los estudiantes
    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        return ResponseEntity.ok(service.List());
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<StudentResponse> deactivateStudent(@PathVariable Long id) {
        return ResponseEntity.ok(service.desactivate(id));
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<StudentResponse> reactivateStudent(@PathVariable Long id) {
        return ResponseEntity.ok(service.reactivate(id));
    }
}