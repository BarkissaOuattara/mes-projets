package bf.Sonabel.Ges_cli.Gestion.Abonnement;

import bf.Sonabel.Ges_cli.Gestion.Branchement.Branchement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AbonnementRepository extends JpaRepository<Abonnement, String> {
    boolean existsByBranchementAndDateFinIsNull(Branchement branchement);
    Optional<Abonnement> findByBranchementAndNumPolAndDateFinIsNullAndStatut(Branchement branchement, String numPol, StatutAbonnement statut);
    List<Abonnement> findTop10ByOrderByDateDebutDesc();
        Optional<Abonnement> findByNumPolAndStatut(String numPol, StatutAbonnement statut);

        boolean existsByNumPolAndDateFinIsNull(String numPol);

        long countByDateResiliationNotNull();

         boolean existsByBranchementAndStatut(Branchement branchement, StatutAbonnement statut);

         Optional<Abonnement> findByBranchementAndStatutAndDateFinIsNull(Branchement branchement, StatutAbonnement statut);

         boolean existsByNumPolAndStatut(String numPol, StatutAbonnement statut);
        // Abonnements créés à une date donnée
    long countByDateDebut(LocalDate date);

    // Abonnements créés entre deux dates
    long countByDateDebutBetween(LocalDate debut, LocalDate fin);

    // Résiliations faites à une date donnée
    long countByDateResiliation(LocalDate date);

    // Résiliations faites entre deux dates
    long countByDateResiliationBetween(LocalDate debut, LocalDate fin);


    Optional<Abonnement> findByBranchementAndStatut(Branchement branchement, StatutAbonnement statut);

}
