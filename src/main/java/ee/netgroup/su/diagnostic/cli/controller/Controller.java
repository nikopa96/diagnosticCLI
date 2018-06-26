package ee.netgroup.su.diagnostic.cli.controller;

import ee.netgroup.su.diagnostic.cli.database.EmptyDatabaseException;
import ee.netgroup.su.diagnostic.cli.disease.Disease;
import ee.netgroup.su.diagnostic.cli.disease.DiseaseInteractiveSearch;
import ee.netgroup.su.diagnostic.cli.disease.DiseaseService;
import ee.netgroup.su.diagnostic.cli.disease.DiseaseStorage;

import java.util.*;

public class Controller {

    private DiseaseStorage diseaseStorage;

    public Controller(DiseaseStorage diseaseStorage) {
        this.diseaseStorage = diseaseStorage;
    }

    public void showDiseasesHavingMaxSymptoms() throws EmptyDatabaseException {
        DiseaseService diseaseService = new DiseaseService(diseaseStorage);
        List<String> diseases = diseaseService.getDiseasesHavingMaxSymptoms();

        if (diseases.size() >= 3) {
            System.out.println(
                 "-----------------------------------------" + "\n"
                 + "Diseases that have the most symptoms:" + "\n\n"
                 + diseases.get(0) + "\n"
                 + diseases.get(1) + "\n"
                 + diseases.get(2) + "\n"
                 + "-----------------------------------------"
            );
        } else {
            throw new EmptyDatabaseException("Please check your database: in the database must be 3 or more diseases." +
                    "The program has finished its work :(");
        }
    }

    public void showNumberOfUniqueSymptoms() {
        DiseaseService diseaseService = new DiseaseService(diseaseStorage);
        int uniqueSymptoms = diseaseService.getNumberOfUniqueSymptoms();

        System.out.println(
                "Number of unique symptoms in the database:" + "\n\n"
                + uniqueSymptoms + "\n"
                + "-----------------------------------------"
        );
    }

    public void showPopularSymptoms() throws EmptyDatabaseException {
        DiseaseService diseaseService = new DiseaseService(diseaseStorage);
        List<String> popularSymptoms = diseaseService.getPopularSymptoms();

        if (popularSymptoms.size() >= 3) {
            System.out.println(
                "Diseases that have the most symptoms:" + "\n\n"
                + popularSymptoms.get(0) + "\n"
                + popularSymptoms.get(1) + "\n"
                + popularSymptoms.get(2) + "\n"
                + "-----------------------------------------"
            );
        } else {
            throw new EmptyDatabaseException("Please check your database: in the database must be 3 or more unique " +
                    "symptoms. The program has finished its work :(");
        }
    }

    public void chooseTask() {
        System.out.println(
                "Please choose the task:" + "\n"
                + " -- enter number 1 if you want to get Diseases by entered Symptoms" + "\n"
                + " -- enter number 2 if you want to get Interactive Disease solver" + "\n"
        );

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Task number: ");
                if (scanner.hasNextInt()) {
                    switch (scanner.nextInt()) {
                        case 1:
                            diseasesByEnteredSymptomsConsole();
                            break;
                        case 2:
                            interactiveDiseaseSolverConsole();
                            break;
                        default:
                            System.out.println("This task does not exist!");
                            break;
                    }
                    break;
                } else {
                    scanner.next();
                    System.out.println("Task value must be a number!");
                }
            }
        }
    }

    private void diseasesByEnteredSymptomsConsole() {
        DiseaseService diseaseService = new DiseaseService(diseaseStorage);
        List<String> enteredSymptomsNames;

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print(
                        "-----------------------------------------" + "\n"
                        + "Please enter symptoms (Comma-separated!): "
                );

                if (scanner.hasNextLine()) {
                    enteredSymptomsNames = Arrays.asList(scanner.nextLine().split(",\\s|,"));
                    break;
                } else {
                    scanner.next();
                    System.out.println("Your input is incorrect. Try again");
                }
            }
        }

        List<Disease> diseases = diseaseService.getDiseasesByEnteredSymptoms(enteredSymptomsNames);

        System.out.println("Your input is: " + enteredSymptomsNames);

        if (diseases.size() > 0) {
            System.out.println("Possible diseases: ");
            diseases.forEach(d -> System.out.println(d.getName()));
        } else {
            System.out.println("Diseases was not found");
        }

        System.out.println("-----------------------------------------");
    }

    private void interactiveDiseaseSolverConsole() {

        System.out.println("-----------------------------------------");

        List<Disease> possibleDiseases = diseaseStorage.getDiseasesStorage();
        List<String> possibleSymptoms = diseaseStorage.getAllSymptoms();

        DiseaseInteractiveSearch diseaseInteractiveSearch = new DiseaseInteractiveSearch(possibleDiseases,
                possibleSymptoms);

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print(diseaseInteractiveSearch.getCurrentSymptom() + " [y - Yes / n - No]: ");

                if (scanner.hasNext()) {
                    switch (scanner.next()) {
                        case "y":
                            diseaseInteractiveSearch.searchDiseaseYesRequest();
                            break;
                        case "n":
                            diseaseInteractiveSearch.searchDiseaseNoRequest();
                            break;
                        default:
                            System.out.println("Please enter correct request!");
                            break;
                    }

                    if (diseaseInteractiveSearch.isDiseaseFound()) {
                        System.out.println(
                                "Found: " + diseaseInteractiveSearch.getDiseaseName() + "\n"
                                + "-----------------------------------------"
                        );
                        break;
                    }

                    if (diseaseInteractiveSearch.isDiseaseNotFound()) {
                        System.out.println(
                                "Disease was not found" + "\n"
                                + "-----------------------------------------"
                        );
                        break;
                    }
                } else {
                    scanner.next();
                    System.out.println("Please enter correct request!");
                }
            }
        }
    }
}
