package bf.Sonabel.Ges_cli.Gestion.TypeClient;

import bf.Sonabel.Ges_cli.Gestion.Exception.ItemNotFoundException;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TypeClientServiceImpl implements TypeClientService {

    private final TypeClientRepository typeClientRepository;

    public TypeClientServiceImpl(TypeClientRepository typeClientRepository) {
        this.typeClientRepository = typeClientRepository;
    }

    @Override
    public List<TypeClient> getAll() {
        return typeClientRepository.findAll();
    }

    @Override
    public TypeClient getByCode(String code) {
        return typeClientRepository.findById(code)
                .orElseThrow(ItemNotFoundException::new);
    }

    @Override
    public TypeClient create(TypeClient typeClient) {
    typeClient.setCode(null);
    return typeClientRepository.save(typeClient);
}

    @Override
public TypeClient getByLibelle(String libelle) {
    return typeClientRepository.findByLibelleIgnoreCase(libelle)
           .orElseThrow(() -> new RuntimeException("TypeClient non trouvé pour ce libelle"));
}


        public void updateTypeClient(TypeClient typeClient) {
            if (typeClient.getCode() == null || !typeClientRepository.existsById(typeClient.getCode())) {
                throw new IllegalArgumentException("TypeClient not found for update");
            }
            typeClientRepository.save(typeClient);
        }
    
    // Suppression dans le service, pas dans le contrôleur
    public void deleteTypeClient(String code) {
        TypeClient existing = typeClientRepository.findById(code)
                .orElseThrow(ItemNotFoundException::new);
        typeClientRepository.delete(existing);
    }
}
