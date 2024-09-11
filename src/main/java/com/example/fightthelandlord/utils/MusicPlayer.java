package com.example.fightthelandlord.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.io.File;

public class MusicPlayer {
    private static MediaPlayer mediaPlayer;

    public static void playMusic(String filePath) {
        // 停止当前的音乐
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        // 播放新的音乐
        //Media media = new Media(getClass().getResource("/media/PW~GHMB").toExternalForm());

        Media media = new Media(filePath);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
    public static void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}