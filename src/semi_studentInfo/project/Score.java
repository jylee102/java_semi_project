package semi_studentInfo.project;

public class Score {
    protected int studentId;
    protected Subject subject;
    protected int point;

    Score(int studentId, Subject subject, int point) {
        this.studentId = studentId;
        this.subject = subject;
        this.point = point;
    }

    public String toString() {
        GradeEvaluation evaluation;
        if (subject.gradeType == Subject.MAJOR) evaluation = new MajorEvaluation();
        else evaluation = new BasicEvaluation();
        return evaluation.getGrade(point);
    }
}
