package bf.Sonabel.Ges_cli.Gestion.Tarif.dto;

import bf.Sonabel.Ges_cli.Gestion.Tarif.Tarif;
import bf.Sonabel.Ges_cli.Gestion.TypeBranchement.dto.TypeBranchDTO;
import bf.Sonabel.Ges_cli.Gestion.TypeClient.dto.TypeClientDTO;

public record TarifDTO(
    String code,
    String libelle,
    Integer reglDisjonct,
    Integer  puissance,
    Integer  tarifHp,
    Integer  tarifHpt,
    Integer tarifHc,
    Integer loccpt,
    Integer locposte,
    Integer loctransf,
    Integer mntAvCons,
    Integer fraisPol,
    Integer fraisTimb,
    Integer mntPrimFix,
    Integer mntRedev,
    TypeBranchDTO typeBranchement,
    TypeClientDTO typeClient

){
    public TarifDTO(Tarif tarif) {
        this(
            tarif.getCode(),
            tarif.getLibelle(),
            tarif.getReglDisjonct(),
            tarif.getPuissance(),
            tarif.getTarifHp(),
            tarif.getTarifHpt(),
            tarif.getTarifHc(),
            tarif.getLoccpt(),
            tarif.getLocposte(),
            tarif.getLoctransf(),
            tarif.getMntAvCons(),
            tarif.getFraisPol(),
            tarif.getFraisTimb(),
            tarif.getMntPrimFix(),
            tarif.getMntRedev(),
            new TypeBranchDTO(tarif.getTypeBranch()),
            new TypeClientDTO(tarif.getTypeClient())

        );
    }
}
