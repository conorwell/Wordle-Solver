import java.util.*;

public class ScoreSolve extends Wordle{


    public static void main(String[] args) {

        System.out.println("How long should the wordle be?");
        int lengthInput = getIntInput(2, 10);
        ScoreSolve s = new ScoreSolve(lengthInput);

    }

    public ScoreSolve(int wordleLength) {
        super(wordleLength);
        super.playWordle();
    }

    @Override
    public char[] getInput(){
        while (true){
            String input = chooseNext(scoreList(wordleLength, currentPossibleWords));
            if (input.length() == wordleLength){
                return input.toLowerCase().toCharArray();
            } else{
                System.out.println("Your guess needs to be " + wordleLength + " letters long");
            }
        }
    }

    public String chooseNext(HashMap<String, Long> wordScore){
        List<String> hashKeys = new ArrayList<String>(wordScore.keySet());
        String highScore = null;
        long tempScore = 0;
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

    public HashMap<String, Long> scoreList(int wordleLength, HashMap<String, Long> wordScore){
        String baseWord;
        char[] nextWordChar;
        List<String> keysAsList = new ArrayList<String>(wordScore.keySet());
        for(int i = 0; i <wordScore.size();i++){
            long totalScore = 0;
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

    public int scoreToInt(int[] score) {
        int sum = 0;
        for (int i = 0; i < score.length; i++) {
            sum += score[i];
        }
        return sum;
    }

}
