public class Main {
    public static void main(String[] args) {
      
    }

    public static void testCommonWordSolver() {
        double average = 0;
        int lengthInput = 3;
        double wordCount = 882;
        CommonWordSolve.solvedIn = new int[20];
        double startTime = System.currentTimeMillis();
        for (int i = 0; i < wordCount; i++) {
            CommonWordSolve r = new CommonWordSolve(lengthInput, i);
        }
        double endTime = System.currentTimeMillis();
        average = CommonWordSolve.total / wordCount;
        double time = (endTime - startTime);
        double avgTime = (time / wordCount);
        System.out.println("Average Guesses: " + average);
        System.out.println("Solved: " + CommonWordSolve.solved);
        System.out.println("Time: " + time + " Milliseconds");
        System.out.println("Average solve time: " + avgTime);
        for (int i = 2; i < CommonWordSolve.solvedIn.length; i++) {
            System.out.println("Solved in " + i + "guesses " + CommonWordSolve.solvedIn[i] + " times");
        }
    }

    public static void testRandomSolve() {
        double average = 0;
        int lengthInput = 5;
        double wordCount = 4358;
        double startTime = System.currentTimeMillis();
        for (int i = 1; i < wordCount; i++) {
            Wordle w = new Wordle(lengthInput, i);
            RandomSolve r = new RandomSolve(w);
        }
        double endTime = System.currentTimeMillis();
        average = RandomSolve.total / wordCount;
        double time = (endTime - startTime);
        double avgTime = (time / wordCount);
        System.out.println("Average Guesses: " + average);
        System.out.println("Solved: " + RandomSolve.solved);
        System.out.println("Time: " + time + " Milliseconds");
        System.out.println("Average solve time: " + avgTime);
    }

    public static void testScoreSolve() {
        double average = 0;
        int lengthInput = 5;
        double wordCount = 4358;
        ScoreSolve.solvedIn = new int[15];
        double startTime = System.currentTimeMillis();
        for (int i = 0; i < wordCount; i++) {
            ScoreSolve r = new ScoreSolve(lengthInput);
        }
        double endTime = System.currentTimeMillis();
        average = ScoreSolve.total / wordCount;
        double time = (endTime - startTime);
        double avgTime = (time / wordCount);
        System.out.println("Average Guesses: " + average);
        System.out.println("Solved: " + ScoreSolve.solved);
        System.out.println("Time: " + time + " Milliseconds");
        System.out.println("Average solve time: " + avgTime);
        for (int i = 2; i < ScoreSolve.solvedIn.length; i++) {
            System.out.println("Solved in " + i + "guesses " + ScoreSolve.solvedIn[i] + " times");
        }
    }

    public static void testEntropyMaximizer() {
        double average = 0;
        int lengthInput = 10;
        double wordCount = 4298;
        int [] solvedIn = new int[20];
        int guesses =0;
        int total =0;
        int solved=0;
        double startTime = System.currentTimeMillis();
        for (int i = 0; i < wordCount; i++) {
            guesses = 0;
            EntropyMaximizer r = new EntropyMaximizer(lengthInput);
            guesses = r.playWordle();
            total = total + guesses;
            if(guesses<7){solved++;}
            solvedIn[guesses] = solvedIn[guesses] + 1;
        }
        double endTime = System.currentTimeMillis();
        average = total / wordCount;
        double time = (endTime - startTime);
        double avgTime = (time / wordCount);
        System.out.println("Average Guesses: " + average);
        System.out.println("Solved: " + solved);
        System.out.println("Time: " + time + " Milliseconds");
        System.out.println("Average solve time: " + avgTime);
        for (int i = 2; i < solvedIn.length; i++) {
            System.out.println("Solved in " + i + "guesses " + solvedIn[i] + " times");
        }
    }
}