package tn.esprit.tpfoyer.test;

import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.EtudiantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.service.EtudiantService;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EtudiantServiceTest {  // Peut-être rendre la classe package-private si nécessaire (pas public)

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantService etudiantService;

    private Etudiant etudiant;

    @BeforeEach
    void setup() {  // Peut-être rendre la méthode package-private si nécessaire (pas public)
        MockitoAnnotations.openMocks(this);

        // Initialize Etudiant and Reservations
        etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEtudiant("John");
        etudiant.setPrenomEtudiant("Doe");

        // Set up Reservation data for testing
        Set<Reservation> reservations = new HashSet<>();
        Reservation reservation1 = new Reservation();
        reservation1.setAnneeUniversitaire(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24));  // 1 day ago
        reservation1.setEstValide(true);

        Reservation reservation2 = new Reservation();
        reservation2.setAnneeUniversitaire(new Date());  // Today
        reservation2.setEstValide(true);

        reservations.add(reservation1);
        reservations.add(reservation2);
        etudiant.setReservations(reservations);
    }

    @Test
    void testCalculateReservationsCount() {  // Peut-être rendre la méthode package-private si nécessaire (pas public)
        // Mock the repository
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        // Define the date range
        Date startDate = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 48);  // 2 days ago
        Date endDate = new Date();  // Today

        // Call the service method
        long count = etudiantService.calculateReservationsCount(1L, startDate, endDate);

        // Verify the result
        assertEquals(2, count);  // Both reservations should be in the range
        verify(etudiantRepository, times(1)).findById(1L);
    }
}
