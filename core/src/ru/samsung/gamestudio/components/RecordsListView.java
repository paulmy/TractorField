package ru.samsung.gamestudio.components;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.samsung.gamestudio.GameSettings;

import java.util.ArrayList;

public class RecordsListView extends View {

    BitmapFont font;
    String recordsListString;

    public RecordsListView(BitmapFont font, float y) {
        super(0, y);
        this.font = font;
        recordsListString = "";
    }

    public void setRecords(ArrayList<Integer> recordsList) {
        recordsListString = "";
        int countOfRows = Math.min(recordsList.size(), 5);
        for (int i = 0; i < countOfRows; i++) {
            System.out.println(recordsList.get(i));
            recordsListString += (i + 1) + ". - " + recordsList.get(i) + "\n";
        }

        GlyphLayout glyphLayout = new GlyphLayout(font, recordsListString);
        x = (GameSettings.SCREEN_WIDTH - glyphLayout.width) / 2;
    }

    @Override
    public void draw(SpriteBatch batch) {
        font.draw(batch, recordsListString, x, y);
    }

    @Override
    public void dispose() {
        font.dispose();
    }

}
