package semi_studentInfo.project;

public class BasicEvaluation implements GradeEvaluation {
    @Override
    public String getGrade(int point) {
        String grade;
        if (0 <= point && point <= 100) {
            if (90 <= point) {
                grade = "A";
            } else if (80 <= point) {
                grade = "B";
            } else if (70 <= point) {
                grade = "C";
            } else if (55 <= point) {
                grade = "D";
            } else grade = "F";
        } else grade = "Error";

        return grade;
    }
}
