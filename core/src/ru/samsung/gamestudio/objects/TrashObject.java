package ru.samsung.gamestudio.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import ru.samsung.gamestudio.GameSettings;

import java.util.Random;

public class TrashObject extends GameObject {

    private static final float paddingHorizontal = 30f;

    public TrashObject(World world) {
        super(
                "textures/trash.png",
                140f / 2 + paddingHorizontal + (new Random()).nextInt((int) (GameSettings.SCREEN_WIDTH - 2 * paddingHorizontal - 140)),
                GameSettings.SCREEN_HEIGHT + 100f / 2,
                140, 100,
                world
        );

        body.setLinearVelocity(new Vector2(0, -GameSettings.TRASH_VELOCITY));
    }

    public boolean isInFrame() {
        return getY() + height / 2 > 0;
    }

}
