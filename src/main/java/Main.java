import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите источник данных (json/xml):");
        String source = scanner.nextLine();

        DataReader dataReader = new DataReader();
        LetterGenerator letterGenerator = new LetterGenerator();

        if ("json".equalsIgnoreCase(source)) {
            String jsonApplicantsFilePath = "src\\main\\resources\\Applicants.json";
            String jsonFacultiesFilePath = "src\\main\\resources\\Faculties.json";

            List<Faculty> faculties = dataReader.readFacultiesFromJsonFile(jsonFacultiesFilePath);
            List<Applicant> applicants = dataReader.readApplicantsFromJsonFile(jsonApplicantsFilePath);

            letterGenerator.generateLetters(faculties, applicants);
        } else if ("xml".equalsIgnoreCase(source)) {
            String xmlApplicantsFilePath = "src\\main\\resources\\Applicants.xml";
            String xmlFacultiesFilePath = "src\\main\\resources\\Faculties.xml";

            List<Faculty> faculties = dataReader.readFacultiesFromXmlFile(xmlFacultiesFilePath);
            List<Applicant> applicants = dataReader.readApplicantsFromXmlFile(xmlApplicantsFilePath);

            letterGenerator.generateLetters(faculties, applicants);
        } else {
            System.out.println("Неверный источник данных. Выберите 'json' или 'xml'.");
        }
    }
}

