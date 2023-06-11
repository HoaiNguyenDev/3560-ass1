public class UpperLetterCandidateList extends CandidateList {

    @Override
    public String toAnswer(int n) {
        StringBuilder result = new StringBuilder();
        n++;
        while (n > 0) {
            int num = (n - 1) % 26;
            char letter = (char) (num + 65);
            result.append(letter);
            n = (n - 1) / 26;
        }
        return result.reverse().toString();
    }

    @Override
    public int toIndex(String input) {
        return input.charAt(0) - 'A';
    }
}
