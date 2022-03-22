package service;

import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.util.Objects;

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
    public void tc_SHOULD_throwError_WHEN_addStudent_WITH_empty_id() {
        Student stud = new Student("", "New Student", 936, "test@gmail.com");

        service.addStudent(stud);
    }

    @Test(expected=ValidationException.class)
    public void tc_SHOULD_throwError_WHEN_addStudent_WITH_empty_name() {
        Student stud = new Student("1", "", 936, "test@gmail.com");

        service.addStudent(stud);
    }

    @Test(expected=ValidationException.class)
    public void tc_SHOULD_throwError_WHEN_addStudent_WITH_empty_email() {
        Student stud = new Student("1", "", 936, "");

        service.addStudent(stud);
    }

    @Test(expected=ValidationException.class)
    public void tc_SHOULD_throwError_WHEN_addStudent_WITH_null_id() {
        Student stud = new Student(null, "New Student", 936, "test@gmail.com");

        service.addStudent(stud);
    }

    @Test(expected=ValidationException.class)
    public void tc_SHOULD_throwError_WHEN_addStudent_WITH_null_name() {
        Student stud = new Student("1", null, 936, "test@gmail.com");

        service.addStudent(stud);
    }

    @Test(expected=ValidationException.class)
    public void tc_SHOULD_throwError_WHEN_addStudent_WITH_null_email() {
        Student stud = new Student("1", null, 936, null);

        service.addStudent(stud);
    }

    @Test(expected=ValidationException.class)
    public void tc_SHOULD_throwError_WHEN_addStudent_WITH_invalid_group() {
        Student stud = new Student("1", "name", -1, "test@gmail.com");

        service.addStudent(stud);
    }

    @Test
    public void tc_SHOULD_notAddAnotherStudent_WHEN_addStudent_WITH_existing_student_id() {
        Student stud = new Student("1", "name", 936, "test@gmail.com");

        service.addStudent(stud);

        Student returnedStudent = service.addStudent(stud);

        assert(Objects.equals(returnedStudent.getID(), stud.getID()));
    }


    @Test
    public void tc_SHOULD_addTema_WHEN_addTema_WITH_valid_tema() {
        Tema tema = new Tema("1", "description", 1, 1);

        service.addTema(tema);

        assert(Objects.equals(service.findTema(tema.getID()).getID(), tema.getID()));
    }

    @Test(expected=ValidationException.class)
    public void tc_SHOULD_throwError_WHEN_addTema_WITH_empty_nr() {
        Tema tema = new Tema("", "New Tema", 936, 1);

        service.addTema(tema);
    }

    @Test(expected=ValidationException.class)
    public void tc_SHOULD_throwError_WHEN_addTema_WITH_null_nr() {
        Tema tema = new Tema(null, "New Tema", 936, 1);

        service.addTema(tema);
    }

    @Test(expected=ValidationException.class)
    public void tc_SHOULD_throwError_WHEN_addTema_WITH_empty_description() {
        Tema tema = new Tema("1", "", 936, 1);

        service.addTema(tema);
    }

    @Test(expected=ValidationException.class)
    public void tc_SHOULD_throwError_WHEN_addTema_WITH_invalid_negative_deadline() {
        Tema tema = new Tema(null, "New Tema", -1, 1);

        service.addTema(tema);
    }

    @Test(expected=ValidationException.class)
    public void tc_SHOULD_throwError_WHEN_addTema_WITH_invalid_too_big_deadline() {
        Tema tema = new Tema("1", "descr", 15, 1);

        service.addTema(tema);
    }

    @Test(expected=ValidationException.class)
    public void tc_SHOULD_throwError_WHEN_addTema_WITH_invalid_negative_primire() {
        Tema tema = new Tema("1", "New Tema", -1, 1);

        service.addTema(tema);
    }

    @Test(expected=ValidationException.class)
    public void tc_SHOULD_throwError_WHEN_addTema_WITH_invalid_too_big_primire() {
        Tema tema = new Tema("1", "descr", 15, 1);

        service.addTema(tema);
    }

    @Test
    public void tc_SHOULD_notAddAnotherTema_WHEN_addTema_WITH_existing_id() {
        Tema tema = new Tema("1", "name", 1, 1);

        service.addTema(tema);

        Tema returnedTema = service.addTema(tema);

        assert(Objects.equals(returnedTema.getID(), tema.getID()));
    }
}
