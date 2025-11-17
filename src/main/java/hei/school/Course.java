package hei.school;

public class Course {
    private int id;
    private String label;
    private int credits;
    private Teacher enseignant;

    public Course(int id, String label, int credits, Teacher enseignant) {
        this.id = id;
        this.label = label;
        this.credits = credits;
        this.enseignant = enseignant;
    }

    public int getId() { return id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id;
    }
}
