package ru.samsung.gamestudio.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.GameSettings;

import java.util.Random;

public class TrashObject extends GameObject {

    private static final float paddingHorizontal = 30f;

    private int livesLeft;

    public TrashObject(World world) {
        super(
                GameResources.TRASH_IMG_PATH,
                140f / 2 + paddingHorizontal + (new Random()).nextInt((int) (GameSettings.SCREEN_WIDTH - 2 * paddingHorizontal - 140)),
                GameSettings.SCREEN_HEIGHT + 100f / 2,
                140, 100,
                GameSettings.TRASH_BIT,
                world
        );

        body.setLinearVelocity(new Vector2(0, -GameSettings.TRASH_VELOCITY));
        livesLeft = 1;
    }

    public boolean isAlive() {
        return livesLeft > 0;
    }

    public boolean isInFrame() {
        return getY() + height / 2 > 0;
    }

    @Override
    public void hit() {
        livesLeft -= 1;
    }
}
