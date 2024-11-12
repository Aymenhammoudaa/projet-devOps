package tn.esprit.tpfoyer.repository;

import tn.esprit.tpfoyer.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
}
