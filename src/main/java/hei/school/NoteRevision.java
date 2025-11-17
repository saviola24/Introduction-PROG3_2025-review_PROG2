package hei.school;

import java.time.Instant;

public class NoteRevision {
    private double nouvelleValeur;
    private Instant horodatage;
    private String motif;

    public NoteRevision(double nouvelleValeur, Instant horodatage, String motif) {
        this.nouvelleValeur = nouvelleValeur;
        this.horodatage = horodatage;
        this.motif = motif;
    }

    public double getNouvelleValeur() { return nouvelleValeur; }
    public Instant getHorodatage() { return horodatage; }
}