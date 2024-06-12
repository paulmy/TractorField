package ru.paulmy.tractorfield.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Random;

import ru.paulmy.tractorfield.GameResources;
import ru.paulmy.tractorfield.GameSession;
import ru.paulmy.tractorfield.GameSettings;
import ru.paulmy.tractorfield.GameState;
import ru.paulmy.tractorfield.MyGdxGame;
import ru.paulmy.tractorfield.components.ButtonView;
import ru.paulmy.tractorfield.components.ImageView;
import ru.paulmy.tractorfield.components.LiveView;
import ru.paulmy.tractorfield.components.MovingBackgroundView;
import ru.paulmy.tractorfield.components.RecordsListView;
import ru.paulmy.tractorfield.components.TextView;
import ru.paulmy.tractorfield.managers.ContactManager;
import ru.paulmy.tractorfield.managers.MemoryManager;
import ru.paulmy.tractorfield.objects.BulletObject;
import ru.paulmy.tractorfield.objects.TractorObject;
import ru.paulmy.tractorfield.objects.TrashObject;

public class GameScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;
    GameSession gameSession;
    TractorObject tractorObject;

    ArrayList<TrashObject> trashArray;
    ArrayList<BulletObject> bulletArray;

    ContactManager contactManager;

    // PLAY state UI
    MovingBackgroundView backgroundView;
    ImageView topBlackoutView;
    LiveView liveView;
    TextView scoreTextView;
    ButtonView pauseButton;

    // PAUSED state UI
    ImageView fullBlackoutView;
    TextView pauseTextView;
    ButtonView homeButton;
    ButtonView continueButton;

    // ENDED state UI
    TextView recordsTextView;
    RecordsListView recordsListView;
    ButtonView homeButton2;

    Random random;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        gameSession = new GameSession();

        contactManager = new ContactManager(myGdxGame.world);

        trashArray = new ArrayList<>();
        bulletArray = new ArrayList<>();

        tractorObject = new TractorObject(
                GameSettings.SCREEN_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.TRAC_IMG_PATH_START_GRAY,
                myGdxGame.world
        );

        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        topBlackoutView = new ImageView(0, 1180, GameResources.BLACKOUT_TOP_IMG_PATH);
        liveView = new LiveView(305, 1215);
        scoreTextView = new TextView(myGdxGame.commonWhiteFont, 50, 1215);
        pauseButton = new ButtonView(
                605, 1200,
                46, 54,
                GameResources.PAUSE_IMG_PATH
        );

        fullBlackoutView = new ImageView(0, 0, GameResources.BLACKOUT_FULL_IMG_PATH);
        pauseTextView = new TextView(myGdxGame.largeWhiteFont, 282, 842, "Pause");
        homeButton = new ButtonView(
                138, 695,
                200, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_SHORT_BG_IMG_PATH,
                "Home"
        );
        continueButton = new ButtonView(
                393, 695,
                200, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_SHORT_BG_IMG_PATH,
                "Continue"
        );

        recordsListView = new RecordsListView(myGdxGame.commonWhiteFont, 690);
        recordsTextView = new TextView(myGdxGame.largeWhiteFont, 206, 842, "Last records");
        homeButton2 = new ButtonView(
                280, 365,
                160, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_SHORT_BG_IMG_PATH,
                "Home"
        );

    }

    @Override
    public void show() {
        restartGame();
    }

    @Override
    public void render(float delta) {
        random = new Random();
        handleInput();
        if (gameSession.state == GameState.PLAYING) {
            String trashPath = GameResources.TRASH_IMG_PATH_L;
            if (gameSession.shouldSpawnTrash()) {

                int type = random.nextInt(3);
                switch (type) {
                    case 1:
                        trashPath = GameResources.TRASH_IMG_PATH_L;
                        break;
                    case 2:
                        trashPath = GameResources.TRASH_SECOND_IMG_PATH;
                        break;
                    default:
                        trashPath = GameResources.TRASH_IMG_PATH_R;

                }
                TrashObject trashObject = new TrashObject(
                        GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                        trashPath,
                        myGdxGame.world, type
                );
                trashArray.add(trashObject);
            }

           /* if (tractorObject.needToShoot()) {
                BulletObject laserBullet = new BulletObject(
                        tractorObject.getX(), tractorObject.getY() + tractorObject.height / 2,
                        GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT,
                        GameResources.BULLET_IMG_PATH,
                        myGdxGame.world
                );
                bulletArray.add(laserBullet);
                if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.shootSound.play();
            }*/

            if (!tractorObject.isAlive()) {
                gameSession.endGame();
                recordsListView.setRecords(MemoryManager.loadRecordsTable());
            }

            updateTrash();
            //  updateBullets();
            backgroundView.move();
            gameSession.updateScore();
            scoreTextView.setText("Score: " + gameSession.getScore());
            updateLevel(gameSession.getScore());
            //  System.out.println("" + gameSession.getScore2());
            liveView.setLeftLives(tractorObject.getLiveLeft());

            myGdxGame.stepWorld();
        }

        draw();
    }

    private void updateLevel(int score) {

        String pathTexture = GameResources.TRAC_IMG_PATH_START_GRAY;
        if (score > 100 && score < 500) {

            pathTexture = GameResources.TRAC_IMG_PATH_ORANGE;
        } else if (score >= 500 && score < 1000) {
            pathTexture = GameResources.TRAC_IMG_PATH_GREEN;
        } else if (score >= 1000 && score < 2000) {
            pathTexture = GameResources.TRAC_IMG_PATH_BLUE;
        } else if (score >= 2000 && score < 3000) {
            pathTexture = GameResources.TRAC_IMG_PATH_YELLOW;
        } else if (score >= 3000) {
            pathTexture = GameResources.TRAC_IMG_PATH_RED;
        }
        tractorObject.setTextureUrl(pathTexture);

    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            switch (gameSession.state) {
                case PLAYING:
                    if (pauseButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        gameSession.pauseGame();
                    }
                    tractorObject.move(myGdxGame.touch);
                    break;

                case PAUSED:
                    if (continueButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        gameSession.resumeGame();
                    }
                    if (homeButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }
                    break;

                case ENDED:

                    if (homeButton2.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }
                    break;
            }

        }
    }

    private void draw() {

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();
        backgroundView.draw(myGdxGame.batch);
        for (TrashObject trash : trashArray) trash.draw(myGdxGame.batch);
        tractorObject.draw(myGdxGame.batch);
        for (BulletObject bullet : bulletArray) bullet.draw(myGdxGame.batch);
        topBlackoutView.draw(myGdxGame.batch);
        scoreTextView.draw(myGdxGame.batch);
        liveView.draw(myGdxGame.batch);
        pauseButton.draw(myGdxGame.batch);

        if (gameSession.state == GameState.PAUSED) {
            fullBlackoutView.draw(myGdxGame.batch);
            pauseTextView.draw(myGdxGame.batch);
            homeButton.draw(myGdxGame.batch);
            continueButton.draw(myGdxGame.batch);
        } else if (gameSession.state == GameState.ENDED) {
            fullBlackoutView.draw(myGdxGame.batch);
            recordsTextView.draw(myGdxGame.batch);
            recordsListView.draw(myGdxGame.batch);
            homeButton2.draw(myGdxGame.batch);
        }

        myGdxGame.batch.end();

    }

    private void updateTrash() {
        for (int i = 0; i < trashArray.size(); i++) {

            boolean hasToBeDestroyed = !trashArray.get(i).isAlive(trashArray.get(i).getTrashType()) || !trashArray.get(i).isInFrame();

            if (!trashArray.get(i).isAlive(trashArray.get(i).getTrashType())) {
                gameSession.destructionRegistration();
                if (myGdxGame.audioManager.isSoundOn)
                    myGdxGame.audioManager.explosionSound.play(0.2f);
            }

            if (hasToBeDestroyed) {

                myGdxGame.world.destroyBody(trashArray.get(i).body);

                trashArray.remove(i--);
            }
        }
    }

/*    private void updateBullets() {
        for (int i = 0; i < bulletArray.size(); i++) {
            if (bulletArray.get(i).hasToBeDestroyed()) {
                myGdxGame.world.destroyBody(bulletArray.get(i).body);
                bulletArray.remove(i--);
            }
        }
    }*/

    private void restartGame() {

        for (int i = 0; i < trashArray.size(); i++) {
            myGdxGame.world.destroyBody(trashArray.get(i).body);
            trashArray.remove(i--);
        }

        if (tractorObject != null) {
            myGdxGame.world.destroyBody(tractorObject.body);
        }

        tractorObject = new TractorObject(
                GameSettings.SCREEN_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.TRAC_IMG_PATH_START_GRAY,
                myGdxGame.world
        );

        bulletArray.clear();
        gameSession.startGame();
    }

}
