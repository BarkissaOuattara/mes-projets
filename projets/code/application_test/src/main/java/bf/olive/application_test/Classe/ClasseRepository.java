package bf.olive.application_test.Classe;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
    

@Repository
public interface ClasseRepository extends JpaRepository<Classe, String> {

}
