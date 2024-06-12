package ru.paulmy.tractorfield.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

import ru.paulmy.tractorfield.GameSettings;

public class TrashObject extends GameObject {

    private static final int paddingHorizontal = 30;
    private int trashType = 0;
    private int livesLeft;

    public TrashObject(int width, int height, String texturePath, World world, int trashType) {
        super(
                texturePath,
                width / 2 + paddingHorizontal + (new Random()).nextInt((GameSettings.SCREEN_WIDTH - 2 * paddingHorizontal - width)),
                GameSettings.SCREEN_HEIGHT + height / 2,
                width, height,
                GameSettings.TRASH_BIT,
                world
        );

        this.trashType = trashType;
        body.setLinearVelocity(new Vector2(0, -GameSettings.TRASH_VELOCITY));
        livesLeft = 1;
    }

    public int getTrashType() {
        return trashType;
    }

    public void setTrashType(int trashType) {
        this.trashType = trashType;
    }

    public boolean isAlive(int type) {
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
