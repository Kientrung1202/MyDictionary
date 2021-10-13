package com.example.trung;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.io.*;


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
                        // check xem cos phai tu do khong, vi co the chu cai dau giong nhau
                        int size = firstLine.length;
                        if( size != 0 && firstLine[0].length() - englishText.length() > 2) {
                            check = false;
                        } else if(size == 1) check = false ;
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
                    String type = "";
                    line = bufferedReader.readLine();
                    while ( line != null && line.length() != 0 && line.charAt(0) != '@') {// ban đầu dùng line.charAt(0) != '@' sai do có dòng trống
                        vietnamText += line + '\n';
                        if (line.charAt(0) == '*') {
                            type += line + '\n';
                        }
                        line = bufferedReader.readLine();
                    }
                    res.setVietnamText(vietnamText);
                    res.setType(type);
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
        boolean exis = !lookupWord(input.getEnglishText()).getVietnamText().equals("Khong co tu nay trong tu dien");
        ;
            if( !exis && !input.getVietnamText().equals("") && !input.getEnglishText().equals("")) {
                bufferedWriter.write("@" + input.getEnglishText() + "\n" + input.getVietnamText()+"\n");
                bufferedWriter.close();
                fileWriter.close();
                return true;
            }
            else {
                return false;
            }
    }
    public static void speakVoiceEn(String text) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");//Getting voice
        if (voice != null) {
            voice.allocate();//Allocating Voice
        }
        try {
            voice.setRate(150);//Setting the rate of the voice
            voice.setPitch(100);//Setting the Pitch of the voice
            voice.setVolume(5);//Setting the volume of the voice
            voice.speak(text);//Calling speak() method

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
