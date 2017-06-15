package com.example.admin.applicationsante.database.modele;

import java.util.List;

import com.example.admin.applicationsante.database.datasource.Modele;
import com.example.admin.applicationsante.database.datasource.e.Type;

import static com.example.admin.applicationsante.database.datasource.Modele.DataBase;
import static com.example.admin.applicationsante.database.datasource.Modele.Table;

@Table("USER")
@DataBase("sante.db")
public class User extends Modele<User> {

    private String nom;

    @Columne(value = "NOM", type = Type.String)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCreateDataBase() {
        return "CREATE TABLE " + getTable() + "("
                + "ID" + " Integer PRIMARY KEY AUTOINCREMENT, "
                + "NOM Text NOT NULL "
                + ");";
    }

    @Override
    public User build(int id, List<String> valeurs) {
        User user = new User();
        user.setId(id);
        user.setNom(valeurs.get(0));
        return user;
    }

}
