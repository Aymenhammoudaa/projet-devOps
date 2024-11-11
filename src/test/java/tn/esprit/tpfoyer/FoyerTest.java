package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.FoyerRepository;
import tn.esprit.tpfoyer.service.FoyerServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoyerTest {

    private static final Long FOYER_ID = 1L;
    private static final String FOYER_NAME = "Main Foyer";
    private static final String UPDATED_FOYER_NAME = "Updated Foyer";
    private static final int UPDATED_FOYER_CAPACITY = 400;

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private FoyerServiceImpl foyerService;

    private Foyer foyer;
    private Bloc bloc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        foyer = new Foyer();
        foyer.setIdFoyer(FOYER_ID);
        foyer.setNomFoyer(FOYER_NAME);
        foyer.setCapaciteFoyer(200);
    }

    @Test
    void retrieveAllFoyers_ShouldReturnFoyersList() {
        when(foyerRepository.findAll()).thenReturn(Arrays.asList(foyer));
        List<Foyer> foyers = foyerService.retrieveAllFoyers();
        assertNotNull(foyers, "Foyers list should not be null");
        assertEquals(1, foyers.size(), "Foyers list should contain one foyer");
        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    void retrieveFoyer_ShouldReturnFoyer_WhenIdExists() {
        when(foyerRepository.findById(FOYER_ID)).thenReturn(Optional.of(foyer));
        Foyer result = foyerService.retrieveFoyer(FOYER_ID);
        assertNotNull(result, "Retrieved foyer should not be null");
        assertEquals(FOYER_NAME, result.getNomFoyer(), "Foyer name should match");
        verify(foyerRepository, times(1)).findById(FOYER_ID);
    }

    @Test
    void addFoyer_ShouldSaveAndReturnFoyer() {
        when(foyerRepository.save(foyer)).thenReturn(foyer);
        Foyer result = foyerService.addFoyer(foyer);
        assertNotNull(result, "Added foyer should not be null");
        assertEquals(FOYER_NAME, result.getNomFoyer(), "Foyer name should match");
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void modifyFoyer_ShouldUpdateAndReturnFoyer() {
        foyer.setNomFoyer(UPDATED_FOYER_NAME);
        foyer.setCapaciteFoyer(UPDATED_FOYER_CAPACITY);
        when(foyerRepository.save(foyer)).thenReturn(foyer);
        Foyer result = foyerService.modifyFoyer(foyer);
        assertNotNull(result, "Modified foyer should not be null");
        assertEquals(UPDATED_FOYER_NAME, result.getNomFoyer(), "Foyer name should match the updated value");
        assertEquals(UPDATED_FOYER_CAPACITY, result.getCapaciteFoyer(), "Foyer capacity should match the updated value");
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void removeFoyer_ShouldDeleteFoyer_WhenIdExists() {
        when(foyerRepository.existsById(FOYER_ID)).thenReturn(true);
        foyerService.removeFoyer(FOYER_ID);
        verify(foyerRepository, times(1)).deleteById(FOYER_ID);
    }

    @Test
    void removeFoyer_ShouldThrowException_WhenIdDoesNotExist() {
        doThrow(new RuntimeException("Foyer not found")).when(foyerRepository).deleteById(FOYER_ID);
        assertThrows(RuntimeException.class, () -> foyerService.removeFoyer(FOYER_ID), "Expected exception not thrown for non-existing foyer");
    }

    @Test
    public void testToString() {
        foyer.setNomFoyer("Test Foyer");
        foyer.setCapaciteFoyer(200);
        String expectedString = "Foyer(idFoyer=1, nomFoyer=Test Foyer, capaciteFoyer=200)";
        assertEquals(expectedString, foyer.toString(), "ToString should match expected format");
    }

    @Test
    public void testSetAndGetUniversite() {
        Universite universite = new Universite();
        foyer.setUniversite(universite);
        assertEquals(universite, foyer.getUniversite(), "Foyer should have the correct university");
    }

    @Test
    public void testSetAndGetBlocs() {
        Set<Bloc> blocs = new HashSet<>();
        foyer.setBlocs(blocs);
        assertEquals(blocs, foyer.getBlocs(), "Foyer should have the correct blocks");
    }

    @Test
    public void testSetAndGetCapaciteFoyer() {
        long capacity = 150;
        foyer.setCapaciteFoyer(capacity);
        assertEquals(capacity, foyer.getCapaciteFoyer(), "Foyer capacity should match");
    }

    @Test
    void addFoyer_ShouldThrowException_WhenFoyerIsNull() {
        assertThrows(IllegalArgumentException.class, () -> foyerService.addFoyer(null), "Expected exception not thrown for null foyer");
    }

    @Test
    void modifyFoyer_ShouldThrowException_WhenFoyerDoesNotExist() {
        doThrow(new RuntimeException("Foyer not found")).when(foyerRepository).save(foyer);
        assertThrows(RuntimeException.class, () -> foyerService.modifyFoyer(foyer), "Expected exception not thrown for non-existing foyer");
    }

    @Test
    void addFoyer_ShouldThrowException_WhenFoyerWithDuplicateName() {
        Foyer duplicateFoyer = new Foyer();
        duplicateFoyer.setNomFoyer(FOYER_NAME);
        when(foyerRepository.save(duplicateFoyer)).thenThrow(new RuntimeException("Foyer with this name already exists"));
        assertThrows(RuntimeException.class, () -> foyerService.addFoyer(duplicateFoyer), "Expected exception not thrown for duplicate foyer name");
    }
}
