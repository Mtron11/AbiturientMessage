import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LetterGenerator {
    public void generateLetters(List<Faculty> faculties, List<Applicant> applicants) {
        for (Applicant applicant : applicants) {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            try {
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                PDType0Font font = PDType0Font.load(document, new File("C:\\Windows\\Fonts\\Arial.ttf"));
                contentStream.setFont(font, 16);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Абитуриент: " + applicant.getName());
                contentStream.newLineAtOffset(0, -30);
                contentStream.setFont(font, 12);

                boolean admitted = false;

                for (Faculty faculty : faculties) {
                    if (isAdmitted(applicant, faculty)) {
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("- " + faculty.getName());
                        admitted = true;
                    }
                }

                if (!admitted) {
                    contentStream.newLineAtOffset(0, -30);
                    contentStream.showText("- Извините, не удалось поступить ни на один факультет");
                    contentStream.newLine();
                }

                contentStream.endText();
                contentStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                String path = "C:\\Users\\Mtron\\Desktop\\Новая папка\\";
                document.save(path + applicant.getName() + ".pdf");
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isAdmitted(Applicant applicant, Faculty faculty) {
        List<Subject> requiredSubjects = faculty.getRequiredSubjects();
        int passingScore = faculty.getPassingScore();

        // Проверка требования 1: Абитуриент сдал все требуемые предметы
        for (Subject requiredSubject : requiredSubjects) {
            boolean subjectFound = false;
            for (Subject exam : applicant.getExams()) {
                if (exam.getName().equals(requiredSubject.getName()) && exam.getScore() >= requiredSubject.getScore()) {
                    subjectFound = true;
                    break;
                }
            }
            if (!subjectFound) {
                return false; // Абитуриент не сдал один из требуемых предметов
            }
        }

        // Проверка требования 2: Набраны минимальные баллы по требуемым предметам
        int totalScore = 0;
        for (Subject exam : applicant.getExams()) {
            for (Subject requiredSubject : requiredSubjects) {
                if (exam.getName().equals(requiredSubject.getName())) {
                    totalScore += exam.getScore();
                    break;
                }
            }
        }

        if (totalScore < passingScore) {
            return false; // Абитуриент не набрал проходной балл
        }

        return true; // Все требования выполнены
    }
}
