package semi_studentInfo.project;

import java.util.ArrayList;

public class Subject {
    protected String subjectName;
    protected int subjectId;
    protected int gradeType;
    protected ArrayList<Student> studentList;

    static final int korean = 1;
    static final int math = 2;

    static final int MAJOR = 1;
    static final int NOT_MAJOR = 2;

    public Subject(String subjectName, int subjectId) {
        this.subjectName = subjectName;
        this.subjectId = subjectId;
        studentList = new ArrayList<>();
    }

    public void register(Student student) {
        studentList.add(student);
    }
}
