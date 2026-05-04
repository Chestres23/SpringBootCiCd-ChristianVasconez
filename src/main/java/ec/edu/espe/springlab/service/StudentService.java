package ec.edu.espe.springlab.service;

import ec.edu.espe.springlab.dto.StudentCreateRequest;
import ec.edu.espe.springlab.dto.StudentResponse;

import java.util.List;

public interface StudentService {
    //Crear un estudiante
    StudentResponse create(StudentCreateRequest request);

    //Buscar estudiante por ID
    StudentResponse getById(Long id);

    //Listar todos los estudiante
    List<StudentResponse> List();

    //Cambiar el estado
    StudentResponse desactivate(Long id);

    //Reactivar estudiante
    StudentResponse reactivate(Long id);
}
