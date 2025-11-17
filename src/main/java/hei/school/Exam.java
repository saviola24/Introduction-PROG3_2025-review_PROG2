package hei.school;

import java.time.ZonedDateTime;

public class Exam {
    private int id;
    private String titre;
    private Course cours;
    private ZonedDateTime dateHeure;
    private int coefficient;

    public Exam(int id, String titre, Course cours, ZonedDateTime dateHeure, int coefficient) {
        this.id = id;
        this.titre = titre;
        this.cours = cours;
        this.dateHeure = dateHeure;
        this.coefficient = coefficient;
    }

    public Course getCours() { return cours; }
    public int getCoefficient() { return coefficient; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return id == exam.id;
    }
}
