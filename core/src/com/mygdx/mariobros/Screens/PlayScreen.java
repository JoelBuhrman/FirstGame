package com.mygdx.mariobros.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.mariobros.MarioBros;
import com.mygdx.mariobros.Scenes.Hud;
import com.mygdx.mariobros.Sprites.Mario;
import com.mygdx.mariobros.Tools.B2WorldCreator;
import com.mygdx.mariobros.Tools.WorldContactListener;

/**
 * Created by JoelBuhrman on 16-03-23.
 */
public class PlayScreen implements Screen {
    private TextureAtlas atlas;
    private MarioBros game;
    private Mario player;
    private Hud hud;
    private int currentScoore = 0;
    private int highestScoore = 0;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private float heightBeforeJump, currentHeight, heightBeforeFall;
    private boolean GAMEOVER = false;


    private World world;
    private Box2DDebugRenderer b2dr;


    public PlayScreen(MarioBros game) {
        atlas = new TextureAtlas("Mario_and_Enemies.pack");
        this.game = game;

        hud = new Hud(game.batch);
        gamecam = new OrthographicCamera();

        gamePort = new FitViewport(MarioBros.V_WIDTH / MarioBros.PPM, MarioBros.V_HEIGHT / MarioBros.PPM, gamecam);
        mapLoader = new TmxMapLoader();

        //Laddar in kartan för vår bana
        map = mapLoader.load("level2.tmx");

        renderer = new OrthogonalTiledMapRenderer(map, 1 / MarioBros.PPM);


        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world, map);

        player = new Mario(world, this);
        //startposition för kameran
        gamecam.position.set(player.getX() + gamePort.getWorldHeight() / 2, player.getY(), 0);
        hud.setScoore(currentScoore);
        world.setContactListener(new WorldContactListener());

    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    public void update(float dt) {
        handleInput(dt);

        currentScoore= (int) Math.round((currentHeight - (float)0.22499996)*(1/0.79999974));
        if(currentScoore>highestScoore){
            highestScoore=currentScoore;
        }

        world.step(1 / 60f, 6, 2);
        player.update(dt);


        currentHeight = player.getCurrentHeight();




        //Så att kameran endast följer med om man inte trillar
        if (!(currentHeight < heightBeforeJump) && !(currentHeight < gamecam.position.y)) {

            gamecam.position.y = player.b2body.getPosition().y;


        }

        if (currentHeight < (heightBeforeJump - gamePort.getWorldHeight() / 6)) {

            System.out.println("GAME OVER");
            GAMEOVER = true;
        }
        // System.out.println("p: " + player.previousState + " c: " + player.currentState);
        hud.setScoore(highestScoore);
        gamecam.update();
        renderer.setView(gamecam);

    }

    public void handleInput(float dt) {
/*
        //För dator
        if (!GAMEOVER) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.getState() != Mario.State.JUMPING && player.getState() != Mario.State.FALLING) {
                heightBeforeJump = player.getCurrentHeight();
                player.b2body.applyLinearImpulse(new Vector2(0, 4.5f), player.b2body.getWorldCenter(), true);


            }


            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
                //heightBeforeJump = player.getCurrentHeight();
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
                // heightBeforeJump = player.getCurrentHeight();
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
            }

        }*/


//För android:

        if (!GAMEOVER) {
            if (Gdx.input.getAccelerometerX() > 2) {
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);

            }
            if (Gdx.input.getAccelerometerX() < -2) {

                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);

            }
            if (Gdx.input.isTouched() && player.getState() != Mario.State.JUMPING && player.getState() != Mario.State.FALLING) {

                heightBeforeJump = player.getCurrentHeight();
                player.b2body.applyLinearImpulse(new Vector2(0, 4.5f), player.b2body.getWorldCenter(), true);
            }
        }

    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();


        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);

    }

    public World getWorld() {
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
