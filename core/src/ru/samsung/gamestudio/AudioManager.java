package ru.samsung.gamestudio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {

    public boolean isSoundOn;
    public boolean isMusicOn;

    public Music backgroundMusic;
    public Sound shootSound;
    public Sound explosionSound;

    AudioManager() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/background_music.mp3"));
        shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.mp3"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/destroy.mp3"));

        isMusicOn = true;
        isSoundOn = true;

        backgroundMusic.setVolume(0.2f);
        backgroundMusic.setLooping(true);

        backgroundMusic.play();
    }

    public void updateMusicFlag() {
        if (isMusicOn) backgroundMusic.play();
        else backgroundMusic.stop();
    }

}
