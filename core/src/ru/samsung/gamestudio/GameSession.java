package ru.samsung.gamestudio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class GameSession {

    public GameState state;
    long nextEnemySpawnTime;
    long sessionStartTime;

    public GameSession() {
    }

    public void startGame() {
        state = GameState.PLAYING;
        sessionStartTime = TimeUtils.millis();
        nextEnemySpawnTime = sessionStartTime + (long) (GameSettings.STARTING_ENEMY_APPEARANCE_COOL_DOWN
                * getEnemyPeriodCoolDown());
    }

    public void pauseGame() {
        state = GameState.PAUSED;
    }

    public void resumeGame() {
        state = GameState.PLAYING;
    }

    public boolean shouldSpawnTrash() {
        if (nextEnemySpawnTime <= TimeUtils.millis()) {
            nextEnemySpawnTime = TimeUtils.millis() + (long) (GameSettings.STARTING_ENEMY_APPEARANCE_COOL_DOWN
                    * getEnemyPeriodCoolDown());
            return true;
        }
        return false;
    }

    private float getEnemyPeriodCoolDown() {
        return (float) Math.exp(-0.001 * (TimeUtils.millis() - sessionStartTime + 1) / 1000);
    }
}
