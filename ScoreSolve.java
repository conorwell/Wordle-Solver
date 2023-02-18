import java.util.*;
import java.util.stream.IntStream;

public class ScoreSolve extends Wordle{


    Wordle wordle;
    ArrayList<String> possibleWords;
    int guessCounter;
    String guess;
    int total;

    public static void main(String[] args) {

        System.out.println("How long should the wordle be?");
        int lengthInput = getIntInput(2, 10);
        ScoreSolve s = new ScoreSolve(lengthInput);


    }

    public ScoreSolve(int wordleLength) {
        super(wordleLength);
        char[] guess;
        guessCounter = 0;
        int[] score = {0};
        Random rand = new Random();
        possibleWords = new ArrayList<String>(nLetterWordHashMap.keySet());
        while(IntStream.of(score).anyMatch(x -> x == 0) || IntStream.of(score).anyMatch(x -> x == 1)) {
            guess = chooseNext(compare(wordleLength)).toCharArray();
            score = scoreWord(guess, correctWord);
            for (char i: guess){
                System.out.print(i + ", ");
            }
            System.out.println();
            for (int i: score){
                System.out.print(i + ", ");
            }
            System.out.println();
        }
        System.out.println("You win!! The word was " + String.valueOf(correctWord));

    }

    public String chooseNext(HashMap<String, Integer> wordScore){
        List<String> hashKeys = new ArrayList<String>(wordScore.keySet());
        String highScore = null;
        int tempScore = 0;
        for(int i = 0; i<wordScore.size();i++){
            if(wordScore.get(hashKeys.get(i)) > tempScore){
                tempScore = wordScore.get(hashKeys.get(i));
            }
        }
        for(int j = 0;j<wordScore.size();j++){
            if(wordScore.get(hashKeys.get(j)) == tempScore){
                highScore = hashKeys.get(j);
            }
        }
        return highScore;
    }

    public HashMap<String, Integer> compare(int wordleLength){
        HashMap<String, Integer> wordScore = new HashMap<>();
        String baseWord;
        char[] nextWordChar;
        List<String> keysAsList = new ArrayList<String>(nLetterWordHashMap.keySet());
        for(int i = 0; i <nLetterWordHashMap.size();i++){
            int totalScore = 0;
            baseWord = keysAsList.get(i);
            char[] baseWordChar = baseWord.toCharArray();

            for(int j = 0; j<keysAsList.size();j++){
                nextWordChar = keysAsList.get(j).toCharArray();
                totalScore += scoreToInt(scoreWord(baseWordChar, nextWordChar));
                wordScore.put(baseWord, totalScore);
            }
        }
        return wordScore;
    }

    public int scoreToInt(int[] score){
        int sum = 0;
        for(int i = 0; i<score.length;i++){
            sum += score[i];
        }
        return sum;
    }



}
