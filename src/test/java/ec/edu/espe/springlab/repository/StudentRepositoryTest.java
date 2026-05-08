package ec.edu.espe.springlab.repository;


import ec.edu.espe.springlab.domain.Student;
import org.hibernate.validator.internal.constraintvalidators.bv.AssertTrueValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;


import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    void shouldSaveAndFindStudentByEmail() {
        Student student = new Student();
        student.setFullName("Test User");
        student.setEmail("test@hotmail.com");
        student.setBirthdate(LocalDate.of(2003,8,23));
        student.setActive(true);

        studentRepository.save(student);

        var result = studentRepository.findByEmail("test@hotmail.com");
        assertThat(result).isPresent();
        assertThat(result.get().getFullName()).isEqualTo("Test User");
    }
}
