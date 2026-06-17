package bf.Sonabel.Ges_cli.Gestion.Branchement;

import bf.Sonabel.Ges_cli.Gestion.Branchement.dto.NewBranchementRequest;
import java.util.List;

public interface BranchementService {
    List<Branchement> getAll();  // Retourner des Branchement au lieu de BranchementDTO
    Branchement createBranchement(NewBranchementRequest request);
    void updateBranchement(Branchement branchement);
    void deleteBranchement(String code);
    Branchement findById(String code);
    boolean existsByCodeExpl(Integer codeExpl);
    List<Branchement> findByParams(Integer lot, Integer parcelle, Integer rang, String section, Integer codeExpl);

    
}
