import java.util.ArrayList;

public abstract class CandidateList {
    protected ArrayList<String> candidates = new ArrayList<>();

    public CandidateList() {
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < candidates.size() - 1; i++) {
            result.append(String.format("%4s. %s\n", toAnswer(i), candidates.get(i)));
        }
        result.append(String.format("%4s. %s", toAnswer(candidates.size() - 1), candidates.get(candidates.size() - 1)));
        return result.toString();
    }

    public void addCandidate(String candidate) {
        candidates.add(candidate);
    }

    public int getAnswerCount() {
        return this.candidates.size();
    }

    public abstract int toIndex(String input);

    public abstract String toAnswer(int index);

    public boolean isValid(String input) {
        return isValid(toIndex(input));
    }

    public boolean isValid(int index) {
        return (index >= 0 && index < getAnswerCount());
    }
}
