import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class RandomSolve {

    int wordleLength;
    static int guessCounter;
    static int total;
    static int solved;

    public static void main(String[] args) {
        System.out.println("How long should the wordle be?");
        int lengthInput = Wordle.getIntInput(1, 10);
        Wordle w = new Wordle(lengthInput);
        RandomSolve r = new RandomSolve(w);
    }

    public RandomSolve(Wordle wordle){
        guessCounter = 0;
        String randomGuess;
        int[] score = {0};
        char[] randomGuessToArray;
        List<String> keysAsList = new ArrayList<String>(wordle.nLetterWordHashMap.keySet());
        while(IntStream.of(score).anyMatch(x -> x == 0) || IntStream.of(score).anyMatch(x -> x == 1)) {
            randomGuess = getRandWord(keysAsList, wordle.nLetterWordHashMap);
            randomGuessToArray = wordToScore(randomGuess);
            score = wordle.scoreWord(randomGuessToArray, wordle.correctWord);
            guessCounter++;
            for (char i: randomGuessToArray){
                System.out.print(i + ", ");
            }
            System.out.println();
            for (int i: score){
                System.out.print(i + ", ");
            }
            System.out.println();
        }
        System.out.println("You win!! The word was " + String.valueOf(wordle.correctWord));
        System.out.println("it took the random solver " + guessCounter + " tries!");
        total = total + guessCounter;
        if(guessCounter<7){solved++;}
    }

    private String getRandWord(List<String> keysAsList, HashMap<String, Long> nLetterWordHashMap){
        Random rand = new Random();
        int randNum = rand.nextInt(keysAsList.size()-1)+1;
        String randWord = keysAsList.get(randNum);
        keysAsList.remove(randNum);
        return randWord;
    }

    private char[] wordToScore(String word){
        char[] wordArray = word.toCharArray();
        return wordArray;
    }
}

