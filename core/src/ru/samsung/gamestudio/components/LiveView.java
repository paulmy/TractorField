package ru.samsung.gamestudio.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LiveView {

    private final static int livePadding = 6;

    float x;
    float y;

    float width;
    float height;

    Texture texture;

    public LiveView(float x, float y) {
        this.x = x;
        this.y = y;
        texture = new Texture("textures/life.png");
        this.width = texture.getWidth() ;
        this.height = texture.getHeight() ;
    }

    public void draw(int leftLive, SpriteBatch batch) {
        if (leftLive > 0) batch.draw(texture, x + (texture.getWidth() + livePadding) , y, width, height);
        if (leftLive > 1) batch.draw(texture, x, y, width, height);
        if (leftLive > 2) batch.draw(texture, x + 2 * (texture.getWidth() + livePadding) , y, width, height);
    }

    public void dispose() {
        texture.dispose();
    }


}
