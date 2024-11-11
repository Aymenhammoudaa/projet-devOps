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
        MockitoAnnotations.openMocks(this); // Initialize mocks
        foyer = new Foyer();
        foyer.setIdFoyer(FOYER_ID);
        foyer.setNomFoyer(FOYER_NAME);
        foyer.setCapaciteFoyer(200);
    }

    @Test
    void retrieveAllFoyers_ShouldReturnFoyersList() {
        // Arrange
        when(foyerRepository.findAll()).thenReturn(Arrays.asList(foyer));

        // Act
        List<Foyer> foyers = foyerService.retrieveAllFoyers();

        // Assert
        assertNotNull(foyers, "Foyers list should not be null");
        assertEquals(1, foyers.size(), "Foyers list should contain one foyer");
        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    void retrieveFoyer_ShouldReturnFoyer_WhenIdExists() {
        // Arrange
        when(foyerRepository.findById(FOYER_ID)).thenReturn(Optional.of(foyer));

        // Act
        Foyer result = foyerService.retrieveFoyer(FOYER_ID);

        // Assert
        assertNotNull(result, "Retrieved foyer should not be null");
        assertEquals(FOYER_NAME, result.getNomFoyer(), "Foyer name should match");
        verify(foyerRepository, times(1)).findById(FOYER_ID);
    }

    @Test
    void addFoyer_ShouldSaveAndReturnFoyer() {
        // Arrange
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Act
        Foyer result = foyerService.addFoyer(foyer);

        // Assert
        assertNotNull(result, "Added foyer should not be null");
        assertEquals(FOYER_NAME, result.getNomFoyer(), "Foyer name should match");
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void modifyFoyer_ShouldUpdateAndReturnFoyer() {
        // Arrange
        foyer.setNomFoyer(UPDATED_FOYER_NAME);
        foyer.setCapaciteFoyer(UPDATED_FOYER_CAPACITY); // Set the updated capacity
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Act
        Foyer result = foyerService.modifyFoyer(foyer);

        // Assert
        assertNotNull(result, "Modified foyer should not be null");
        assertEquals(UPDATED_FOYER_NAME, result.getNomFoyer(), "Foyer name should match the updated value");
        assertEquals(UPDATED_FOYER_CAPACITY, result.getCapaciteFoyer(), "Foyer capacity should match the updated value"); // Assert for capacity
        verify(foyerRepository, times(1)).save(foyer);
    }


    @Test
    void removeFoyer_ShouldDeleteFoyer_WhenIdExists() {
        // Act
        foyerService.removeFoyer(FOYER_ID);

        // Assert
        verify(foyerRepository, times(1)).deleteById(FOYER_ID);
    }

    @Test
    void removeFoyer_ShouldThrowException_WhenIdDoesNotExist() {
        // Arrange
        doThrow(new RuntimeException("Foyer not found")).when(foyerRepository).deleteById(FOYER_ID);

        // Act & Assert
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
        Universite universite = new Universite(); // Assume Universite is a valid class
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
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> foyerService.addFoyer(null), "Expected exception not thrown for null foyer");
    }

    @Test
    void modifyFoyer_ShouldThrowException_WhenFoyerDoesNotExist() {
        // Arrange
        doThrow(new RuntimeException("Foyer not found")).when(foyerRepository).save(foyer);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> foyerService.modifyFoyer(foyer), "Expected exception not thrown for non-existing foyer");
    }



    @Test
    void addFoyer_ShouldThrowException_WhenFoyerWithDuplicateName() {
        // Arrange
        Foyer duplicateFoyer = new Foyer();
        duplicateFoyer.setNomFoyer(FOYER_NAME); // Same name as existing foyer
        when(foyerRepository.save(duplicateFoyer)).thenThrow(new RuntimeException("Foyer with this name already exists"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> foyerService.addFoyer(duplicateFoyer), "Expected exception not thrown for duplicate foyer name");
    }






}