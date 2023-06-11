import java.util.Objects;

public class Student implements Comparable<Student> {
    private final String ID;

    public Student(String ID) {
        this.ID = ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(ID, student.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public int compareTo(Student o) {
        return this.ID.compareTo(o.ID);
    }
}
