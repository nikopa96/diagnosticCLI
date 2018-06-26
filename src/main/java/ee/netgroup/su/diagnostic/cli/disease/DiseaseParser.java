package ee.netgroup.su.diagnostic.cli.disease;

import java.util.List;

public class DiseaseParser {

    public Disease getParsedDisease(List<String> rawDiseaseInfo) {
        Disease disease = new Disease();
        disease.setName(rawDiseaseInfo.remove(0));

        disease.setSymptoms(rawDiseaseInfo);

        return disease;
    }

}
