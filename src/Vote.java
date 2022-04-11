import java.io.Serializable;

public class Vote implements Serializable {
    private Student student;

    public Vote(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    @Override
    public String toString() {
        return "Voter: " + student.getUsername();
    }
}
