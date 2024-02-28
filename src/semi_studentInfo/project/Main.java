package semi_studentInfo.project;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Subject korean = new Subject("국어", Subject.korean);
        Subject math = new Subject("수학", Subject.math);

        ArrayList<Student> students = new ArrayList<>();

        Student s1 = new Student(181213, "이지은", korean);
        Student s2 = new Student(181518, "장원영", math);
        Student s3 = new Student(171230, "원빈", korean);
        Student s4 = new Student(171255, "하니", korean);
        Student s5 = new Student(171590, "정성찬", math);

        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        students.add(s5);

        int[] koreanScore = new int[]{95, 95, 100, 89, 83};
        int[] mathScore = new int[]{56, 98, 88, 95, 56};

        for (Student student : students) {
            korean.register(student);
            math.register(student);

            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).equals(student)) {
                    student.addSubjectScore(new Score(student.studentId, korean, koreanScore[i]));
                    student.addSubjectScore(new Score(student.studentId, math, mathScore[i]));
                }
            }
        }

        System.out.println("국어수강생 학점");
        System.out.println("이름 | 학번 | 필수과목 | 점수");
        System.out.println("-------------------------------------------");
        for (Student koreanStudent : korean.studentList) {
            int koreanPoint = 0;
            String koreanGrade = null;

            if (koreanStudent.majorSubject == korean) korean.gradeType = Subject.MAJOR;
            else korean.gradeType = Subject.NOT_MAJOR;

            for (Score score : koreanStudent.scoreList) {
                if (score.subject.equals(korean)) {
                    koreanPoint = score.point;
                    koreanGrade = score.toString();
                }
            }
            System.out.println(koreanStudent.studentName + " | "
                    + koreanStudent.studentId + " | "
                    + koreanStudent.majorSubject.subjectName + " | "
                    + koreanPoint + ":" + koreanGrade + " |");
        }

        System.out.println("============================================");

        System.out.println("수학수강생 학점");
        System.out.println("이름 | 학번 | 필수과목 | 점수");
        System.out.println("-------------------------------------------");
        for (Student mathStudent : math.studentList) {
            int mathPoint = 0;
            String mathGrade = null;

            if (mathStudent.majorSubject == math) math.gradeType = Subject.MAJOR;
            else math.gradeType = Subject.NOT_MAJOR;

            for (Score score : mathStudent.scoreList) {
                if (score.subject.equals(math)) {
                    mathPoint = score.point;
                    mathGrade = score.toString();
                }
            }
            System.out.println(mathStudent.studentName + " | "
                    + mathStudent.studentId + " | "
                    + mathStudent.majorSubject.subjectName + " | "
                    + mathPoint + ":" + mathGrade + " |");
        }
    }
}
