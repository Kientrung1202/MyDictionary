package com.example.trung;

import java.io.*;
import java.lang.String;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class DictionaryManagement {
    private static final String path = "src/main/resources/data/raw dictionary(en-vi).txt";

    private static Map<String, Word> wordList = new LinkedHashMap<>(104000);

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
        System.out.println(wordList.size());
    }

    /**
     *
     * @param engWord String.
     * @return
     */
    public static Word lookUp(String engWord) {
        if (engWord.isBlank()) {
            return null;
        }
        engWord = removeExtraSpaces(engWord);
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

    /**
     * Add new word
     * @param newWord
     * @return true if adding successfully.
     * @throws IOException
     */
    public static boolean addAWord(Word newWord) throws IOException {
        if (newWord.getEnglishText().isBlank()) {
            return false;
        }
        boolean wordExist = wordList.containsKey(newWord.getEnglishText());

        if (!wordExist && !newWord.getVietnamText().equals("") && !newWord.getEnglishText().equals("")) {
            String engWord = newWord.getEnglishText();
            String pronunciation = newWord.getPronunciation();
            String meaning = newWord.getVietnamText();
            newWord.setPartsOfSpeech();

            wordList.put(engWord, new Word(engWord, pronunciation, meaning));
            String wordToAppend = "@" + engWord + " " + pronunciation + "\n" + meaning;
            Files.write(Paths.get(path), //append the word just added
                    wordToAppend.getBytes(),
                    StandardOpenOption.APPEND);
            return true;
        }
        return false;
    }

    /**
     * Edit/Replace specified word with another word.
     * @param replacedWord
     * @param newWord
     * @return true when the word is edited successfully
     * @throws IOException
     */
    public static boolean editWord(Word replacedWord, Word newWord) throws IOException {
        String replacedWordString = replacedWord.getEnglishText();
        String newWordString = newWord.getEnglishText();

        Word result = lookUp(replacedWordString);
        if (result == null) {
            return false;
        }
        if (replacedWordString.equals(newWordString)) {
            return false;
        }

        String pronunciation = newWord.getPronunciation();
        String meaning = newWord.getVietnamText();

        wordList.remove(replacedWord);
        wordList.put(newWordString, new Word(newWordString, pronunciation, meaning));

        writeToFile();
        return true;
    }

    /**
     *  Remove specified word.
     * @param word
     * @return true of false
     * @throws IOException
     */
    public static boolean removeWord(String word) throws IOException {
        if (!wordList.containsKey(word)) {
            return false;
        }
        wordList.remove(word);
        writeToFile();
        return true;
    }

    /**
     * Get suggested words list.
     * @param text
     * @return list of ArrayList type.
     */
    public static ArrayList<String> getSuggestedWords(String text) {
        final int SIZE_LIMIT = 30;
        text = text.toLowerCase(Locale.ROOT);

        LinkedHashSet<String> tempSet = new LinkedHashSet<>();
        ArrayList<String> list = new ArrayList<>(); //return this arraylist

        for (int index = 0, count = 0; (index < text.length() / 2) && (count != SIZE_LIMIT); index++) {
            //consider both lowercase word and capitalized word.
            String subText = text.substring(0, text.length() - index);
            String capSubText = getCapitalizedWord(subText);

            for(String word : wordList.keySet()) {
                if (count == SIZE_LIMIT) {
                    list.addAll(tempSet);
                    return list;
                }
                if (word.startsWith(subText) || word.startsWith(capSubText)) {
                    count++;
                    tempSet.add(word);
                }
            }
        }
        list.addAll(tempSet);
        return list;
    }

    /**
     * Get pronunciation part from the first line of each word in text file(maybe contain or not).
     * @param firstLine
     * @return
     */
    private static String getPronunciation(final String firstLine) {
        String[] arr = firstLine.split(" /");
        if (arr.length > 1) {
            return "/" + arr[1];
        }
        return "";
    }

    /**
     * Only used for the line of each word in text file.
     * @param firstLine
     * @return
     */
    private static String getWord(final String firstLine) {
        String[] arr = firstLine.split(" /");
        String result = arr[0];
        if (result.endsWith(" ")) { //the argument 'line' sometimes inserts " " in the end of the string??
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

    /**
     * For words those capitalized version exist in wordlist.
     * @param word String
     * @return
     */
    private static String getCapitalizedWord(String word) {
        char tmp = word.charAt(0);
        if (tmp >= 'A' && tmp <= 'Z') return word;
        Character oldChar = tmp;
        tmp -= 32;
        Character newChar = tmp;
        return word.replaceFirst(oldChar.toString(), newChar.toString());
    }

    private static String removeExtraSpaces(String text) {
        //find the first letter of the word and remove all the spaces before it(leading space).
        String[] tmpArr = text.split(" ");
        ArrayList<String> strArray = new ArrayList<>(); //Create a string array whose all elements are not blank
        for (int i = 0; i < tmpArr.length; i++) {
            if (!tmpArr[i].isBlank()) {
                strArray.add(tmpArr[i].strip()); //and add every stripped sequence.
            }
        }

        StringBuilder result = new StringBuilder("");

        for (int i = 0; i < strArray.size(); i++) {
            if (i == strArray.size() - 1) {
                result.append(strArray.get(i));
            }
            else { //check next sequence, if it satisfies the following cond then not appending a whitespace behind.
                String nextSequence = strArray.get(i + 1);
                if (nextSequence.equals(",") || nextSequence.equals(".")
                        || nextSequence.equals(";") || nextSequence.equals("?")) {
                    result.append(strArray.get(i));
                }
                else result.append(strArray.get(i) + " ");
            }
        }
        return result.toString();
    }
}
