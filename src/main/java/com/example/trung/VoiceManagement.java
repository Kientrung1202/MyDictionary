package com.example.trung;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class VoiceManagement {
    private Voice voice;//Creating object of Voice class

    public VoiceManagement() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        voice = VoiceManager.getInstance().getVoice("kevin16");//Getting voice
        if (voice == null) {
            System.out.println("Can't setup voice.");
        }
    }

    public void speak(String text) {
        if (voice != null) {
            voice.allocate();//Allocating Voice
            try {
                voice.setRate(148);//Setting the rate of the voice
                voice.setPitch(90);//Setting the Pitch of the voice
                voice.setVolume(20);//Setting the volume of the voice
                voice.speak(text);//Calling speak() method
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Can't get voice.");
        }

    }
}
