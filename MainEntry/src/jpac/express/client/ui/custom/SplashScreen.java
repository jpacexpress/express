package jpac.express.client.ui.custom;

public class SplashScreen {
	
	private static SplashScreen instance = new SplashScreen();
	
	public static SplashScreen getInstance() {
		if (instance == null) {
			instance = new SplashScreen();
		}
		return instance;
	}
}
