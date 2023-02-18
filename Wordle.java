import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class Wordle {

    char[] correctWord;
    int wordleLength;
    int guesses;
    HashMap<String, Long> nLetterWordHashMap;
    HashMap<String, Long> currentPossibleWords;

    public Wordle(int wordleLength){
        System.out.println("--------NEW WORDLE--------");
        this.wordleLength = wordleLength;
        try {
            nLetterWordHashMap = getNLetterWordHashMap(wordleLength);
            currentPossibleWords = nLetterWordHashMap;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Random random = new Random();
        List<String> keysAsList = new ArrayList<>(nLetterWordHashMap.keySet());
        correctWord = keysAsList.get(random.nextInt(keysAsList.size())).toCharArray();
        System.out.println("correctWord:" + String.valueOf(correctWord));
    }

    public static void startPlayerWordle(){
        System.out.println("How long should the wordle be?");
        int lengthInput = Wordle.getIntInput(2, 10);
        Wordle w = new Wordle(lengthInput);
        w.playWordle();
    }

    public int playWordle(){
        char[] wordInput;
        int[] score = {0};
        guesses = 0;
        while (IntStream.of(score).anyMatch(x -> x == 0) || IntStream.of(score).anyMatch(x -> x == 1)){
            wordInput = getInput();
            score = scoreWord(wordInput, correctWord);
            updatePossibleWords(score, wordInput);
            guesses++;


            printResult(wordInput, score);
        }
        System.out.println("You win!! You got " + String.valueOf(correctWord) + " in " + guesses + " guesses!");
        return guesses;
    }

    //This is the default getInput() Method. It gets user input.
    public char[] getInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Guess a " + wordleLength + " letter word");
        while (true){
            String input = scanner.nextLine();
            if (input.length() == wordleLength){
                return input.toLowerCase().toCharArray();
            } else{
                System.out.println("Your guess needs to be " + wordleLength + " letters long");
            }
        }
    }

    public void printResult(char[] word, int[] score){
        for (char i: word){
            System.out.print(Character.toUpperCase(i) + ",");
        }
        System.out.println();
        for (int i: score){
            System.out.print(i + ",");
        }
        System.out.println();
        System.out.println();
    }

    public static void testWordle(int wordLength, int count){
        HashMap<Integer, Integer> results = new HashMap<>();
        for(int i = 0; i < count; i++){
            EntropyMaximizer e = new EntropyMaximizer(wordLength);
            int result = e.playWordle();
            if (!results.containsKey(result)){
                results.put(result, 1);
            } else{
                results.replace(result, results.get(result) + 1);
            }
        }
        System.out.println(results);
        double sumOfGuesses = 0;
        int numOfGuesses = 0;
        for(Map.Entry<Integer, Integer> guessEntry :results.entrySet()){
            numOfGuesses += guessEntry.getValue();
            sumOfGuesses += guessEntry.getKey() * guessEntry.getValue();
        }
        double average = sumOfGuesses / numOfGuesses;
        System.out.println("On average it took " + average + " guesses!");
    }

    public void updatePossibleWords(int[] score, char[] inputtedWord){
        HashMap<String,Long> newCurrentPossibleWords = (HashMap<String,Long>) currentPossibleWords.clone();
        for(HashMap.Entry<String,Long> wordEntry :currentPossibleWords.entrySet()){
            if (!doesWordFitScore(wordEntry.getKey().toCharArray(), score, inputtedWord)){
                newCurrentPossibleWords.remove(wordEntry.getKey());
            }
        }
        currentPossibleWords = newCurrentPossibleWords;
    }

    public boolean doesWordFitScore(char[] testWord, int[] score, char[] scoredWord){
        return Arrays.equals(score, scoreWord(scoredWord, testWord));
    }

    public int[] scoreWord(char[] inputWord, char[] correctWord){
        int[] score = new int[inputWord.length];
        for (int i: score){i=0;}
        char[] mutableCorrectWord = correctWord.clone();
        char[] mutableInputWord = inputWord.clone();
        for (int i = 0; i < mutableInputWord.length; i++){
            if (mutableInputWord[i] == mutableCorrectWord[i]){
                score[i] = 2;
                mutableInputWord[i] = '*';
                mutableCorrectWord[i] = '*';
            }
        }
        for (char ch: mutableCorrectWord){
            if (ch != '*'){
                int firstIndex = getFirstIndex(mutableInputWord, ch);
                if (firstIndex != -1){
                    score[firstIndex] = 1;
                }
            }
        }
        return score;
    }

    public static void initializeWordles(int minWordLength, int maxWordLength){
        for (int i = minWordLength; i <= maxWordLength; i++){
            Wordle w = new Wordle(i);
        }
    }

    public int getFirstIndex(char[] word, char target){
        for (int i = 0; i < word.length; i++){
            if (word[i] == target){
                return i;
            }
        }
        return -1;
    }

    public static int getIntInput(int min, int max){
        Scanner scanner = new Scanner(System.in);
        while (true){
            String input = scanner.nextLine();
            int intInput;
            try{
                intInput = Integer.parseInt(input);
            } catch (NumberFormatException nfe) {
                System.out.println("Input an integer");
                continue;
            }
            if (intInput <= max && intInput >= min){
                return intInput;
            } else {
                System.out.println("Input an integer between " + min + " and " + max);
            }
        }
    }

    public HashMap<String, Long> getNLetterWordHashMap(int wordLength) throws IOException, ClassNotFoundException {
        HashMap<String,Long> outputMap = new HashMap<>();
        String desiredDirectory = "src/HashMaps/" + wordLength + "LetterWordHashMap";
        try {
            outputMap = readMapFromFile(new File(desiredDirectory));
            return outputMap;
        }catch (Exception e){
            //Code for making a hashmap from an n letter word text file
            outputMap = makeHashMapFromTxt(new File("src/TextFiles/wordLength" + wordLength + ".txt"));

            //Code for copying a given word length from the global hashmap
//            HashMap<String, Long> allWordHashMap = readMapFromFile(new File("src/AllWordHashMap"));
//            for(HashMap.Entry<String,Long> m :allWordHashMap.entrySet()){
//                if(m.getKey().length() == wordLength){
//                    outputMap.put(m.getKey(), m.getValue());
//                }
//            }
            File newFile = new File(desiredDirectory);
            writeMapToFile(outputMap, newFile);
        }
        return outputMap;
    }

    public static HashMap<String, Long> readMapFromFile(File file) throws IOException, ClassNotFoundException {
        HashMap<String,Long> mapInFile;
        FileInputStream fis=new FileInputStream(file);
        ObjectInputStream ois=new ObjectInputStream(fis);

        mapInFile=(HashMap<String,Long>)ois.readObject();

        ois.close();
        fis.close();
        return mapInFile;
    }

    public static HashMap<String, Long> makeHashMapFromTxt(File file) throws FileNotFoundException {
        HashMap<String, Long> outputMap = new HashMap<>();
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("\n");
        //This skips the first item which is irrelevant
        while (scanner.hasNext()) {
            String line = scanner.next();
            String[] pair = line.split(" ");
            Long count = Long.parseLong(pair[1]);
            outputMap.put(pair[0], count);
        }
        scanner.close();
        return outputMap;
    }

    public static void writeMapToFile(HashMap<String, Long> map, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(map);
        oos.flush();
        oos.close();
        fos.close();
    }
}
