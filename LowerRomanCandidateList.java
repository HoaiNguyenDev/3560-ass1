public class LowerRomanCandidateList extends CandidateList {
    private static final int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] romanLetters = {"m", "cm", "d", "cd", "c", "xc", "l", "xl", "x", "ix", "v", "iv", "i"};

    @Override
    public int toIndex(String input) {
        int res = 0;
        for (int i = 0; i < input.length(); i++) {
            int s1 = value(input.charAt(i));
            if (s1 == -1) return -1;
            if (i + 1 < input.length()) {
                int s2 = value(input.charAt(i + 1));
                if (s1 >= s2) res = res + s1;
                else {
                    res = res + s2 - s1;
                    i++;
                }
            } else res = res + s1;
        }
        return res - 1;
    }

    @Override
    public String toAnswer(int index) {
        StringBuilder result = new StringBuilder();
        index++;
        for (int i = 0; i < values.length; i++) {
            while (index >= values[i]) {
                index = index - values[i];
                result.append(romanLetters[i]);
            }
        }
        return result.toString();
    }

    int value(char r) {
        if (r == 'i')
            return 1;
        if (r == 'v')
            return 5;
        if (r == 'x')
            return 10;
        if (r == 'l')
            return 50;
        if (r == 'c')
            return 100;
        if (r == 'd')
            return 500;
        if (r == 'm')
            return 1000;
        return -1;

    }
}
