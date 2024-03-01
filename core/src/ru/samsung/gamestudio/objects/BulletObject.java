package ru.samsung.gamestudio.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import ru.samsung.gamestudio.GameSettings;

public class BulletObject extends GameObject {

    public BulletObject(float x, float y, World world) {
        super("textures/bullet.png", x, y, 12, 44, world);
        body.setLinearVelocity(new Vector2(0, GameSettings.BULLET_VELOCITY));
        body.setBullet(true);
    }

    public boolean hasToBeDestroyed() {
        return getY() - height / 2 > GameSettings.SCREEN_HEIGHT;
    }

}
