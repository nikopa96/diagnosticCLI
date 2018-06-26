package ee.netgroup.su.diagnostic.cli.disease;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DiseaseInteractiveSearchTest {

    @Test
    public void testGetDiseaseNameFound() {
        Disease disease1 = new Disease();
        disease1.setName("Invisibility");
        disease1.setSymptoms(Arrays.asList("blindness near mirrors", "obsessive compulsive behavior"));

        Disease disease2 = new Disease();
        disease2.setName("Vampiris");
        disease2.setSymptoms(Arrays.asList("blindness near mirrors", "zombie-like behavior", "bite wounds",
                "photosensitivity", "obsessive compulsive behavior"));

        List<Disease> possibleDiseases = Arrays.asList(disease1, disease2);
        List<String> possibleSymptoms = Arrays.asList("blindness near mirrors", "obsessive compulsive behavior",
                "blindness near mirrors", "zombie-like behavior", "bite wounds", "photosensitivity",
                "obsessive compulsive behavior");

        DiseaseInteractiveSearch diseaseInteractiveSearch = new DiseaseInteractiveSearch(possibleDiseases,
                possibleSymptoms);

        diseaseInteractiveSearch.searchDiseaseYesRequest();
        diseaseInteractiveSearch.searchDiseaseYesRequest();
        diseaseInteractiveSearch.searchDiseaseNoRequest();

        assertEquals(diseaseInteractiveSearch.getDiseaseName(), "Invisibility");
    }

    @Test
    public void testGetDiseaseNameNotFound() {
        Disease disease1 = new Disease();
        disease1.setName("Invisibility");
        disease1.setSymptoms(Arrays.asList("blindness near mirrors", "obsessive compulsive behavior"));

        Disease disease2 = new Disease();
        disease2.setName("Vampiris");
        disease2.setSymptoms(Arrays.asList("blindness near mirrors", "zombie-like behavior", "bite wounds",
                "photosensitivity", "obsessive compulsive behavior"));

        List<Disease> possibleDiseases = Arrays.asList(disease1, disease2);
        List<String> possibleSymptoms = Arrays.asList("blindness near mirrors", "obsessive compulsive behavior",
                "blindness near mirrors", "zombie-like behavior", "bite wounds", "photosensitivity",
                "obsessive compulsive behavior");

        DiseaseInteractiveSearch diseaseInteractiveSearch = new DiseaseInteractiveSearch(possibleDiseases,
                possibleSymptoms);

        diseaseInteractiveSearch.searchDiseaseYesRequest();
        diseaseInteractiveSearch.searchDiseaseNoRequest();

        assertTrue(diseaseInteractiveSearch.isDiseaseNotFound());
    }
}