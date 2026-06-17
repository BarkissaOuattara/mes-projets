package bf.Sonabel.Ges_cli.Gestion.TypeBranchement;

import java.util.List;

public interface TypeBranchService {

    List<TypeBranch>getAll();

    TypeBranch getById(String typeBranch_Code);
    TypeBranch save(TypeBranch typeBranch);
    TypeBranch update(String code, TypeBranch typeBranch);
    void delete(String code);

    public TypeBranch findByCode(String code);

    
}