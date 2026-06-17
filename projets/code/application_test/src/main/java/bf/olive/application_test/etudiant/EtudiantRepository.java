package bf.olive.application_test.etudiant;

import org.springframework.data.jpa.repository.JpaRepository;


public interface EtudiantRepository extends JpaRepository<Etudiant, String> {
    boolean existsByMatricule(String matricule);
}
