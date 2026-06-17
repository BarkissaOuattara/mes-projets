package bf.Sonabel.Ges_cli.Gestion.Tarif;

import bf.Sonabel.Ges_cli.Gestion.TypeBranchement.TypeBranch;
import bf.Sonabel.Ges_cli.Gestion.TypeClient.TypeClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarifRepository extends JpaRepository<Tarif, String> {

    boolean existsByCode(String code);

    List<Tarif> findByTypeBranchAndTypeClient(TypeBranch typeBranch, TypeClient typeClient);


}
