package ru.paulmy.tractorfield;

import static ru.paulmy.tractorfield.GameSettings.POSITION_ITERATIONS;
import static ru.paulmy.tractorfield.GameSettings.STEP_TIME;
import static ru.paulmy.tractorfield.GameSettings.VELOCITY_ITERATIONS;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;

import ru.paulmy.tractorfield.managers.AudioManager;
import ru.paulmy.tractorfield.screens.GameScreen;
import ru.paulmy.tractorfield.screens.MenuScreen;
import ru.paulmy.tractorfield.screens.RecordScreen;
import ru.paulmy.tractorfield.screens.SettingsScreen;

public class MyGdxGame extends Game {

    public World world;

    public BitmapFont largeWhiteFont;
    public BitmapFont commonWhiteFont;
    public BitmapFont commonBlackFont;

    public Vector3 touch;
    public SpriteBatch batch;
    public OrthographicCamera camera;
    public AudioManager audioManager;

    public GameScreen gameScreen;
    public MenuScreen menuScreen;
    public RecordScreen recordScreen;
    public SettingsScreen settingsScreen;

    float accumulator = 0;

    @Override
    public void create() {

        Box2D.init();
        world = new World(new Vector2(0, 0), true);

        largeWhiteFont = FontBuilder.generate(48, Color.WHITE, GameResources.FONT_PATH);
        commonWhiteFont = FontBuilder.generate(24, Color.WHITE, GameResources.FONT_PATH);
        commonBlackFont = FontBuilder.generate(24, Color.BLACK, GameResources.FONT_PATH);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
        audioManager = new AudioManager();

        gameScreen = new GameScreen(this);
        menuScreen = new MenuScreen(this);
        settingsScreen = new SettingsScreen(this);
        recordScreen = new RecordScreen(this);
        setScreen(menuScreen);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }
}
