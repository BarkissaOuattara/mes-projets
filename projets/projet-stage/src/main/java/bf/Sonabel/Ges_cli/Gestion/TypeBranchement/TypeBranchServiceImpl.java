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
        // Recherche du type de branchement par son code
        return typebranchRepository.findById(typeBranch_Code)
            .orElseThrow(ItemNotFoundException::new);
    }

    
}

