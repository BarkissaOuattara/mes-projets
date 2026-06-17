package bf.Sonabel.Ges_cli.Gestion.TypeBranchement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bf.Sonabel.Ges_cli.Gestion.Exception.ItemNotFoundException;

@Service
public class TypeBranchServiceImpl implements TypeBranchService {

    @Autowired
    private TypeBranchRepository typebranchRepository;

    @Override
    public List<TypeBranch> getAll() {
        return typebranchRepository.findAll();
    }

    @Override
    public TypeBranch getById(String typeBranch_Code) {
        return typebranchRepository.findById(typeBranch_Code)
                .orElseThrow(ItemNotFoundException::new);
    }

    public TypeBranch save(TypeBranch typeBranch) {
    if (typebranchRepository.existsById(typeBranch.getCode())) {
        throw new IllegalArgumentException("TypeBranch with this code already exists");
    }
    return typebranchRepository.save(typeBranch);
}


    @Override
    public TypeBranch update(String code, TypeBranch updatedTypeBranch) {
        TypeBranch existing = typebranchRepository.findById(code)
                .orElseThrow(ItemNotFoundException::new);

        existing.setCalibrage(updatedTypeBranch.getCalibrage());
        existing.setLibelle(updatedTypeBranch.getLibelle());
        existing.setFraisrcvrt(updatedTypeBranch.getFraisrcvrt());
        existing.setFraisremise(updatedTypeBranch.getFraisremise());
        existing.setFraisrepose(updatedTypeBranch.getFraisrepose());
        existing.setFraisetal(updatedTypeBranch.getFraisetal());
        existing.setForfTypebr(updatedTypeBranch.getForfTypebr());
        existing.setForfMntBran(updatedTypeBranch.getForfMntBran());
        existing.setForfGle(updatedTypeBranch.getForfGle());

        return typebranchRepository.save(existing);
    }

    @Override
    public void delete(String code) {
        TypeBranch existing = typebranchRepository.findById(code)
                .orElseThrow(ItemNotFoundException::new);
        typebranchRepository.delete(existing);
    }

    public TypeBranch findByCode(String code) {
    return typebranchRepository.findByCodeIgnoreCase(code)
            .orElseThrow(() -> new RuntimeException("Type de branchement non trouvé"));
}

}
