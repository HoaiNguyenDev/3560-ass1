import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SimulationDriver {
    public static void main(String[] args) {
        Random random = new Random();
        StringBuilder consoleData = new StringBuilder();
        int numberOfQuestion = random.nextInt(10);
        for (int i = 0; i < numberOfQuestion; i++) {
            // set question
            int questionLength = random.nextInt(20) + 10;
            consoleData.append(randomText(questionLength));
            consoleData.append(System.lineSeparator());

            // set question type
            boolean isSingleChoice = random.nextBoolean();
            if (isSingleChoice)
                consoleData.append("a");
            else consoleData.append("b");
            consoleData.append(System.lineSeparator());

            // set list type
            int listType = random.nextInt(3);
            switch (listType) {
                case 0:
                    consoleData.append("a");
                    break;
                case 1:
                    consoleData.append("b");
                    break;
                case 2:
                    consoleData.append("c");
                    break;
            }
            consoleData.append(System.lineSeparator());

            // add candidate answers
            int numberOfCandidate = random.nextInt(8) + 2;
            for (int j = 0; j < numberOfCandidate; j++) {
                int candidateLength = random.nextInt(20) + 5;
                consoleData.append(randomText(candidateLength));
                consoleData.append(System.lineSeparator());
            }
            consoleData.append(System.lineSeparator());

            // add answers
            int numberOfStudent = random.nextInt(9) + 1;
            for (int j = 0; j < numberOfStudent; j++) {
                consoleData.append(randomText(6));
                consoleData.append(System.lineSeparator());

                Set<Integer> answerIndexes = new HashSet<>();
                int answerIndex = random.nextInt(numberOfCandidate);
                answerIndexes.add(answerIndex);
                if (!isSingleChoice) {
                    int numberOfAnswer = random.nextInt(numberOfCandidate - 1) + 1;
                    while (answerIndexes.size() < numberOfAnswer) {
                        answerIndex = random.nextInt(numberOfCandidate);
                        answerIndexes.add(answerIndex);
                    }
                }
                for (Integer integer : answerIndexes) {
                    if (listType == 0)
                        consoleData.append(toNumberIndex(integer));
                    else if (listType == 1) {
                        consoleData.append(toUppercaseAlphabet(integer));
                    } else consoleData.append(toLowercaseRoman(integer));
                    consoleData.append(System.lineSeparator());
                }
                if (!isSingleChoice) consoleData.append(System.lineSeparator());
            }
            consoleData.append(System.lineSeparator());
            if (i == numberOfQuestion - 1)
                consoleData.append("no");
            else consoleData.append("yes");
            consoleData.append(System.lineSeparator());
        }

        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream(consoleData.toString().getBytes());
        System.setIn(in);
        new VotingService().start(); // start voting service
        System.setIn(sysInBackup); // reset System.in to its original
    }

    private static StringBuffer randomText(int targetStringLength) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuffer::new, StringBuffer::appendCodePoint, StringBuffer::append);
    }

    public static StringBuffer toLowercaseRoman(int n) {
        final int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        final String[] romanLetters = {"m", "cm", "d", "cd", "c", "xc", "l", "xl", "x", "ix", "v", "iv", "i"};
        StringBuffer result = new StringBuffer();
        n++;
        for (int i = 0; i < values.length; i++) {
            while (n >= values[i]) {
                n = n - values[i];
                result.append(romanLetters[i]);
            }
        }
        return result;
    }

    public static StringBuffer toNumberIndex(int n) {
        return new StringBuffer().append(n + 1);
    }

    public static StringBuffer toUppercaseAlphabet(int n) {
        StringBuffer result = new StringBuffer();
        result.append((char) (n + 'A'));
        return result;
    }
}
