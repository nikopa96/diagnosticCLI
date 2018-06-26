package ee.netgroup.su.diagnostic.cli.disease;

import java.util.ArrayList;
import java.util.List;

public class DiseaseStorage {

    private List<Disease> diseases = new ArrayList<>();

    public void addDiseaseToList(Disease disease) {
        diseases.add(disease);
    }

    public List<Disease> getDiseasesStorage() {
        return diseases;
    }

    public List<String> getAllSymptoms() {
        List<String> symptoms = new ArrayList<>();

        for (Disease disease : diseases) {
            symptoms.addAll(disease.getSymptoms());
        }

        return symptoms;
    }

}
