package ee.netgroup.su.diagnostic.cli.disease;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DiseaseService {

    private DiseaseStorage diseaseStorage;

    public DiseaseService(DiseaseStorage diseaseStorage) {
        this.diseaseStorage = diseaseStorage;
    }

    public List<String> getDiseasesHavingMaxSymptoms() {
        List<Disease> diseases = diseaseStorage.getDiseasesStorage().stream()
                .sorted(Comparator.comparingInt(d -> d.getSymptoms().size()))
                .collect(Collectors.toList());

        Collections.reverse(diseases);

        diseases.sort((d1, d2) -> {
            if (d1.getSymptoms().size() == d2.getSymptoms().size()) {
                return d1.getName().compareTo(d2.getName());
            } else {
                return 1;
            }
        });

        List<String> diseasesHavingMaxSymptoms = new ArrayList<>();

        if (diseases.size() >= 3) {
            diseasesHavingMaxSymptoms.add(diseases.get(0).getName());
            diseasesHavingMaxSymptoms.add(diseases.get(1).getName());
            diseasesHavingMaxSymptoms.add(diseases.get(2).getName());

            return diseasesHavingMaxSymptoms;
        } else {
            return Collections.emptyList();
        }
    }

    public int getNumberOfUniqueSymptoms() {
        List<String> uniqueSymptoms = diseaseStorage.getAllSymptoms().stream()
                .distinct()
                .collect(Collectors.toList());

        return uniqueSymptoms.size();
    }

    public List<String> getPopularSymptoms() {
        Map<String, Long> symptomsDuplicates = diseaseStorage.getAllSymptoms().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        System.out.println("The most popular symptoms:" + "\n");

        List<String> popularSymptoms = symptomsDuplicates.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed().thenComparing(Map.Entry::getKey))
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (popularSymptoms.size() >= 3) {
            return popularSymptoms;
        } else {
            return Collections.emptyList();
        }
    }

    public List<Disease> getDiseasesByEnteredSymptoms(List<String> enteredSymptomNames) {
        List<Disease> possibleDiseases = diseaseStorage.getDiseasesStorage().stream()
                .filter(d -> d.getSymptoms().containsAll(enteredSymptomNames))
                .collect(Collectors.toList());

        return possibleDiseases;
    }

}
