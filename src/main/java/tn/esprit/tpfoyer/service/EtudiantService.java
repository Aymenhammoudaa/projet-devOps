package tn.esprit.tpfoyer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;

    @Autowired
    public EtudiantService(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }

    // Méthode pour calculer le nombre de réservations valides dans une plage de dates
    public long calculateReservationsCount(Long etudiantId, Date startDate, Date endDate) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
                .orElseThrow(() -> new IllegalArgumentException("Etudiant not found with ID: " + etudiantId));

        return etudiant.getReservations().stream()
                .filter(reservation -> reservation.getAnneeUniversitaire().after(startDate) &&
                        reservation.getAnneeUniversitaire().before(endDate) && reservation.isEstValide())
                .count();
    }
}
