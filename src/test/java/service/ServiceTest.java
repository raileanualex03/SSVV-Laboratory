package service;

import domain.Student;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class ServiceTest
{
    /**
     * Rigorous Test :-)
     */

    String filenameStudent = "fisiere/Studenti.xml";
    String filenameTema = "fisiere/Teme.xml";
    String filenameNota = "fisiere/Note.xml";


    StudentValidator studentValidator;
    TemaValidator temaValidator;

    StudentXMLRepo studentXMLRepository;
    TemaXMLRepo temaXMLRepository;
    NotaValidator notaValidator;
    NotaXMLRepo notaXMLRepository;
    Service service;

    @Before
    public void setup() {
         studentValidator = new StudentValidator();
         temaValidator = new TemaValidator();
         studentXMLRepository = new StudentXMLRepo(filenameStudent);
         temaXMLRepository = new TemaXMLRepo(filenameTema);
         notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
         notaXMLRepository = new NotaXMLRepo(filenameNota);
         service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

    }

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void tc_SHOULD_add_WHEN_addStudent_WITH_valid_student() {
        Student stud = new Student("2001", "New Student", 936, "test@gmail.com");

        service.addStudent(stud);

        assert(service.findStudent(stud.getID()) != null);
    }

    @Test(expected=ValidationException.class)
    public void tc_SHOULD_throwError_WHEN_addStudent_WITH_invalid_id() {
        Student stud = new Student("", "New Student", 936, "test@gmail.com");

        service.addStudent(stud);
    }

    @Test(expected=ValidationException.class)
    public void tc_SHOULD_throwError_WHEN_addStudent_WITH_invalid_name() {
        Student stud = new Student("1", "", 936, "test@gmail.com");

        service.addStudent(stud);
    }
}
