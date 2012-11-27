package phase10.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextPane;

import java.awt.Font;

public class WelcomeFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					WelcomeFrame frame = new WelcomeFrame(new GuiManager(new GameManager()));
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public WelcomeFrame(final GuiManager gManage) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 491, 325);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		contentPane.setLayout(null);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				gManage.mainManager.newGame();
				gManage.displaySettingsFrame();
				dispose();
			}
		});
		btnNewGame.setBounds(28, 224, 120, 52);
		contentPane.add(btnNewGame);
		
		JButton btnLoadGame = new JButton("Load Game");
		btnLoadGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LoadFileFrame loader = new LoadFileFrame(gManage);
				loader.setVisible(true);
				dispose();
			}
		});
		btnLoadGame.setBounds(176, 224, 120, 52);
		contentPane.add(btnLoadGame);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(324, 224, 120, 52);
		contentPane.add(btnCancel);
		
		JTextPane txtpnWelcomeToPhase = new JTextPane();
		txtpnWelcomeToPhase.setEditable(false);
		txtpnWelcomeToPhase.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtpnWelcomeToPhase.setText("Welcome to Phase 10!");
		txtpnWelcomeToPhase.setBounds(95, 55, 285, 93);
		contentPane.add(txtpnWelcomeToPhase);
	}
}
