package ru.samsung.gamestudio.components;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextView {

    float x;
    float y;

    private float width;
    private float height;

    private BitmapFont font;
    private String text;

    public TextView(BitmapFont font, float x, float y) {
        this.font = font;
        this.x = x;
        this.y = y;
    }

    public TextView(BitmapFont font, float x, float y, String text) {
        this(font, x, y);
        this.text = text;

        GlyphLayout glyphLayout = new GlyphLayout(font, text);
        width = glyphLayout.width;
        height = glyphLayout.height;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void draw(String text, SpriteBatch batch) {
        GlyphLayout glyphLayout = new GlyphLayout(font, text);
        font.draw(batch, text, x, y + glyphLayout.height);
    }

    public void draw(SpriteBatch batch) {
        font.draw(batch, text, x, y + height);
    }

    public boolean isHit(float tx, float ty) {
        return tx >= x && tx <= x + width && ty >= y && ty <= y + height;
    }

    public void dispose() {
        font.dispose();
    }

}
