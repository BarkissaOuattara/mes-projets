package bf.projet.universite.gestion.cours.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NewCoursRequest {

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    @NotBlank(message = "Le nom de l'enseignant est obligatoire")
    private String enseignant;

    @NotNull(message = "Le volume horaire est obligatoire")
    @Min(value = 1, message = "Le volume horaire doit être supérieur à 0")
    private Integer volumeHoraire;

    @NotNull(message = "Le champ actif est obligatoire")
    private Boolean actif;

    public NewCoursRequest() {
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