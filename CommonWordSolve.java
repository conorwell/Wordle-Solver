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

    public static void main(String[] args) {
        //System.out.println("How long should the wordle be?");
        //int lengthInput = Wordle.getIntInput(1, 10);
        int lengthInput = 10;
        double wordCount = 4298;
        total = 0;
        solved = 0;
        double startTime = System.currentTimeMillis();
        for(int i = 0 ; i < wordCount; i++){
        CommonWordSolve r = new CommonWordSolve(lengthInput, i);}
        double endTime = System.currentTimeMillis();
        average = total/wordCount;
        double time = (long) (endTime - startTime);
        double avgTime = (long) (time/wordCount);
        System.out.println("Average Guesses: " + average);
        System.out.println("Solved: "+ solved);
        System.out.println("Time: " + time + " Milliseconds");
        System.out.println("Average solve time: " + avgTime);

    }


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
            currentPossibleWords = super.currentPossibleWords;
            guesses = guesses + 1;
            possibleWords = getAllWords(currentPossibleWords);
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


public String findWord(LinkedList<String> possibleWords){
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
    public LinkedList<String> getAllWords(HashMap<String, Long> nLetterWordHashMap){
        LinkedList<String> allWords = new LinkedList<String>(nLetterWordHashMap.keySet());
        return allWords;
    }
public LinkedList<String> getPossibleWords(int[] score, char[] guessToArray, LinkedList<String> possibleWords){
        this.possibleWords = possibleWords;
    char [] correctLetters = new char[score.length];
    for( int i = 0 ; i < score.length; i++){
        if(score[i] == 2 || score[i] == 1){correctLetters[i] = guessToArray[i];}
    }
        for(int i = 0; i < score.length; i++){
            if(score[i] == 0){
                possibleWords = removeGrays(guessToArray[i], possibleWords, correctLetters, i);
            }
            if(score[i] == 1){
                possibleWords = removeYellows(guessToArray[i], possibleWords, i);
            }
            if(score[i] == 2){
                possibleWords = removeGreens(guessToArray[i], possibleWords, i);
            }
        }

return possibleWords;
    }
    public LinkedList<String> removeGrays(char character, LinkedList<String> possibleWords, char[] correctLetters, int index){
        String currentChar = String.valueOf(character);
        String correct = Arrays.toString(correctLetters);
        for(int i =0 ; i<possibleWords.size(); i++){
            if(possibleWords.get(i).contains(currentChar) == true && correct.contains(currentChar) == false) {possibleWords.remove(i);i--;}
            else if(possibleWords.get(i).contains(currentChar) == true && correct.contains(currentChar) == true && character  == possibleWords.get(i).charAt(index)) {
                possibleWords.remove(i);
                i--;
            }
            }

        return possibleWords;
    }
    public LinkedList<String> removeYellows(char character, LinkedList<String> possibleWords, int index){
        String currentChar = String.valueOf(character);
        for(int i =0 ; i<possibleWords.size(); i++){
            if(possibleWords.get(i).contains(currentChar) == false) {possibleWords.remove(i);i--;}
            else if(character  == possibleWords.get(i).charAt(index)){
            possibleWords.remove(i);
            i--;
        }}
        return possibleWords;
    }
    public LinkedList<String> removeGreens(char character, LinkedList<String> possibleWords, int index){
        for(int i = 0; i < possibleWords.size(); i++)
        if(character  != possibleWords.get(i).charAt(index)){
            possibleWords.remove(i);
            i--;
        };

        return possibleWords;
    }

}
