package hei.school;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Note {
    private Student etudiant;
    private Exam examen;
    private List<NoteRevision> historique;

    public Note(Student etudiant, Exam examen, double valeurInitiale, Instant dateSaisie) {
        this.etudiant = etudiant;
        this.examen = examen;
        this.historique = new ArrayList<>();
        this.historique.add(new NoteRevision(valeurInitiale, dateSaisie, "Saisie initiale."));
    }

    public void modifierValeur(double nouvelleValeur, Instant horodatage, String motif) {
        this.historique.add(new NoteRevision(nouvelleValeur, horodatage, motif));
    }

    public Student getEtudiant() { return etudiant; }
    public Exam getExamen() { return examen; }
    public List<NoteRevision> getHistorique() { return historique; }
}
