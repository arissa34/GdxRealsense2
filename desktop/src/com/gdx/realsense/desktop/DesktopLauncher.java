package com.gdx.realsense.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gdx.realsense.MyGdxRs2;
import com.intel.realsense.librealsense.DeviceWatcherDesktop;
import com.intel.realsense.librealsense.RsContext;

public class DesktopLauncher {

	public static void main (String[] arg) {
		MyGdxRs2 myGdxRs2;
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1200;
		config.height = 800;
		config.forceExit = false;
		new LwjglApplication(myGdxRs2 = new MyGdxRs2(), config){
			//	To avoid error on exit I use :
			//		config.forceExit = false;
			//	+ listener on exit with :
			//		System.exit(0);
			@Override
			public void exit()
			{
					((MyGdxRs2)getApplicationListener()).onExit();
					super.exit();
					System.exit(0);
			}
		};
		RsContext.init(new DeviceWatcherDesktop());
		myGdxRs2.getRsContext().setDevicesChangedCallback(myGdxRs2.getRsManager());
	}
}
