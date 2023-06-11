public class NumberedCandidateList extends CandidateList {

    @Override
    public String toAnswer(int n) {
        return String.valueOf(n + 1);
    }

    @Override
    public int toIndex(String input) {
        try {
            return Integer.parseInt(input) - 1;
        } catch (Exception e) {
            return -1;
        }
    }
}
