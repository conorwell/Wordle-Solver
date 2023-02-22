import java.util.*;
import java.util.stream.Collectors;

public class EntropyMaximizer extends Wordle{


    public EntropyMaximizer(int wordleLength) {
        super(wordleLength);
        firstTime = true;
    }

    public static void main(String[] args) {
        System.out.println("How long should the wordle be?");
        int lengthInput = getIntInput(2, 10);
        EntropyMaximizer w = new EntropyMaximizer(lengthInput);
        w.playWordle();
//        testWordle(5, 100);
    }

    public boolean firstTime;

    public char[] getInput() {
        if (firstTime){
            firstTime = false;
            if (returnHardCoded() != null){
                return returnHardCoded();
            }
        }
        //Get all the entropies and return the key of the first entry (the word with the largest entropy)
        HashMap.Entry<String, Double> maxEntropy = getEntropies().entrySet().iterator().next();
        System.out.println("Entropy of " + maxEntropy.getKey() + " is " + maxEntropy.getValue());
        return maxEntropy.getKey().toCharArray();
    }

    public LinkedHashMap<String,Double> getEntropies(){
        HashMap<String,Double> entropies = new HashMap<>();
        for(HashMap.Entry<String,Long> wordEntry :currentPossibleWords.entrySet()){
            HashMap<String, Integer> scores = getAllPossibleScores(wordEntry.getKey().toCharArray());
            double entropy = 0;
            for(HashMap.Entry<String,Integer> scoreEntry :scores.entrySet()){
                double probOfScore = scoreEntry.getValue()/(double) currentPossibleWords.size();
                double infoOfScore = logBase2(1/probOfScore);
                entropy += probOfScore * infoOfScore;
            }
            entropies.put(wordEntry.getKey(), entropy);
        }

        LinkedHashMap<String,Double> sortedEntropies =
                entropies.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return sortedEntropies;
    }

    public HashMap<String, Integer> getAllPossibleScores(char[] word){
        HashMap<String, Integer> scores = new HashMap<>();
        for(HashMap.Entry<String,Long> wordToScore :currentPossibleWords.entrySet()){
            int[] score = scoreWord(word, wordToScore.getKey().toCharArray());
            String stringScore = Arrays.toString(score);
            if (!scores.containsKey(stringScore)){
                scores.put(stringScore, 1);
            } else{
                scores.replace(stringScore, scores.get(stringScore) + 1);
            }
        }
        return scores;
    }

    public static double logBase2 (double x){
        return (Math.log(x) / Math.log(2));
    }

    public char[] returnHardCoded(){
        if (wordleLength == 2){
            return "no".toCharArray();
        }
        if (wordleLength == 3){
            return "eat".toCharArray();
        }
        if (wordleLength == 4){
            return "lare".toCharArray();
        }
        if (wordleLength == 5){
            return "raise".toCharArray();
        }
        if (wordleLength == 6){
            return "santer".toCharArray();
        }
        if (wordleLength == 7){
            return "saltier".toCharArray();
        }
        if (wordleLength == 8){
            return "caroline".toCharArray();
        }
        if (wordleLength == 9){
            return "sectorial".toCharArray();
        }
        if (wordleLength == 10){
            return "centralism".toCharArray();
        }
        return null;
    }
}
