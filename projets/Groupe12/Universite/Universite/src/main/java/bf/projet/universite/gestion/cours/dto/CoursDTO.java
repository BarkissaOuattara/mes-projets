package bf.projet.universite.gestion.cours.dto;

import bf.projet.universite.gestion.cours.Cours;

public class CoursDTO {

    private Long id;
    private String titre;
    private String enseignant;
    private Integer volumeHoraire;
    private Boolean actif;

    public CoursDTO() {
    }

    public CoursDTO(Cours cours) {
        this.id = cours.getId();
        this.titre = cours.getTitre();
        this.enseignant = cours.getEnseignant();
        this.volumeHoraire = cours.getVolumeHoraire();
        this.actif = cours.getActif();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(String enseignant) {
        this.enseignant = enseignant;
    }

    public Integer getVolumeHoraire() {
        return volumeHoraire;
    }

    public void setVolumeHoraire(Integer volumeHoraire) {
        this.volumeHoraire = volumeHoraire;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }
}