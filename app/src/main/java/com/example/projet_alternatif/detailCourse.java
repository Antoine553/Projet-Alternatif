package com.example.projet_alternatif;

public class detailCourse {
    private String nom;
    private String date_creation;
    private String distance;
    private String Id;

    public detailCourse(String nom, String date_creation, String distance, String Id) {
        this.nom = nom;
        this.date_creation = date_creation;
        this.distance = distance;
        this.Id = Id;
    }

    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}

    public String getDate_creation() {return date_creation;}
    public void setDate_creation(String date_creation) {this.date_creation = date_creation;}

    public String getDistance() {return distance;}
    public void setDistance(String distance) {this.distance = distance;}

    public String getId() {return Id;}
    public void setId(String id) {Id = id;}
}
