package tn.esprit.tpfoyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tpfoyer.entity.Reservation;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Ajouter cette méthode pour trouver des réservations par année universitaire
    List<Reservation> findByAnneeUniversitaire(Date anneeUniversitaire);
}
