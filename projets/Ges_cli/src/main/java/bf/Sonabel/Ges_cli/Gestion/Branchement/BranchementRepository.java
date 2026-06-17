package bf.Sonabel.Ges_cli.Gestion.Branchement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bf.Sonabel.Ges_cli.Gestion.Exploitation.Exploitation;


@Repository
public interface BranchementRepository extends JpaRepository<Branchement, String> {
    

    List<Branchement> findByLotAndParcelleAndRangAndSectionAndExploitation(Integer lot, Integer parcelle,
            Integer rang, String section, Exploitation exploitation);
            boolean existsByExploitation_Code(Integer codeExpl);
            List<Branchement> findByExploitation(Exploitation exploitation);
            


}
