package bf.Sonabel.Ges_cli.Gestion.Branchement.dto;

import bf.Sonabel.Ges_cli.Gestion.Branchement.Branchement;
import bf.Sonabel.Ges_cli.Gestion.Exploitation.dto.ExploitationDTO;
import bf.Sonabel.Ges_cli.Gestion.TypeBranchement.dto.TypeBranchDTO;

public record BranchementDTO(
    String code,
    String section,
    Integer lot,
    Integer parcelle,
    Integer rang,
    String etat,
    Integer avoirs,
    String nom,
    String prenom,
    String tel,
    String rue,
    ExploitationDTO exploitation,
    TypeBranchDTO typeBranchement
) {
    public BranchementDTO(Branchement branchement) {
        this(
            branchement.getCode(),
            branchement.getSection(),
            branchement.getLot(),
            branchement.getParcelle(),
            branchement.getRang(),
            branchement.getEtat(),
            branchement.getAvoirs(),
            branchement.getNom(),
            branchement.getPrenom(),
            branchement.getTel(),
            branchement.getRue(),
            new ExploitationDTO(branchement.getExploitation()),
            new TypeBranchDTO(branchement.getTypeBranch())
        );
    }
}
