
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class WordFrequencySorter {
    static String fileName;
    static HashMap wordCount;
    static String directoryName;
    static ArrayList<String> words;
    static   String mostRepeatedWord;


    public static ArrayList[] read(String fileName) throws FileNotFoundException {
        File text = new File(fileName);
        Scanner fileReader = new Scanner(text);
        int value;
        int set;
        ArrayList<String>[] sets = new ArrayList [15];
        for(int i =0; i<sets.length; i++){
            sets[i] = new ArrayList<>();
        }
        while(fileReader.hasNextLine()){
            String line = fileReader.nextLine();
            line = line.toLowerCase();
            String [] data = line.split(",");
            set = data[0].length();
            if(set>1 && set<11){
            sets[set].add(data[0] + " " + data[1]);}
        }

        return sets;
    }
    public static void write() throws IOException {
        ArrayList<String> [] sets = new ArrayList[20];
        String currentFile;
        String fileName = "wordleData/OPTED-Dictionary.csv";
        sets = read(fileName);
        for(int i = 1; i<sets.length; i++){
            currentFile = "wordleData/validWordLength" + i + ".txt";
            FileWriter writer = new FileWriter(currentFile);
            for(int j = 0; j<sets[i].size(); j++)
            {
            //  if(clean(sets[i].get(j))==true){
            writer.write(sets[i].get(j));
            writer.write("\n");}
           // }
            writer.close();


        }
    }
    public static void clean() throws IOException {
        int currentLength = 10;
        File dictionary = new File("WordleData/validWordLength" + currentLength + ".txt");
        Scanner fileReader = new Scanner(dictionary);
        ArrayList validWords = new ArrayList(100);
        boolean valid = false;
        while(fileReader.hasNextLine()){
            String word = fileReader.nextLine();
            word = word.toLowerCase();
            String [] data = word.split(" ");
            word = data[0];
            if(word.length() < 11 && word.length() >1)
            validWords.add(word);
    }
        File words = new File("WordleData/wordLength" + currentLength + ".txt");
        Scanner wordReader = new Scanner(words);
        ArrayList allWords = new ArrayList(100);
        HashMap wordsAndFreqs = new HashMap<String, Integer>();
        while(wordReader.hasNextLine()){
            String thisWord = wordReader.nextLine();
            thisWord = thisWord.toLowerCase();
            String [] datas = thisWord.split(" ");
            thisWord = datas[0];
            if(thisWord.length() < 11 && thisWord.length() >1)
                allWords.add(thisWord);
            wordsAndFreqs.put(datas[0],datas[1]);
        }
        FileWriter cleaner = new FileWriter ("WordleData/wordLength" + currentLength + ".txt");
        String currentInput = null;
        for(int i = 0; i< allWords.size(); i++){
            for( int j = 0; j<validWords.size(); j++){
                if(allWords.get(i).equals(validWords.get(j)) == true && allWords.get(i).equals(currentInput) != true){
                    currentInput = (String) allWords.get(i);
                    cleaner.write(allWords.get(i) + " " + wordsAndFreqs.get(allWords.get(i)) + "\n");
                }
            }
        }
cleaner.close();
    }

    public static void main(String[] args) throws IOException {
        clean();
    }

}
