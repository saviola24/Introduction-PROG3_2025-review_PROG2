import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GradeManagerTest {

    private GradeManager gradeManager;
    private Student student1;
    private Course prog2;
    private Exam examProg2_1;
    private Exam examProg2_2;

    @BeforeEach
    void setUp() {
        gradeManager = new GradeManager();

        Tutor tutor1 = new Tutor(1, "Smith", "Alice", LocalDate.of(1980, 5, 10), "a.smith@mail.com", "0123456789", "Mère");
        Teacher teacher1 = new Teacher(10, "Dupont", "Marc", LocalDate.of(1975, 1, 1), "m.dupont@uni.com", "9876543210", "back-end");
        student1 = new Student(100, "Martin", "Paul", LocalDate.of(2000, 1, 1), "p.martin@uni.com", "1122334455", "L3-A", tutor1);

        prog2 = new Course(20, "PROG2", 5, teacher1);

        ZonedDateTime dateExam1 = ZonedDateTime.of(2025, 11, 1, 10, 0, 0, 0, ZoneId.of("Europe/Paris"));
        examProg2_1 = new Exam(200, "Contrôle 1 PROG2", prog2, dateExam1, 2);

        ZonedDateTime dateExam2 = ZonedDateTime.of(2025, 12, 1, 14, 0, 0, 0, ZoneId.of("Europe/Paris"));
        examProg2_2 = new Exam(201, "Projet Final PROG2", prog2, dateExam2, 3);
    }

    @Test
    void testGetExamGrade_NoteModifieeDansLeTemps() {
        Instant t1_saisie = Instant.parse("2025-11-01T10:00:00Z");
        Note note1 = new Note(student1, examProg2_1, 10.0, t1_saisie);
        gradeManager.ajouterNote(note1);

        Instant t2_modificationA = Instant.parse("2025-11-05T15:30:00Z");
        note1.modifierValeur(15.0, t2_modificationA, "Erreur.");

        Instant t3_modificationB = Instant.parse("2025-11-10T08:00:00Z");
        note1.modifierValeur(12.0, t3_modificationB, "Nouvelle relecture.");

        Instant t_consultation1 = Instant.parse("2025-11-06T00:00:00Z");
        assertEquals(15.0, gradeManager.getExamGrade(examProg2_1, student1, t_consultation1), 0.001);

        Instant t_consultation2 = Instant.parse("2025-11-15T00:00:00Z");
        assertEquals(12.0, gradeManager.getExamGrade(examProg2_1, student1, t_consultation2), 0.001);
    }

    @Test
    void testGetCourseGrade_CalculMoyennePonderee() {
        Instant t_final = Instant.parse("2025-12-15T00:00:00Z");

        Note note1 = new Note(student1, examProg2_1, 10.0, Instant.parse("2025-11-01T10:00:00Z")); // Coeff 2
        gradeManager.ajouterNote(note1);

        Note note2 = new Note(student1, examProg2_2, 15.0, Instant.parse("2025-12-01T14:00:00Z")); // Coeff 3
        gradeManager.ajouterNote(note2);

        // Attendu : (10 * 2 + 15 * 3) / 5 = 13.0
        double result = gradeManager.getCourseGrade(prog2, student1, t_final);
        assertEquals(13.0, result, 0.001);
    }

    @Test
    void testGetCourseGrade_AvecModificationEtTemps() {
        Instant t1 = Instant.parse("2025-11-01T10:00:00Z");
        Note note1 = new Note(student1, examProg2_1, 10.0, t1); // Note initiale 10
        gradeManager.ajouterNote(note1);

        Instant t2 = Instant.parse("2025-12-01T14:00:00Z");
        Note note2 = new Note(student1, examProg2_2, 15.0, t2); // Note 15
        gradeManager.ajouterNote(note2);

        Instant t3 = Instant.parse("2025-12-05T09:00:00Z");
        note1.modifierValeur(20.0, t3, "Correction."); // Note 1 devient 20

        // Avant T3 : (10*2 + 15*3)/5 = 13.0
        Instant t_consultation1 = Instant.parse("2025-12-04T00:00:00Z");
        assertEquals(13.0, gradeManager.getCourseGrade(prog2, student1, t_consultation1), 0.001);

        // Après T3 : (20*2 + 15*3)/5 = 17.0
        Instant t_consultation2 = Instant.parse("2025-12-10T00:00:00Z");
        assertEquals(17.0, gradeManager.getCourseGrade(prog2, student1, t_consultation2), 0.001);
    }
}