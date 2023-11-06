import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    public List<Faculty> readFacultiesFromXmlFile(String filePath) {
        List<Faculty> faculties = new ArrayList<>();

        try {
            File inputFile = new File(filePath);
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);

            Element rootElement = document.getRootElement();
            List<Element> facultyElements = rootElement.getChildren("faculty");

            for (Element facultyElement : facultyElements) {
                Faculty faculty = new Faculty();
                faculty.setName(facultyElement.getChildText("name"));
                int passingScore = Integer.parseInt(facultyElement.getChildText("passing_score"));
                faculty.setPassingScore(passingScore);

                List<Subject> requiredSubjects = new ArrayList<>();
                List<Element> subjectElements = facultyElement.getChild("required_subjects").getChildren("subject");
                for (Element subjectElement : subjectElements) {
                    String subjectName = subjectElement.getAttributeValue("name");
                    int minScore = Integer.parseInt(subjectElement.getAttributeValue("min_score"));
                    requiredSubjects.add(new Subject(subjectName, minScore));
                }

                faculty.setRequiredSubjects(requiredSubjects);
                faculties.add(faculty);
            }
        } catch (IOException | org.jdom2.JDOMException e) {
            e.printStackTrace();
        }

        return faculties;
    }

    public List<Applicant> readApplicantsFromXmlFile(String filePath) {
        List<Applicant> applicants = new ArrayList<>();

        try {
            File inputFile = new File(filePath);
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);

            Element rootElement = document.getRootElement();
            List<Element> applicantElements = rootElement.getChildren("applicant");

            for (Element applicantElement : applicantElements) {
                Applicant applicant = new Applicant();
                applicant.setName(applicantElement.getChildText("name"));

                List<Subject> exams = new ArrayList<>();
                List<Element> examElements = applicantElement.getChildren("exam");
                for (Element examElement : examElements) {
                    String subjectName = examElement.getAttributeValue("subject");
                    int score = Integer.parseInt(examElement.getAttributeValue("score"));
                    exams.add(new Subject(subjectName, score));
                }

                applicant.setExams(exams);
                applicants.add(applicant);
            }
        } catch (IOException | org.jdom2.JDOMException e) {
            e.printStackTrace();
        }

        return applicants;
    }
    public List<Faculty> readFacultiesFromJsonFile(String filePath) {
        List<Faculty> faculties = new ArrayList();

        try (FileReader fileReader = new FileReader(filePath)) {
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(fileReader);
            JsonArray facultiesArray = jsonObject.getAsJsonArray("faculties");

            for (int i = 0; i < facultiesArray.size(); i++) {
                JsonObject facultyObject = facultiesArray.get(i).getAsJsonObject();
                Faculty faculty = new Faculty();
                faculty.setName(facultyObject.get("name").getAsString());
                faculty.setPassingScore(facultyObject.get("passing_score").getAsInt());

                List<Subject> requiredSubjects = new ArrayList<>();
                JsonArray requiredSubjectsArray = facultyObject.getAsJsonArray("required_subjects");

                for (int j = 0; j < requiredSubjectsArray.size(); j++) {
                    JsonObject subjectObject = requiredSubjectsArray.get(j).getAsJsonObject();
                    Subject subject = new Subject();
                    subject.setName(subjectObject.get("name").getAsString());
                    requiredSubjects.add(subject);
                }

                faculty.setRequiredSubjects(requiredSubjects);
                faculties.add(faculty);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return faculties;
    }

    public List<Applicant> readApplicantsFromJsonFile(String filePath) {
        List<Applicant> applicants = new ArrayList();

        try (FileReader fileReader = new FileReader(filePath)) {
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(fileReader);
            JsonArray applicantsArray = jsonObject.getAsJsonArray("applicants");

            for (int i = 0; i < applicantsArray.size(); i++) {
                JsonObject applicantObject = applicantsArray.get(i).getAsJsonObject();
                Applicant applicant = new Applicant();
                applicant.setName(applicantObject.get("name").getAsString());

                List<Subject> exams = new ArrayList<>();
                JsonArray examsArray = applicantObject.getAsJsonArray("exams");

                for (int j = 0; j < examsArray.size(); j++) {
                    JsonObject examObject = examsArray.get(j).getAsJsonObject();
                    Subject subject = new Subject();
                    subject.setName(examObject.get("subject").getAsString());
                    subject.setScore(examObject.get("score").getAsInt());
                    exams.add(subject);
                }

                applicant.setExams(exams);
                applicants.add(applicant);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return applicants;
    }
}

