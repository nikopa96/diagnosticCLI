package ee.netgroup.su.diagnostic.cli.disease;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DiseaseInteractiveSearch {

    private List<Disease> possibleDiseases;
    private List<String> possibleSymptoms;
    private List<String> chosenSymptoms = new ArrayList<>();

    public DiseaseInteractiveSearch(List<Disease> possibleDiseases, List<String> possibleSymptoms) {
        this.possibleDiseases = possibleDiseases;
        this.possibleSymptoms = possibleSymptoms;
    }

    public String getCurrentSymptom() {
        return possibleSymptoms.get(0);
    }

    public String getDiseaseName() {
        return possibleDiseases.get(0).getName();
    }

    public boolean isDiseaseFound() {
        return possibleDiseases.size() == 1;
    }

    public boolean isDiseaseNotFound() {
        return possibleDiseases.size() == 0;
    }

    public void searchDiseaseYesRequest() {
        String request = possibleSymptoms.get(0);
        chosenSymptoms.add(request);

        possibleDiseases = possibleDiseases.stream()
                .filter(d -> d.getSymptoms().contains(request))
                .collect(Collectors.toList());

        List<String> tempSymptoms = new ArrayList<>();
        for (Disease disease : possibleDiseases) {
            tempSymptoms.addAll(disease.getSymptoms());
        }
        tempSymptoms = tempSymptoms.stream().distinct().collect(Collectors.toList());
        tempSymptoms.removeAll(chosenSymptoms);

        possibleSymptoms = tempSymptoms;
    }

    public void searchDiseaseNoRequest() {
        String request = possibleSymptoms.get(0);
        chosenSymptoms.add(request);

        possibleDiseases = possibleDiseases.stream()
                .filter(d -> !d.getSymptoms().contains(request))
                .collect(Collectors.toList());

        List<String> tempSymptoms = new ArrayList<>();
        for (Disease disease : possibleDiseases) {
            tempSymptoms.addAll(disease.getSymptoms());
        }

        tempSymptoms = tempSymptoms.stream().distinct().collect(Collectors.toList());
        tempSymptoms.removeAll(chosenSymptoms);

        possibleSymptoms = tempSymptoms;
    }

}
