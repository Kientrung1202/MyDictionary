package com.example.trung;

import java.io.*;
import java.lang.String;

public class DictionaryManagement {
    private static final String path = "C:\\Users\\ADMIN\\OneDrive\\Desktop\\trung\\src\\main\\resources\\data\\trung.txt";

    public static Word lookupWord(String englishText) throws FileNotFoundException {
        englishText = englishText.toLowerCase();
        Word res = new Word();
        res.setEnglishText(englishText);
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = bufferedReader.readLine();
            boolean check = true;
            // cấu trúc file là @từ /danhvan/
            while (line != null) {
                // check line.length() trước bvi có dòng trống nên line.charAt == '@' là lỗi
                if ( line.length() > englishText.length() && line.charAt(0) == '@') {
                    // compare to englishText
                    for (int j = 0; j < englishText.length(); j++) {
                        if (line.charAt(j + 1) != englishText.charAt(j)) {
                            check = false;
                            break;
                        }
                    }
                } else {
                    check = false;
                }
                if (check) {
                    String[] firstLine = line.split("/");
                    res.setPronunciation("/" + firstLine[1] + '/');
                    String vietnamText = "";
                    line = bufferedReader.readLine();
                    vietnamText += line + '\n';
                    while (line.length() != 0) {// ban đầu dùng line.charAt(0) != '@' sai do có dòng trống
                        line = bufferedReader.readLine();
                        vietnamText += line + '\n';
                    }
                    res.setVietnamText(vietnamText);
                    return res;
                } else {
                    check = true;
                    line = bufferedReader.readLine();
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
