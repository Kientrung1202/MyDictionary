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

    private static Map<String, Word> wordList = new LinkedHashMap<>();

    public static void initialize() throws IOException {
        wordListInitialize();
    }

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
    }

    public static Word lookupWord(String englishWord) {
        if (!wordList.containsKey(englishWord)) return null;

        Word result = wordList.get(englishWord);
        var tmpRef = new Object() {
            String partsOfSpeech = "";
        }; //Create a new temporary object just to use the string pOS in functional interface instantiation.

        result.getVietnamText().lines().forEach(line -> {
            if (line.startsWith("* ")) {
                tmpRef.partsOfSpeech += line.substring(2) + "\n";
            }
        });

        result.setPartOfSpeech(tmpRef.partsOfSpeech);
        // We have to process parts of speech appending as above coz it haven't had yet.
        return result;
    }

    public static boolean addAWord(Word newWord) throws IOException {
        boolean wordExist = wordList.containsKey(newWord.getEnglishText());
        //May supplement pronunciation adding feature(optional).
        if (!wordExist && !newWord.getVietnamText().equals("") && !newWord.getEnglishText().equals("")) {
            String engWord = newWord.getEnglishText();
            String pronunciation = newWord.getPronunciation();
            String partOfSpeech = newWord.getPartOfSpeech();
            String meaning = newWord.getVietnamText();

            wordList.put(engWord, new Word(engWord, pronunciation, meaning));
            String wordToAppend = engWord + " " + pronunciation + "\n" + meaning;
            Files.write(Paths.get(path), //append the word just added
                    wordToAppend.getBytes(),
                    StandardOpenOption.APPEND);
            return true;
        }
        return false;
    }
    public static void editWord(String replacedWord) {

    }
    public static void removeWord(String word) {

    }
    public static void transferWordFromFileToList() {

    }

    private static String getPronunciation(final String firstLine) {
        String[] arr = firstLine.split(" ");
        if (arr.length > 1) {
            return arr[1];
        }
        return "";
    }

    private static String getWord(final String firstLine) { //only used for the line that contains main Word.
        String[] arr = firstLine.split(" ");
        if (firstLine.charAt(1) == '@') {
            return arr[0].substring(2);
        } else {
            return arr[0].substring(1);
        }
    }
}
