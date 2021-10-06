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
            // cấu trúc file là @từ /danhvan/, có cả @tu
            while (line != null) {
                // check line.length() trước bvi có dòng trống nên line.charAt == '@' là lỗi
                if ( line.length() > englishText.length() && line.charAt(0) == '@') {
                    // check nếu mà từ nó có phần pronun thì độ dài của phần đứng trước "/" lớn hơn từ cần tìm 2 chữ.
                    if (line.length() - englishText.length() >= 2 ) {
                        String[] firstLine = line.split("/");
                        if(firstLine[0].length() - englishText.length() > 2) {
                            check = false;
                        }
                    }
                    // compare to englishText
                    if(check) {
                        for (int j = 0; j < englishText.length(); j++) {
                            if (line.charAt(j + 1) != englishText.charAt(j)) {
                                check = false;
                                break;
                            }
                        }
                    }
                } else {
                    check = false;
                }
                if (check) {
                    if (line.length() > englishText.length() + 1) {
                        String[] firstLine = line.split("/");
                        res.setPronunciation("/" + firstLine[1] + '/');
                    } else if (line.length() == englishText.length() + 1) {
                        res.setPronunciation("");
                    }
                    String vietnamText = "";
                    line = bufferedReader.readLine();
                    while ( line != null && line.length() != 0 && line.charAt(0) != '@') {// ban đầu dùng line.charAt(0) != '@' sai do có dòng trống
                        vietnamText += line + '\n';
                        line = bufferedReader.readLine();
                    }
                    res.setVietnamText(vietnamText);
                    return res;
                } else {
                    check = true;
                    line = bufferedReader.readLine();
                }
            }
            bufferedReader.close();
//            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
    public static boolean addAWord(Word input) throws IOException {
        File file = new File(path);
        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            if(input.getVietnamText()!= "" && input.getEnglishText() != "") {
                bufferedWriter.write("@" + input.getEnglishText() + "\n" + input.getVietnamText()+"\n");
                bufferedWriter.close();
                fileWriter.close();
                System.out.println("oke");
                return true;
            }
            else {
                return false;
            }
    }
}
