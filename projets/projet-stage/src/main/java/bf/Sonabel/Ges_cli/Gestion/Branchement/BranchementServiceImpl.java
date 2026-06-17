package bf.Sonabel.Ges_cli.Gestion.Branchement;

import bf.Sonabel.Ges_cli.Gestion.Branchement.dto.NewBranchementRequest;
import bf.Sonabel.Ges_cli.Gestion.Exception.BranchementAlreadyExistsException;
import bf.Sonabel.Ges_cli.Gestion.Exception.ItemNotFoundException;
import bf.Sonabel.Ges_cli.Gestion.Exploitation.Exploitation;
import bf.Sonabel.Ges_cli.Gestion.Exploitation.ExploitationService;
import bf.Sonabel.Ges_cli.Gestion.TypeBranchement.TypeBranch;
import bf.Sonabel.Ges_cli.Gestion.TypeBranchement.TypeBranchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchementServiceImpl implements BranchementService {

    private final BranchementRepository branchementRepository;
    private final ExploitationService exploitationService;
    private final TypeBranchService typeBranchService;

    public BranchementServiceImpl(
            BranchementRepository branchementRepository,
            ExploitationService exploitationService,
            TypeBranchService typeBranchService
    ) {
        this.branchementRepository = branchementRepository;
        this.exploitationService = exploitationService;
        this.typeBranchService = typeBranchService;
    }

    @Override
    public List<Branchement> getAll() {
        return branchementRepository.findAll();
    }

    @Override
    public Branchement createBranchement(NewBranchementRequest request) {
        Exploitation exploitation = exploitationService.getByCode(request.getExploitation());
        TypeBranch typeBranch = typeBranchService.getById(request.getTypeBranchCode());

        // Vérification de l’unicité du branchement
        List<Branchement> existing = branchementRepository
                .findByLotAndParcelleAndRangAndSectionAndExploitation(
                        request.getLot(),
                        request.getParcelle(),
                        request.getRang(),
                        request.getSection(),
                        exploitation
                );

        if (!existing.isEmpty()) {
            throw new BranchementAlreadyExistsException("Ce branchement existe déjà !");
        }

        Branchement newBranchement = Branchement.builder()
                .exploitation(exploitation)
                .section(request.getSection())
                .lot(request.getLot())
                .parcelle(request.getParcelle())
                .rang(request.getRang())
                .etat(request.getEtat())
                .typeBranch(typeBranch)
                .avoirs(request.getAvoirs())
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .tel(request.getTel())
                .rue(request.getRue())
                .build();

        return branchementRepository.save(newBranchement);
    }

    @Override
    public void updateBranchement(Branchement branchement) {
        Branchement existing = branchementRepository.findById(branchement.getCode())
                .orElseThrow(ItemNotFoundException::new);

        existing.update(branchement);
        branchementRepository.save(existing);
    }

    @Override
    public void deleteBranchement(String code) {
        Branchement branchement = branchementRepository.findById(code)
                .orElseThrow(ItemNotFoundException::new);
        branchementRepository.delete(branchement);
    }

    @Override
    public Branchement findById(String code) {
        return branchementRepository.findById(code)
                .orElseThrow(ItemNotFoundException::new);
    }

    @Override
    public boolean existsByCodeExpl(Integer codeExpl) {
        return branchementRepository.existsByExploitation_Code(codeExpl);
    }

    @Override
    public List<Branchement> findByParams(Integer lot, Integer parcelle, Integer rang, String section, Integer codeExpl) {
        Exploitation exploitation = exploitationService.getByCode(codeExpl);
        return branchementRepository.findByLotAndParcelleAndRangAndSectionAndExploitation(
                lot,
                parcelle,
                rang,
                section,
                exploitation
        );
    }
    }
    

