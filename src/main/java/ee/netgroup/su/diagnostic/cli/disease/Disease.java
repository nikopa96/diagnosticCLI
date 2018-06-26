package ee.netgroup.su.diagnostic.cli.disease;

import java.util.List;

public class Disease {

    private String name;
    private List<String> symptoms;

    public void setName(String name) {
        this.name = name;
    }

    public void setSymptoms(List<String> symptoms) {
        this.symptoms = symptoms;
    }

    public String getName() {
        return name;
    }

    public List<String> getSymptoms() {
        return symptoms;
    }




}
