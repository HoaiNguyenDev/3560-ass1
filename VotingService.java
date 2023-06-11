import java.util.*;

public class VotingService {
    private final Set<Student> students;
    private final List<Question> questions;
    private final List<Map<Student, Set<Integer>>> answersList;
    private final Scanner scanner;
    private String input;
    private Question currentQuestion;

    public VotingService() {
        System.out.println("==== VOTING SERVICE STARTED ====");
        this.students = new HashSet<>();
        this.questions = new ArrayList<>();
        this.answersList = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        new VotingService().start();
    }

    public void start() {
        do {
            System.out.println("================================");
            setupQuestion();
            questions.add(currentQuestion);
            System.out.println();
            System.out.println(currentQuestion);
            System.out.println();
            beginVote();
            showSummary();
            System.out.print("\nDO YOU WANT TO SETUP ANOTHER POLL [NO]?: ");
            input = scanner.nextLine();
            System.out.println("\"" + input + "\"\n");
        } while (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y"));
        showStatistics();
    }

    private void showSummary(Question question) {
        int questionIndex = questions.indexOf(question);
        int[] counts = new int[question.getAnswerCount()];
        for (Set<Integer> temp : answersList.get(questionIndex).values()) {
            for (int i : temp) {
                counts[i]++;
            }
        }
        System.out.println("\n+++ SUMMARY +++");
        System.out.println(answersList.get(questionIndex).size() + " student(s) answer this question");
        for (int i = 0; i < counts.length - 1; i++) {
            System.out.printf("%4s : %d; ", question.indexToAnswer(i), counts[i]);
        }
        System.out.printf("%4s : %d", question.indexToAnswer(counts.length - 1), counts[counts.length - 1]);
        System.out.println();
    }

    private void showSummary() {
        showSummary(currentQuestion);
    }

    private void setupQuestion() {
        System.out.println("+++ SETUP QUESTION +++");
        System.out.print("\nTYPE A QUESTION: ");
        currentQuestion = new Question();
        String questionContent = scanner.nextLine();
        currentQuestion.setQuestion(questionContent);
        System.out.println("\"" + questionContent + "\"");

        setQuestionType();

        setCandidateAnswers();

        setCorrectAnswer();
    }

    private void setCorrectAnswer() {

    }

    private void setCandidateAnswers() {
        CandidateList candidateList;
        System.out.println("\nTYPE OF ANSWER LIST?");
        do {
            System.out.println(" a) Numbered list (1, 2, 3,...)");
            System.out.println(" b) Uppercase alphabetize list (A, B, C,...)");
            System.out.println(" c) Lower roman alphabetize list (i, ii, iii,...)");
            System.out.print("Your choice <a-c>: ");
            input = scanner.nextLine();
            System.out.println("\"" + input + "\"");
            if (input.equals("a") || input.equals("b") || input.equals("c")) {
                if (input.equals("a")) candidateList = new NumberedCandidateList();
                else if (input.equals("b")) candidateList = new UpperLetterCandidateList();
                else candidateList = new LowerRomanCandidateList();
                break;
            } else System.out.println("Invalid option, try again!");
        } while (true);

        currentQuestion.setCandidateList(candidateList);

        System.out.println();
        do {
            System.out.print("TYPE A CANDIDATE ANSWER, LEAVE BLANK IF DONE: ");
            input = scanner.nextLine();
            System.out.println("\"" + input + "\"");
            if (isBlank(input)) break;
            candidateList.addCandidate(input);
        } while (true);
    }

    private void setQuestionType() {
        System.out.println("\nTYPE OF THIS QUESTION?");
        do {
            System.out.println(" a) Single choice question");
            System.out.println(" b) Multiple choice question");
            System.out.print("Your choice <a-b>: ");
            input = scanner.nextLine();
            System.out.println("\"" + input + "\"");
            if (input.equals("a") || input.equals("b")) {
                if (input.equals("a")) currentQuestion.setQuestionType(QuestionType.SINGLE_CHOICE_QUESTION);
                else currentQuestion.setQuestionType(QuestionType.MULTIPLE_CHOICE_QUESTION);
                break;
            } else System.out.println("Invalid option, try again!");
        } while (true);
    }

    private void beginVote() {
        System.out.println("+++ RECEIVE ANSWER +++");
        Student student;
        Set<Integer> answerIndexes;
        Map<Student, Set<Integer>> answers = new HashMap<>();
        do {
            System.out.print("TYPE A STUDENT ID TO VOTE, LEAVE BLANK IF NO ONE LEFT: ");
            input = scanner.nextLine();
            System.out.println("\"" + input + "\"");
            if (isBlank(input)) break;
            student = new Student(input);
            students.add(student);
            answerIndexes = receiveAnswerFromStudent();
            answers.put(student, answerIndexes);
        } while (true);
        answersList.add(answers);
    }

    private Set<Integer> receiveAnswerFromStudent() {
        Set<Integer> result = new HashSet<>();
        System.out.print("TYPE A CHOICE: ");
        input = scanner.nextLine();
        System.out.println("\"" + input + "\"");
        while (!currentQuestion.isValidAnswer(input)) {
            System.out.print("INVALID CHOICE, TRY AGAIN: ");
            input = scanner.nextLine();
            System.out.println("\"" + input + "\"");
        }
        result.add(currentQuestion.answerToIndex(input));

        if (currentQuestion.getQuestionType() == QuestionType.SINGLE_CHOICE_QUESTION)
            return result;

        System.out.print("TYPE ANOTHER CHOICE, LEAVE BLANK IF DONE: ");
        input = scanner.nextLine();
        System.out.println("\"" + input + "\"");
        while (!isBlank(input)) {
            if (currentQuestion.isValidAnswer(input)) {
                result.add(currentQuestion.answerToIndex(input));
                System.out.print("TYPE ANOTHER CHOICE, LEAVE BLANK IF DONE: ");
            } else {
                System.out.print("INVALID CHOICE, TRY AGAIN: ");
            }
            input = scanner.nextLine();
            System.out.println("\"" + input + "\"");
        }
        return result;
    }

    private void showStatistics() {
        System.out.println("================================");
        System.out.println("+++ STATISTICS +++");
        System.out.println("Count of questions: " + questions.size());
        System.out.println("Count of students: " + students.size());
    }

    private boolean isBlank(String text) {
        return text.trim().isEmpty();
    }
}
