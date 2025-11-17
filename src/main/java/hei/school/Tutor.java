package hei.school;

import java.time.LocalDate;

public class Tutor extends Personne{
    private String descriptionLien;

    public Tutor(int id, String nom, String prenom, LocalDate dateNaissance, String email, String telephone, String descriptionLien) {
        super(id, nom, prenom, dateNaissance, email, telephone);
        this.descriptionLien = descriptionLien;
    }

}
