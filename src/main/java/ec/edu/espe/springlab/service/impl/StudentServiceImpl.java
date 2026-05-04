package ec.edu.espe.springlab.service.impl;

import ec.edu.espe.springlab.domain.Student;
import ec.edu.espe.springlab.dto.StudentCreateRequest;
import ec.edu.espe.springlab.dto.StudentResponse;
import ec.edu.espe.springlab.repository.StudentRepository;
import ec.edu.espe.springlab.service.StudentService;
import ec.edu.espe.springlab.web.advice.ConflictException;
import ec.edu.espe.springlab.web.advice.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    //Inyección de Dependencia
    private final StudentRepository repo;

    public StudentServiceImpl(StudentRepository repo) {
        this.repo = repo;
    }

    @Override
    public StudentResponse create(StudentCreateRequest request) {
        if (repo.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email ya está registrado");
        }

        Student s = new Student();
        s.setFullName(request.getFullName());
        s.setEmail(request.getEmail());
        s.setBirthdate(request.getBirthDate());
        s.setActive(true);

        Student saved = repo.save(s);
        return toResponse(saved);
    }

    @Override
    public StudentResponse getById(Long id) {
        Student s = repo.findById(id).orElseThrow(() ->new NotFoundException("Estudiante no encontrado"));
        return toResponse(s);
    }

    @Override
    public List<StudentResponse> List() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public StudentResponse desactivate(Long id) {
        Student s = repo.findById(id).orElseThrow(() ->new NotFoundException("Estudiante no encontrado"));
        s.setActive(false);
        return toResponse(repo.save(s));
    }

    @Override
    public StudentResponse reactivate(Long id) {
        Student s = repo.findById(id).orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
        s.setActive(true);
        return toResponse(repo.save(s));
    }

    //Mapeo interno entidad -> DTO de salida
    private StudentResponse toResponse(Student s) {
        StudentResponse r =  new StudentResponse();
        r.setId(s.getId());
        r.setFullName(s.getFullName());
        r.setEmail(s.getEmail());
        r.setBirthDate(s.getBirthdate());
        r.setActive(s.getActive());
        return r;
    }

}
