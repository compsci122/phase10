package phase10.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;

public class LoadFileFrame extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField filenameField;
	private String filename;
	private GuiManager gManage;
	private Language gameLang;

	public String getFilename() {
		return filename;
	}

	/**
	 * Create the dialog.
	 */
	public LoadFileFrame(GuiManager guiM) {
		gameLang = guiM.getGameLang();
		
		setTitle(gameLang.getEntry("LOAD_GAME"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(SaveFileFrame.class.getResource("/images/GameIcon.png")));

		gManage = guiM;

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			filenameField = new JTextField();
			filenameField.setBounds(142, 204, 287, 20);
			contentPanel.add(filenameField);
			filenameField.setColumns(10);
		}
		{
			JTextPane promptField = new JTextPane();
			promptField.setBounds(5, 204, 122, 20);
			promptField.setEditable(false);
			promptField.setText(gameLang.getEntry("ENTER_A_FILENAME"));
			contentPanel.add(promptField);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton(gameLang.getEntry("OKAY"));
				okButton.addActionListener(new OKListener());
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton(gameLang.getEntry("CANCEL"));
				cancelButton.addActionListener(new CancelListener());
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private class OKListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			filename = filenameField.getText();
			boolean flag = gManage.mainManager.loadGame(filename);

			if(flag == false) 
			{
				dispose();
				LoadFileFrame tryAgain = new LoadFileFrame(gManage);
				tryAgain.setVisible(true);
				MessageFrame invalidFile = new MessageFrame(gameLang.getEntry("INVALID_FILE_MESSAGE"), gameLang.getEntry("INVALID_FILENAME"), gameLang);
				invalidFile.setVisible(true);
			}
			else {
				gManage.initGameWindow();
				dispose();
			}
		
			
		}
		
	}
	
	private class CancelListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			gManage.initGui();
			dispose();
		}
		
	}
}