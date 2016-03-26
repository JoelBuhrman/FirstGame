package com.mygdx.mariobros.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.mariobros.MarioBros;


/**
 * Created by JoelBuhrman on 16-03-23.
 */
public class Hud implements Disposable{
    public Stage stage;
    private Viewport viewport;
    private Integer worldTimer;
    private float timeCount;
    private Integer score, highScore;
    Label countDownLabel, scoreLabel, timeLabel, levelLabel,worldLabel, marioLabel, highScoreLabel, highScoreTitleLabel;



    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;
        score=0;
        highScore=0;

        viewport= new FitViewport(MarioBros.V_WIDTH, MarioBros.V_HEIGHT, new OrthographicCamera());
        stage= new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);


        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        marioLabel = new Label(String.format("SCOORE"), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        highScoreLabel = new Label(String.format("%06d", highScore), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        highScoreTitleLabel = new Label(String.format("HIGHSCORE"), new Label.LabelStyle(new BitmapFont(), Color.BLACK));

        table.add(marioLabel).expandX().pad(10);
        table.add(highScoreTitleLabel).expandX().pad(10);
        table.row();

        table.add(scoreLabel).expandX();
        table.add(highScoreLabel).expandX();


        stage.addActor(table);

    }

    public void setScore(int score){
        this.score=score;
        scoreLabel.setText(String.valueOf(score));
    }

    public void setHighScore(int highScore){
        this.highScore= highScore;
        highScoreLabel.setText(String.valueOf(highScore));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
