package bf.olive.application_test.etudiant;

import java.util.List;

import bf.olive.application_test.etudiant.dto.EtudiantFull;
import bf.olive.application_test.etudiant.dto.NewEtudiantRequest;

public interface EtudiantService {
    List<EtudiantFull> getAll();
    Etudiant findById(String id);
    Etudiant createEtudiant(NewEtudiantRequest request);
    void updateEtudiant(Etudiant etudiant);
    void deleteEtudiant(String id);
}