package com.example.trung;

import java.io.*;
import java.lang.String;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.LinkedHashMap;

public class DictionaryManagement {
    private static final String path = "src/main/resources/data/raw dictionary(en-vi).txt";

    private static Map<String, Word> wordList = new LinkedHashMap<>(1024);

    private static void wordListInitialize() throws IOException { //read file to wordList
        BufferedReader reader = Files.newBufferedReader(FileSystems.getDefault().getPath(path));
        String line = "";

        while ((line = reader.readLine()) != null) {
            while (line.length() > 1 && (line.charAt(0) == '@' || line.charAt(1) == '@')) {
                String word = getWord(line);
                String pronunciation = getPronunciation(line);
                String meaning = "";

                while ((line = reader.readLine()) != null && (line.length() > 1) && (line.charAt(0) != '@')) {
                    meaning += line + "\n";
                }
                wordList.put(word, new Word(word, pronunciation, meaning));

                if (line == null) {
                    break;
                }
            }
        }
        reader.close();
        System.out.println(wordList.size());
    }

    public static void initialize() throws IOException {
        wordListInitialize();
    }

    public static Word lookUp(String engWord) {
        if (engWord.length() == 0) {
            return null;
        }
        engWord = engWord.toLowerCase();
        String capitalizedEngWord = getCapitalizedWord(engWord);
        if (!wordList.containsKey(engWord) && !wordList.containsKey(capitalizedEngWord)) {
            return null;
        }

        Word result = wordList.get(engWord);
        if (result == null) {
            result = wordList.get(capitalizedEngWord);
        }
        result.setPartsOfSpeech();
        return result;
    }

    public static boolean addAWord(Word newWord) throws IOException {
        boolean wordExist = wordList.containsKey(newWord.getEnglishText());
        //May supplement pronunciation adding feature(optional).
        if (!wordExist && !newWord.getVietnamText().equals("") && !newWord.getEnglishText().equals("")) {
            String engWord = newWord.getEnglishText();
            String pronunciation = newWord.getPronunciation();
            String meaning = newWord.getVietnamText();
            newWord.setPartsOfSpeech();

            wordList.put(engWord, new Word(engWord, pronunciation, meaning));
            String wordToAppend = engWord + " " + pronunciation + "\n" + meaning;
            Files.write(Paths.get(path), //append the word just added
                    wordToAppend.getBytes(),
                    StandardOpenOption.APPEND);
            return true;
        }
        return false;
    }

    //true when editing word successfully
    public static boolean editWord(String replacedWord, String newWord) throws IOException {
        Word result = lookUp(replacedWord);
        if (result == null) {
            System.out.println("'" + replacedWord + "' do not exist in dictionary.");
            return false;
        }
        if (replacedWord.equals(newWord)) {
            return false;
        }

        String currentPronunciation = wordList.get(replacedWord).getPronunciation();
        String currentMeaning = wordList.get(replacedWord).getVietnamText();
        wordList.remove(replacedWord);
        wordList.put(newWord, new Word(newWord, currentPronunciation, currentMeaning));

        writeToFile();
        return true;
    }

    public static boolean removeWord(String word) throws IOException {
        if (!wordList.containsKey(word)) {
            //notice about removing failure
            return false;
        }
        wordList.remove(word);
        writeToFile();
        return true;
    }

    private static String getPronunciation(final String firstLine) {
        String[] arr = firstLine.split(" /");
        if (arr.length > 1) {
            return "/" + arr[1];
        }
        return "";
    }

    private static String getWord(final String firstLine) { //only used for the line that contains main Word.
        String[] arr = firstLine.split(" /");
        String result = arr[0];
        if (result.endsWith(" ")) { //don't understand why the argument 'line' sometimes inserts " " in the end of the string??
            result = result.substring(0, result.length() - 1);
        }
        if (firstLine.charAt(1) == '@') {
            return result.substring(2);
        } else {
            return result.substring(1);
        }
    }

    private static void writeToFile() throws IOException {
        if (wordList.isEmpty()) return;

        FileWriter writer = new FileWriter(new File(path));
        wordList.forEach((engWord, wordMeaning) -> {
            try {
                writer.write("@" + engWord + " ");
                writer.write(wordMeaning.getPronunciation() + "\n"); //need to test \n, \r\n
                writer.write(wordMeaning.getVietnamText() + "\n");
            }catch(IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }

    private  static String getCapitalizedWord(String word) {
        char tmp = word.charAt(0);
        if (tmp >= 'A' && tmp <= 'Z') return word;
        Character oldChar = tmp;
        tmp -= 32;
        Character newChar = tmp;
        return word.replaceFirst(oldChar.toString(), newChar.toString());
    }

    //    public static File getDictionaryFile() {
//        return new File(path);
//    }

//    public static void main(String[] args) {
//        String str = "FlslTc";
//        System.out.println(str.toLowerCase(Locale.ROOT));
//        System.out.println(str);
//    }
}
