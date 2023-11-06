import java.util.List;

public class Applicant {
    private String name;
    private List<Subject> exams;

    public Applicant() {
        // Пустой конструктор
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Subject> getExams() {
        return exams;
    }

    public void setExams(List<Subject> exams) {
        this.exams = exams;
    }
}
