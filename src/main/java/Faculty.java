import java.util.List;

public class Faculty {
    private String name;
    private List<Subject> requiredSubjects;
    private int passingScore;
    public Faculty() {
        // Пустой конструктор
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public List<Subject> getRequiredSubjects() {
        return requiredSubjects;
    }
    public void setRequiredSubjects(List<Subject> requiredSubjects) {
        this.requiredSubjects = requiredSubjects;
    }
    public void setPassingScore(int passingScore) {
        this.passingScore = passingScore;
    }
    public int getPassingScore() {
        return passingScore;
    }
}
