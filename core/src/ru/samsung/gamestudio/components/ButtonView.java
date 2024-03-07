package ru.samsung.gamestudio.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonView {

    Texture texture;
    BitmapFont bitmapFont;

    String text;

    float x;
    float y;

    float textX;
    float textY;

    float width;
    float height;

    public ButtonView(float x, float y, float width, float height, BitmapFont font, String texturePath, String text) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.text = text;
        this.bitmapFont = font;

        texture = new Texture(texturePath);

        GlyphLayout glyphLayout = new GlyphLayout(bitmapFont, text);
        float textWidth = glyphLayout.width;
        float textHeight = glyphLayout.height;

        textX = x + (width - textWidth) / 2;
        textY = y + (height + textHeight) / 2;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
        bitmapFont.draw(batch, text, textX, textY);
    }

    public boolean isHit(float tx, float ty) {
        return (tx >= x && tx <= x + width && ty >= y && ty <= y + height);
    }

    public void dispose() {
        texture.dispose();
        bitmapFont.dispose();
    }

}
