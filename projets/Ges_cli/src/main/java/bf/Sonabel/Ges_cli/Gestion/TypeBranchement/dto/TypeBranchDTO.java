package bf.Sonabel.Ges_cli.Gestion.TypeBranchement.dto;

import bf.Sonabel.Ges_cli.Gestion.TypeBranchement.TypeBranch;

public record TypeBranchDTO (
    String code,
    String calibrage,
    String libelle,
    Integer fraisrcvrt,
    Integer fraisremise,
    Integer fraisrepose,
    Integer fraisetal,
    Integer forfTypebr,
    Integer forfMntBran,
    Integer forfGle
    ){
        public TypeBranchDTO(TypeBranch typeBranch){
            this(
                typeBranch.getCode(),
                typeBranch.getCalibrage(),
                typeBranch.getLibelle(),
                typeBranch.getFraisrcvrt(),
                typeBranch.getFraisremise(),
                typeBranch.getFraisrepose(),
                typeBranch.getFraisetal(),
                typeBranch.getForfTypebr(),
                typeBranch.getForfMntBran(),
                typeBranch.getForfGle()
            );
        }
    }
