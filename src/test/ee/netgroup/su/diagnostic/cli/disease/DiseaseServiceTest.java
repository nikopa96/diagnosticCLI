package ee.netgroup.su.diagnostic.cli.disease;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DiseaseServiceTest {

    private DiseaseStorage diseaseStorage;

    @Before
    public void setUp() throws Exception {
        this.diseaseStorage = mock(DiseaseStorage.class);
    }

    @Test
    public void testGetDiseasesHavingMaxSymptoms() {
        Disease disease1 = new Disease();
        disease1.setName("Mad Zombie Disease");
        disease1.setSymptoms(Arrays.asList("zombie-like behavior", " impaired judgment", "accelerated mutation",
                "bite wounds"));

        Disease disease2 = new Disease();
        disease2.setName("Cyberbrain Sclerosis");
        disease2.setSymptoms(Arrays.asList("impaired judgment", "hardening of the brain tissue"));

        Disease disease3 = new Disease();
        disease3.setName("T-Virus");
        disease3.setSymptoms(Arrays.asList("zombie-like behavior", "impaired judgment", "accelerated mutation"));

        Disease disease4 = new Disease();
        disease4.setName("Invisibility");
        disease4.setSymptoms(Arrays.asList("blindness near mirrors", "obsessive compulsive behavior"));

        when(diseaseStorage.getDiseasesStorage()).thenReturn(Arrays.asList(disease1, disease2, disease3, disease4));
        DiseaseService diseaseService = new DiseaseService(diseaseStorage);

        assertEquals(diseaseService.getDiseasesHavingMaxSymptoms(), Arrays.asList("Mad Zombie Disease", "T-Virus",
                "Cyberbrain Sclerosis"));
    }

    @Test
    public void testGetDiseasesHavingMaxSymptomsNotThree() {
        Disease disease1 = new Disease();
        disease1.setName("Mad Zombie Disease");
        disease1.setSymptoms(Arrays.asList("zombie-like behavior", " impaired judgment", "accelerated mutation",
                "bite wounds"));

        Disease disease2 = new Disease();
        disease2.setName("Cyberbrain Sclerosis");
        disease2.setSymptoms(Arrays.asList("impaired judgment", "hardening of the brain tissue"));

        when(diseaseStorage.getDiseasesStorage()).thenReturn(Arrays.asList(disease1, disease2));
        DiseaseService diseaseService = new DiseaseService(diseaseStorage);

        List<String> emptyDiseasesList = new ArrayList<>();

        assertEquals(diseaseService.getDiseasesHavingMaxSymptoms(), emptyDiseasesList);
    }

    @Test
    public void testGetNumberOfUniqueSymptoms() {
        List<String> allSymptoms = Arrays.asList("blindness near mirrors", "obsessive compulsive behavior",
                "hallucinations", "impaired judgment", "hallucinations", "zombie-like behavior", "zombie-like behavior");

        when(diseaseStorage.getAllSymptoms()).thenReturn(allSymptoms);
        DiseaseService diseaseService = new DiseaseService(diseaseStorage);

        assertEquals(diseaseService.getNumberOfUniqueSymptoms(), 5);
    }

    @Test
    public void testGetPopularSymptoms() {
        List<String> allSymptoms = Arrays.asList("zombie-like behavior", "hallucinations", "zombie-like behavior",
                "hallucinations", "accelerated mutation", "accelerated mutation", "zombie-like behavior");

        when(diseaseStorage.getAllSymptoms()).thenReturn(allSymptoms);
        DiseaseService diseaseService = new DiseaseService(diseaseStorage);

        assertEquals(diseaseService.getPopularSymptoms(), Arrays.asList("zombie-like behavior", "accelerated mutation",
                "hallucinations"));
    }

    @Test
    public void testGetPopularSymptomsNotThree() {
        List<String> allSymptoms = Arrays.asList("zombie-like behavior", "hallucinations");

        when(diseaseStorage.getAllSymptoms()).thenReturn(allSymptoms);
        DiseaseService diseaseService = new DiseaseService(diseaseStorage);

        List<String> emptySymptomsList = new ArrayList<>();

        assertEquals(diseaseService.getPopularSymptoms(), emptySymptomsList);

        allSymptoms = Arrays.asList("zombie-like behavior", "hallucinations", "hallucinations");
        when(diseaseStorage.getAllSymptoms()).thenReturn(allSymptoms);
        assertEquals(diseaseService.getPopularSymptoms(), emptySymptomsList);
    }

    @Test
    public void testGetDiseasesByEnteredSymptoms() {
        Disease disease1 = new Disease();
        disease1.setName("Biscuit Poisoning");
        disease1.setSymptoms(Arrays.asList("zombie-like behavior", "hallucinations"));

        Disease disease2 = new Disease();
        disease2.setName("Mad Zombie Disease");
        disease2.setSymptoms(Arrays.asList("zombie-like behavior", "hallucinations", "accelerated mutation"));

        Disease disease3 = new Disease();
        disease3.setName("Vampiris");
        disease3.setSymptoms(Arrays.asList("zombie-like behavior", "accelerated mutation"));

        when(diseaseStorage.getDiseasesStorage()).thenReturn(Arrays.asList(disease1, disease2, disease3));
        DiseaseService diseaseService = new DiseaseService(diseaseStorage);

        List<String> enteredSymptoms = Arrays.asList("zombie-like behavior", "accelerated mutation");

        assertEquals(diseaseService.getDiseasesByEnteredSymptoms(enteredSymptoms), Arrays.asList(disease2, disease3));
    }

    @Test
    public void testGetNotExistedDiseasesByEnteredSymptoms() {
        Disease disease1 = new Disease();
        disease1.setName("Biscuit Poisoning");
        disease1.setSymptoms(Arrays.asList("zombie-like behavior", "hallucinations"));

        Disease disease2 = new Disease();
        disease2.setName("Mad Zombie Disease");
        disease2.setSymptoms(Arrays.asList("zombie-like behavior", "hallucinations", "accelerated mutation"));

        when(diseaseStorage.getDiseasesStorage()).thenReturn(Arrays.asList(disease1, disease2));
        DiseaseService diseaseService = new DiseaseService(diseaseStorage);

        List<String> enteredSymptoms = Arrays.asList("zombie-like behavior", "not existed bad symptom");
        List<Disease> emptyDiseaseList = new ArrayList<>();

        assertEquals(diseaseService.getDiseasesByEnteredSymptoms(enteredSymptoms), emptyDiseaseList);
    }
}