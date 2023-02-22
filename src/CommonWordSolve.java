import java.util.*;
import java.util.stream.IntStream;

public class CommonWordSolve extends Wordle{

    Wordle wordle;
    LinkedList<String> possibleWords;
    HashMap<String, Long> currentPossibleWords;
    int guesses;
    static double total;
    static double average;
    static int solved;

    static int [] solvedIn;

    public CommonWordSolve(int wordleLength, int wordNumber) {
        super(wordleLength, wordNumber);
        char [] newGuess;
       //newGuess = getInput(wordleLength);
        possibleWords = getAllWords(super.nLetterWordHashMap);
        currentPossibleWords = super.nLetterWordHashMap;
        newGuess = possibleWords.get(0).toCharArray();
        int[] score = {0};
        char[] guessToArray;
        guessToArray = newGuess;
        score = super.scoreWord(guessToArray, super.correctWord);
        guesses = 1;
        for (char i: guessToArray){
            System.out.print(i + ", ");
        }
        System.out.println();
        for (int i: score){
            System.out.print(i + ", ");
        }
        System.out.println();
        while(IntStream.of(score).anyMatch(x -> x == 0) || IntStream.of(score).anyMatch(x -> x == 1)) {
            super.updatePossibleWords(score, guessToArray);
            possibleWords = getAllWords(super.currentPossibleWords);
            guesses = guesses + 1;
            newGuess = findWord(possibleWords).toCharArray();
            guessToArray = newGuess;
            score = super.scoreWord(guessToArray, super.correctWord);
            for (char i: guessToArray){
                System.out.print(i + ", ");
            }
            System.out.println();
            for (int i: score){
                System.out.print(i + ", ");
            }
            System.out.println();
        }
        System.out.println("You win!! The word was " + String.valueOf(super.correctWord) + " Guesses: " + guesses);
        total = total + guesses;
        if(guesses<7){solved++;}
        solvedIn[guesses] = solvedIn[guesses] + 1;
    }


private String findWord(LinkedList<String> possibleWords){
        String newGuess = null;
        long highestFrequency = 0;
        long currentFrequency;
for(String i : possibleWords){
    currentFrequency = super.nLetterWordHashMap.get(i);
    if(currentFrequency > highestFrequency){
        newGuess = i;
        highestFrequency = currentFrequency;
    }
}
        return newGuess;
}
    private LinkedList<String> getAllWords(HashMap<String, Long> nLetterWordHashMap){
        LinkedList<String> allWords = new LinkedList<String>(nLetterWordHashMap.keySet());
        return allWords;
    }

    }


