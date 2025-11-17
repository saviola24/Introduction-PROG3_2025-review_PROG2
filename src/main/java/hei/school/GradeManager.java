package hei.school;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GradeManager {
    private List<Note> toutesLesNotes = new ArrayList<>();

    public void ajouterNote(Note note) {
        this.toutesLesNotes.add(note);
    }

    public double getExamGrade(Exam exam, Student student, Instant t) {
        Note note = toutesLesNotes.stream()
                .filter(n -> n.getEtudiant().equals(student) && n.getExamen().equals(exam))
                .findFirst()
                .orElse(null);

        if (note == null) {
            return 0.0;
        }

        return note.getHistorique().stream()
                .filter(revision -> !revision.getHorodatage().isAfter(t))
                .sorted((r1, r2) -> r2.getHorodatage().compareTo(r1.getHorodatage()))
                .map(NoteRevision::getNouvelleValeur)
                .findFirst()
                .orElse(0.0);
    }

    public double getCourseGrade(Course course, Student student, Instant t) {
        double sommeNotesPonderees = 0.0;
        int sommeCoefficients = 0;

        List<Note> notesDuCours = toutesLesNotes.stream()
                .filter(n -> n.getEtudiant().equals(student) && n.getExamen().getCours().equals(course))
                .collect(Collectors.toList());

        if (notesDuCours.isEmpty()) {
            return 0.0;
        }

        for (Note note : notesDuCours) {
            Exam exam = note.getExamen();
            int coeff = exam.getCoefficient();

            double noteALInstantT = getExamGrade(exam, student, t);

            sommeNotesPonderees += (noteALInstantT * coeff);
            sommeCoefficients += coeff;
        }

        return (sommeCoefficients == 0) ? 0.0 : sommeNotesPonderees / sommeCoefficients;
    }
}
