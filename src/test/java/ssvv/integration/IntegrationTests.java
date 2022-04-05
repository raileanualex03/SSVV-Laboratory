package ssvv.integration;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import java.time.LocalDate;
import java.util.Objects;

public class IntegrationTests {
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
    public void tc_SHOULD_addStudent_WHEN_addStudent_WITH_valid_inputs() {
        Student stud = new Student("2001", "New Student", 936, "test@gmail.com");

        service.addStudent(stud);

        assert(service.findStudent(stud.getID()) != null);
    }

    @Test
    public void tc_SHOULD_addTema_WHEN_addTema_WITH_valid_inputs() {
        Tema tema = new Tema("1", "description", 1, 1);

        service.addTema(tema);

        assert(Objects.equals(service.findTema(tema.getID()).getID(), tema.getID()));
    }

    @Test
    public void tc_SHOULD_addNota_WHEN_addNota_WITH_valid_inputs() {
        Nota nota = new Nota("1", "2001", "1", 10.00, LocalDate.of(2018, 3, 1));

        service.addNota(nota, "very good");

        assert(Objects.equals(service.findNota(nota.getID()).getID(), nota.getID()));
        assert(Objects.equals(service.findNota(nota.getID()).getNota(), nota.getNota()));
    }

    @Test
    public void tc_SHOULD_addAllValues_WHEN_addAllValues_WITH_valid_inputs() {
        Student stud = new Student("2001", "New Student", 936, "test@gmail.com");
        Tema tema = new Tema("1", "description", 14, 14);
        Nota nota = new Nota("1", "2001", "1", 10.00, LocalDate.of(2018, 3, 1));

        service.addStudent(stud);
        service.addTema(tema);
        service.addNota(nota, "very good");

        assert(service.findStudent(stud.getID()) != null);
        assert(Objects.equals(service.findTema(tema.getID()).getID(), tema.getID()));
        assert(Objects.equals(service.findNota(nota.getID()).getID(), nota.getID()));
        assert(Objects.equals(service.findNota(nota.getID()).getNota(), nota.getNota()));
    }
}
