package phase10.gui;

import phase10.Player;

public class GuiManager {

	private SettingsFrame settingsWindow;
	//	public GameManager mainManager;

	
	public static void main(String[] args) {

	}

	//	public GuiManager(GameManager m) {
	//		mainManager = m; //passes a reference from the game manager into the GUI manager
	//	}



	public void displaySettingsFrame() {
		settingsWindow = new SettingsFrame();
		settingsWindow.setVisible(true);
	}

	public void initGame() {
		
	}


}