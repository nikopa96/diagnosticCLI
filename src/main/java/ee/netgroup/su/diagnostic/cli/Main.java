package ee.netgroup.su.diagnostic.cli;

import ee.netgroup.su.diagnostic.cli.controller.Controller;
import ee.netgroup.su.diagnostic.cli.database.EmptyDatabaseException;
import ee.netgroup.su.diagnostic.cli.disease.Disease;
import ee.netgroup.su.diagnostic.cli.disease.DiseaseParser;
import ee.netgroup.su.diagnostic.cli.disease.DiseaseStorage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static File getInputFile(final String[] arguments) {
        if (arguments.length < 1)
            throw new IllegalArgumentException("No input file given at commandline.");

        final File inputFile = new File(arguments[0]);

        if (!inputFile.exists())
            throw new IllegalArgumentException("Specified input file does not exist: " + inputFile);

        if (!inputFile.canRead())
            throw new IllegalArgumentException("Specified input file is not readable: " + inputFile);

        return inputFile;
    }

    /**
     * Starting point for our application.
     */
    public static void main(final String[] arguments) throws IOException, EmptyDatabaseException {
        DiseaseStorage diseaseStorage = new DiseaseStorage();
        DiseaseParser diseaseParser = new DiseaseParser();
        Controller controller = new Controller(diseaseStorage);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(getInputFile(arguments)))){
            int linesCount = 0;
            while (true) {
                final String textLine = bufferedReader.readLine();
                if (textLine == null)
                    break;

                linesCount++;

                List<String> splittedString = new ArrayList<>(Arrays.asList(textLine.split(",\\s")));
                Disease disease = diseaseParser.getParsedDisease(splittedString);
                diseaseStorage.addDiseaseToList(disease);
            }

            controller.showDiseasesHavingMaxSymptoms();
            controller.showNumberOfUniqueSymptoms();
            controller.showPopularSymptoms();
            controller.chooseTask();

            System.out.println("Input file contains " + linesCount + " lines.");
        }
    }

}
