package bf.Sonabel.Ges_cli.Gestion.TypeBranchement;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    
@Repository
public interface TypeBranchRepository extends JpaRepository<TypeBranch, String>{
        Optional<TypeBranch> findByCodeIgnoreCase(String code);

    
}
    
