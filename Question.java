import java.util.HashSet;
import java.util.Set;

public class Question {
    private String question;
    private CandidateList candidateList;
    private Set<Integer> correctAnswers;
    private QuestionType questionType;

    public Question() {
        candidateList = new UpperLetterCandidateList();
        correctAnswers = new HashSet<>();
        questionType = QuestionType.SINGLE_CHOICE_QUESTION;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public void setQuestion(String question) {
        this.question = question.toUpperCase();
    }

    public void setCandidateList(CandidateList candidateList) {
        this.candidateList = candidateList;
    }

    public void setCorrectAnswers(Set<Integer> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getAnswerCount() {
        return this.candidateList.getAnswerCount();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (questionType == QuestionType.SINGLE_CHOICE_QUESTION)
            result.append("~ Choose a correct answer ~\n");
        else result.append("~ Choose all correct candidates ~\n");
        result.append(question);
        if (result.charAt(result.length() - 1) != '?') result.append('?');
        result.append('\n');
        result.append(candidateList.toString());
        return result.toString();
    }

        public double mark(Set<Integer> answers) {
            double mark = 0;
            double markPerCorrect = 1.0 / correctAnswers.size();
            for (Integer i : answers) {
                //if ()
            }
            return mark;
        }


    public int answerToIndex(String choice) {
        return candidateList.toIndex(choice);
    }

    public String indexToAnswer(int index) {
        return candidateList.toAnswer(index);
    }

    public boolean isValidAnswer(String choice) {
        return this.candidateList.isValid(choice);
    }
}
