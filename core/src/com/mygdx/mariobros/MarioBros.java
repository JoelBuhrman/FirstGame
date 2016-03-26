package com.mygdx.mariobros;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.mariobros.Screens.PlayScreen;

public class MarioBros extends Game {
	public SpriteBatch batch;
	public Stage stage;
	private PlayScreen playScreen;
	private int highScore;
//	private FileHandle fh = Gdx.files.local("myfile.txt");
	//public static final int V_WIDTH = 400; Orginalvärdena
	//public static final int V_HEIGHT= 208;
	public static final int V_WIDTH = 200;
	public static final int V_HEIGHT = 208;
	public static final float PPM= 100;

	
	@Override
	public void create () {

		stage= new Stage();
		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();
		playScreen= new PlayScreen(this);
		playScreen.setHighScore(highScore);
		setScreen(playScreen);

	}

	@Override
	public void render () {
		super.render();
		if(playScreen.isGameOver()){
			if(playScreen.getScore()>highScore){
				highScore= playScreen.getScore();
			//	fh.writeString(String.valueOf(highScore), true);

			}
			create();
		}
	}
}
