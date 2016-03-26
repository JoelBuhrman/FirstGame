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
    private Integer scoore;
    Label countDownLabel, scoreLabel, timeLabel, levelLabel,worldLabel, marioLabel;



    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;
        scoore=0;

        viewport= new FitViewport(MarioBros.V_WIDTH, MarioBros.V_HEIGHT, new OrthographicCamera());
        stage= new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);


        scoreLabel = new Label(String.format("%06d", scoore), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        marioLabel = new Label(String.format("SCOORE"), new Label.LabelStyle(new BitmapFont(), Color.BLACK));

        table.add(marioLabel).expandX().pad(10);

        table.row();

        table.add(scoreLabel).expandX();


        stage.addActor(table);

    }

    public void setScoore(int scoore){
        this.scoore=scoore;
        scoreLabel.setText(String.valueOf(scoore));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
