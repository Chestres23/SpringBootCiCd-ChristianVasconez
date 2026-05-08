package ec.edu.espe.springlab.service;

import ec.edu.espe.springlab.domain.Student;
import ec.edu.espe.springlab.dto.StudentCreateRequest;
import ec.edu.espe.springlab.repository.StudentRepository;
import ec.edu.espe.springlab.service.impl.StudentServiceImpl;
import ec.edu.espe.springlab.web.advice.ConflictException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import ec.edu.espe.springlab.service.impl.StudentServiceImpl;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import({StudentServiceImpl.class})
public class StudentServiceTest {
    @Autowired
    private StudentService service;

    @Autowired
    private StudentRepository repository;

    @Test
    void shouldNotAllowDuplicatedName(){
        Student existing = new Student();
        existing.setFullName("Existing");
        existing.setEmail("duplicate@email.com");
        existing.setBirthdate(LocalDate.of(1990, 1, 1));
        existing.setActive(true);
        repository.save(existing);

        StudentCreateRequest req = new StudentCreateRequest();
        req.setFullName("New User");
        req.setEmail("duplicate@email.com");
        req.setBirthDate(LocalDate.of(2004,8,18));

        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ConflictException.class);
    }
}
