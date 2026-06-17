package bf.olive.application_test.etudiant;

import java.util.List;
//import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import bf.olive.application_test.Classe.ClasseService;
import bf.olive.application_test.etudiant.dto.EtudiantFull;
import bf.olive.application_test.etudiant.dto.NewEtudiantRequest;
import bf.olive.application_test.exception.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EtudiantServiceImpl implements EtudiantService {
    private final EtudiantRepository etudiantRepository;
    private final ClasseService classeService;
    
    @Override
    public List<EtudiantFull> getAll() {
        return etudiantRepository.findAll().stream().map(etudiant -> new EtudiantFull(etudiant)).collect(Collectors.toList());
        
    }

    @Override
    public Etudiant createEtudiant(NewEtudiantRequest request) {
        var classeOpt = classeService.findByIdOptional(request.classe().getId());
        if(classeOpt.isEmpty()) throw new ItemNotFoundException();
        Etudiant etudiant = Etudiant.builder()
        .matricule(request.matricule())
        .nom(request.nom())
        .prenom(request.prenom())
        .classe(classeOpt.get())
        .build();
        return etudiantRepository.save(etudiant);
    }

    @Override
    public void updateEtudiant(Etudiant etudiant) {
        // Récupérer l'étudiant existant dans la base de données
        Etudiant sEtudiant = etudiantRepository.findById(etudiant.getId()).orElseThrow(
            () -> new ItemNotFoundException()
        );

        // Vérification si le matricule a changé
        if (!sEtudiant.getMatricule().equals(etudiant.getMatricule())) {
            // Vérification si le nouveau matricule existe déjà
            boolean matriculeExists = etudiantRepository.existsByMatricule(etudiant.getMatricule());
            if (matriculeExists) {
                throw new DataIntegrityViolationException("Le matricule existe déjà");
            }
        }

        // Mise à jour de l'étudiant avec les nouvelles informations
        sEtudiant.update(etudiant);

        // Sauvegarde de l'étudiant mis à jour dans la base de données
        etudiantRepository.save(sEtudiant);
    }


    @Override
    public void deleteEtudiant(String id) {
        //Optional<Etudiant> optionalEtudiant = etudiantRepository.findById(id);
        //if(optionalEtudiant.isPresent()){
        //    Etudiant savedEtudiant = optionalEtudiant.get();
        //    etudiantRepository.delete(savedEtudiant);
        //}else{
        //    throw new ItemNotFoundException();
        //}
        Etudiant savedEtudiant = etudiantRepository.findById(id).orElseThrow(
            () -> new ItemNotFoundException()
        );
        etudiantRepository.delete(savedEtudiant);
    }

    @Override
    public Etudiant findById(String id) {
        //Optional<Etudiant> optionalEtudiant = etudiantRepository.findById(id);
        //if(optionalEtudiant.isPresent()){
        //    return optionalEtudiant.get();
        //}else{
        //    throw new ItemNotFoundException();
        //}
        return etudiantRepository.findById(id).orElseThrow(() -> new ItemNotFoundException());
    }

}
