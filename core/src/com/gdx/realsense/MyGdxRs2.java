package com.gdx.realsense;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.gdx.realsense.engine.StreamTexture;
import com.intel.realsense.librealsense.RsContext;

public class MyGdxRs2 extends Game {

	private static String TAG = MyGdxRs2.class.getSimpleName();

	public static final int UI_WIDTH = 640;
	public static final int UI_HEIGHT = 480;

	private final RsManager rsManager;

	private Stage stage;
	private StreamTexture rsDepthTexture;
	private StreamTexture rsColorTexture;

	public MyGdxRs2(){
		rsManager = new RsManager();
	}

	public RsManager getRsManager(){ return rsManager;}

	public RsContext getRsContext(){ return rsManager.getRsContext();}

	@Override
	public void create () {
		Gdx.app.addLifecycleListener(rsManager);
		createGUI();
	}

	private void createGUI(){
		stage = new Stage(new ExtendViewport(UI_WIDTH, UI_HEIGHT, new OrthographicCamera(UI_WIDTH, UI_HEIGHT)));

		Table table = new Table();
		table.setFillParent(true);

		rsDepthTexture = new StreamTexture(RsManager.CAM_WIDTH, RsManager.CAM_HEIGHT);
		rsColorTexture = new StreamTexture(RsManager.CAM_WIDTH, RsManager.CAM_HEIGHT);
		Sprite depthSprite = new Sprite(rsDepthTexture);
		Sprite colorSprite = new Sprite(rsColorTexture);
		Image rsDepthImage = new Image(new SpriteDrawable(depthSprite));
		Image rsColorImage = new Image(new SpriteDrawable(colorSprite));
		rsDepthImage.setScaling(Scaling.fit);
		rsColorImage.setScaling(Scaling.fit);
		table.add(new Container<>(rsColorImage).fill());
		table.add(new Container<>(rsDepthImage).fill()).row();
		stage.addActor(table);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1, true);
		super.render();
		if(rsManager.isRunning()){
			rsDepthTexture.updateTexture(rsManager.getDepthFrameBytes());
			rsColorTexture.updateTexture(rsManager.getColorFrameBytes());
			stage.act();
			stage.draw();
		}
	}

	public void onExit(){
		rsManager.close();
	}
	
	@Override
	public void dispose () {
		stage.dispose();
		rsManager.dispose();
		rsDepthTexture.dispose();
	}
}
