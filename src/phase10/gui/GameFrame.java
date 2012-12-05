package phase10.gui;


import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import phase10.*;
import phase10.card.Card;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;


public class GameFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	Player current;
	ArrayList<Card> selectedCards = new ArrayList<Card>();
	private boolean isDiscarding = false;

	//begin components
	private JPanel infoPanel = new JPanel(); //Panel that displays on the right side of GameFrame. Displays basic info about the current player
	private JPanel deckPanel = new JPanel(); //includes the deck and discard buttons. Displays on bottom-right corner
	private JPanel handPanel = new JPanel(); //displays all the cards in a player's hand
	private JPanel playersPanel = new JPanel(); //displays all of the current player's opponents
	private JPanel yourPhasesPanel = new JPanel(); //displays the current player's phases, or a button to add a phase if the current player has not yet laid down a phase in a round
	
	private JButton[] handButtons = new JButton[11]; //an array of buttons representing each card in the current player's hand
	private JLabel lblPlayername; //Displays the current player's name in infoPanel
	private JTextArea phaseNumber; //Displays the phase of the current player in infoPanel
	private JButton deckButton; //JButton that represents the top of the deck
	private JButton discardButton; //JButton that represents the top of the discard stack
	private JButton btnNewPhase; //a button in yourPhasesPanel that allows a player to lay down a phase
	private ArrayList<opponentPanel> oppPanels; //an arrayList of all the opponent panels
	private GuiManager gManage; //Reference to the GuiManager object
	private JButton pg1Start; //the first card of the current player's first phase group
	private JLabel lblTo; //a label that says to
	private JButton pg1End; //the last card of the current player's first phase group
	private JButton addToPG1; //a button that allows the user to add a card to their own phase group
	private JButton addToPG2; //a button that allows the user to add a card to their own phase group
	private JLabel lblTo2; //a label that says to
	private JButton pg2End; //the first card of the current player's second phase group
	private JButton pg2Start; //the last card of the current player's second phase group
	private JTextArea playerScore; //displays the current player's score in infoPanel
	private JLabel lblTurnMode;
	//end components

	/**
	 * Creates the GameFrame at the constructor
	 */
	public GameFrame(final GuiManager gManage) {

		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameFrame.class.getResource("/images/GameIcon.png")));

		this.gManage = gManage;

		current = gManage.mainManager.getGame().getCurrentPlayer();

		Phase10 currentGame = gManage.mainManager.getGame(); //added for simplicity of access


		setTitle(current.getName() + " - Phase 10");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1150, 668);
		getContentPane().setLayout(null);

		infoPanel.setBounds(982, 0, 169, 534);
		getContentPane().add(infoPanel);
		infoPanel.setLayout(null);

		lblPlayername = new JLabel(current.getName());
		lblPlayername.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPlayername.setBounds(10, 37, 149, 38);
		infoPanel.add(lblPlayername);

		JLabel lblPhase = new JLabel("Phase");
		lblPhase.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPhase.setBounds(58, 91, 53, 25);
		infoPanel.add(lblPhase);


		phaseNumber = new JTextArea();
		phaseNumber.setRows(1);
		phaseNumber.setColumns(1);
		phaseNumber.setFont(new Font("Century Gothic", Font.BOLD, 36));

		try {
			phaseNumber.setText(Integer.toString(currentGame.getCurrentPlayer().getPhase()));
		} catch (NullPointerException e) {
			phaseNumber.setText("null");
		}
		phaseNumber.setEditable(false);
		phaseNumber.setBounds(58, 120, 53, 53);
		infoPanel.add(phaseNumber);

		JButton btnPhaseDescription = new JButton("Phase Descriptions");
		btnPhaseDescription.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				gManage.displayPhaseDescriptionFrame();
			}
		});
		btnPhaseDescription.setBounds(10, 184, 149, 23);
		infoPanel.add(btnPhaseDescription);

		JButton btnScoreboard = new JButton("Scoreboard");
		btnScoreboard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				gManage.displayScoreFrame();
			}
		});
		btnScoreboard.setBounds(28, 300, 118, 23);
		infoPanel.add(btnScoreboard);

		JButton btnSave = new JButton("Save");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SaveFileFrame saverWindow = new SaveFileFrame(gManage);
				saverWindow.setVisible(true);

			}
		});
		btnSave.setBounds(22, 347, 124, 23);
		infoPanel.add(btnSave);

		JButton btnExit = new JButton("Exit");
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnExit.setBounds(5, 392, 154, 25);
		infoPanel.add(btnExit);
		
		JLabel lblScore = new JLabel("Score");
		lblScore.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblScore.setBounds(58, 218, 53, 25);
		infoPanel.add(lblScore);
		
		playerScore = new JTextArea();
		playerScore.setText(Integer.toString(current.getScore()));
		playerScore.setRows(1);
		playerScore.setFont(new Font("Century Gothic", Font.BOLD, 36));
		playerScore.setEditable(false);
		playerScore.setColumns(1);
		playerScore.setBounds(47, 240, 69, 49);
		infoPanel.add(playerScore);
		
		lblTurnMode = new JLabel("You may draw");
		lblTurnMode.setBounds(28, 509, 118, 14);
		infoPanel.add(lblTurnMode);

		handPanel.setBounds(0, 533, 976, 107);
		getContentPane().add(handPanel);
		handPanel.setLayout(new GridLayout(0, 11, 0, 0));


		for(int i = 0; i < handButtons.length; i++) {
			handButtons[i] = new JButton();

			//add an action listener
			handButtons[i].addActionListener(new HandActionListener(i));
			handPanel.add(handButtons[i]);
		}


		//begin deck panel

		deckPanel.setBounds(982, 533, 169, 107);
		getContentPane().add(deckPanel);
		deckPanel.setLayout(new GridLayout(0, 2, 0, 0));

		deckButton = new JButton("");
		deckButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		deckButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(isDiscarding == false) {
					gManage.mainManager.getGame().getRound().drawFromDeck();
					updateFrame(gManage.mainManager.getGame());
					deckButton.setEnabled(false);
					discardButton.setEnabled(false);
					btnNewPhase.setEnabled(true);
					discardButton.setIcon(null);
					discardButton.setText("discard selected card");
					isDiscarding = true;
				}

			}
		});
		deckButton.setIcon(new ImageIcon(GameFrame.class.getResource("/images/cardImages/card back.png")));
		deckPanel.add(deckButton);

		discardButton = new JButton("");
		discardButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if(isDiscarding == false) {
					boolean isValidCard = gManage.mainManager.getGame().getRound().drawFromDiscard();
					if(isValidCard) {
						updateFrame(gManage.mainManager.getGame());
						deckButton.setEnabled(false);
						discardButton.setEnabled(false);
						btnNewPhase.setEnabled(true);
						discardButton.setIcon(null);
						discardButton.setText("discard selected card");
						isDiscarding = true;
					}
					else {
						MessageFrame skipPickup = new MessageFrame("You cannot pick up a Skip card from the discard pile", "Invalid Move");
						skipPickup.setVisible(true);
					}
				}
				else {
					if(selectedCards.size() == 0) {
						MessageFrame noCard = new MessageFrame("You must select a card to discard", "Invalid move");
						noCard.setVisible(true);
					}
					else {
						gManage.mainManager.getGame().getRound().discard(selectedCards.get(0));
					}
					updateFrame(gManage.mainManager.getGame());
					deckButton.setEnabled(true);
					discardButton.setEnabled(true);	
					isDiscarding = false;

					btnNewPhase.setVisible(true);
					btnNewPhase.setText("Add a Phase!");
				}
			}
		});
		deckPanel.add(discardButton);

		//end deck panel

		playersPanel.setBounds(10, 11, 962, 416);
		getContentPane().add(playersPanel);
		playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.X_AXIS));

		oppPanels = new ArrayList<opponentPanel>();
		try {
			for(int x = 0; x < currentGame.getNumberOfPlayers() - 1; x++) {
				oppPanels.add(new opponentPanel(currentGame.getPlayer(x), gManage.mainManager.getGame(), this));
				playersPanel.add(oppPanels.get(x));
			}
		} catch (NullPointerException e) {
			System.out.println("null pointer exception generated when trying to display opponent Panels");
			playersPanel.add(new JLabel("Null"));
		}

		yourPhasesPanel.setBounds(0, 427, 972, 107);
		getContentPane().add(yourPhasesPanel);
		yourPhasesPanel.setLayout(null);

		btnNewPhase = new JButton("Add a Phase!");
		btnNewPhase.addActionListener(new PhaseActionListener());
		btnNewPhase.setEnabled(false);
		btnNewPhase.setBounds(413, 42, 146, 23);
		yourPhasesPanel.add(btnNewPhase);

		pg1Start = new JButton("");
		pg1Start.setVisible(false);
		pg1Start.setPreferredSize(new Dimension(100, 23));
		pg1Start.setMaximumSize(new Dimension(114, 40));
		pg1Start.setMargin(new Insets(0, 0, 0, 0));
		pg1Start.setHorizontalTextPosition(SwingConstants.CENTER);
		pg1Start.setBounds(10, 0, 88, 107);
		yourPhasesPanel.add(pg1Start);

		pg1End = new JButton("");
		pg1End.setVisible(false);
		pg1End.setPreferredSize(new Dimension(100, 23));
		pg1End.setMaximumSize(new Dimension(114, 40));
		pg1End.setMargin(new Insets(0, 0, 0, 0));
		pg1End.setHorizontalTextPosition(SwingConstants.CENTER);
		pg1End.setBounds(179, 0, 88, 107);
		yourPhasesPanel.add(pg1End);

		pg2Start = new JButton("");
		pg2Start.setVisible(false);
		pg2Start.setPreferredSize(new Dimension(100, 23));
		pg2Start.setMaximumSize(new Dimension(114, 40));
		pg2Start.setMargin(new Insets(0, 0, 0, 0));
		pg2Start.setHorizontalTextPosition(SwingConstants.CENTER);
		pg2Start.setBounds(707, 0, 88, 107);
		yourPhasesPanel.add(pg2Start);

		pg2End = new JButton("");
		pg2End.setVisible(false);
		pg2End.setPreferredSize(new Dimension(100, 23));
		pg2End.setMaximumSize(new Dimension(114, 40));
		pg2End.setMargin(new Insets(0, 0, 0, 0));
		pg2End.setHorizontalTextPosition(SwingConstants.CENTER);
		pg2End.setBounds(884, 0, 88, 107);
		yourPhasesPanel.add(pg2End);

		addToPG1 = new JButton("add to phase");
		addToPG1.addActionListener(new AddToPhaseListener(0));
		addToPG1.setVisible(false);
		addToPG1.setBounds(277, 27, 117, 52);
		yourPhasesPanel.add(addToPG1);

		addToPG2 = new JButton("add to phase");
		addToPG2.addActionListener(new AddToPhaseListener(1));
		addToPG2.setVisible(false);
		addToPG2.setBounds(580, 27, 117, 52);
		yourPhasesPanel.add(addToPG2);

		lblTo = new JLabel("to");
		lblTo.setVisible(false);
		lblTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTo.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTo.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblTo.setBounds(108, 25, 61, 53);
		yourPhasesPanel.add(lblTo);

		lblTo2 = new JLabel("to");
		lblTo2.setVisible(false);
		lblTo2.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTo2.setHorizontalAlignment(SwingConstants.CENTER);
		lblTo2.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblTo2.setBounds(805, 27, 61, 53);
		yourPhasesPanel.add(lblTo2);

		updateCardImages();
	}

	protected void updateYourPhasesPanel() {
		if(current.hasLaidDownPhase()) {
			if(current.getNumberOfPhaseGroups() == 1) { //first phase group is visible
				pg1Start.setVisible(true);
				lblTo.setVisible(true);
				pg1End.setVisible(true);
				addToPG1.setVisible(true);
				
				lblTo.setToolTipText(current.getPhaseGroup(0).toString());

				pg2Start.setVisible(false);
				lblTo2.setVisible(false);
				pg2End.setVisible(false);
				addToPG2.setVisible(false);

				btnNewPhase.setVisible(false);

				pg1Start.setIcon(new ImageIcon(GameFrame.class.getResource(
						getCardFile(current.getPhaseGroup(0).getCard(0)))));

				pg1End.setIcon(new ImageIcon(GameFrame.class.getResource(
						getCardFile(current.getPhaseGroup(0).getCard(current.getPhaseGroup(0).getNumberOfCards() - 1)))));
			}
			else { //first and second phase groups are visible
				pg1Start.setVisible(true);
				lblTo.setVisible(true);
				pg1End.setVisible(true);
				addToPG1.setVisible(true);
				
				lblTo.setToolTipText(current.getPhaseGroup(0).toString());

				pg2Start.setVisible(true);
				lblTo2.setVisible(true);
				pg2End.setVisible(true);
				addToPG2.setVisible(true);
				
				lblTo2.setToolTipText(current.getPhaseGroup(1).toString());

				btnNewPhase.setVisible(false);

				pg1Start.setIcon(new ImageIcon(GameFrame.class.getResource(
						getCardFile(current.getPhaseGroup(0).getCard(0)))));

				pg1End.setIcon(new ImageIcon(GameFrame.class.getResource(
						getCardFile(current.getPhaseGroup(0).getCard(current.getPhaseGroup(0).getNumberOfCards() - 1)))));

				pg2Start.setIcon(new ImageIcon(GameFrame.class.getResource(
						getCardFile(current.getPhaseGroup(1).getCard(0)))));

				pg2End.setIcon(new ImageIcon(GameFrame.class.getResource(
						getCardFile(current.getPhaseGroup(1).getCard(current.getPhaseGroup(1).getNumberOfCards() - 1)))));
			}
		}
		else { //no phase groups are visible
			pg1Start.setVisible(false);
			lblTo.setVisible(false);
			pg1End.setVisible(false);
			addToPG1.setVisible(false);

			pg2Start.setVisible(false);
			lblTo2.setVisible(false);
			pg2End.setVisible(false);
			addToPG2.setVisible(false);

			btnNewPhase.setVisible(true);
		}
	}

	protected void hideAndClearSelectedCards() {
		for(int i = 0; i < handButtons.length; i++) {
			handButtons[i].setVisible(true);
			handButtons[i].setSelected(false);
		}
		selectedCards.clear();
	}

	/**
	 * Will read information from a Card object and return the path to the correct
	 * image for that Card.
	 * 
	 * @param aCard the card object
	 * @return the filename to the image file of the specified card
	 */
	String getCardFile(Card aCard) {

		String filename = "/images/cardImages/";

		if(aCard.getColor().equals(Color.RED))
			filename += "Red";
		else if(aCard.getColor().equals(Color.BLUE))
			filename += "Blue";
		else if(aCard.getColor().equals(Color.YELLOW))
			filename += "Yellow";
		else if(aCard.getColor().equals(Color.GREEN))
			filename += "Green";

		if(aCard.getValue() == 13)
			filename += "Wild";
		else if(aCard.getValue() == 14)
			filename += "Skip";
		else
			filename += aCard.getValue();

		filename += ".png";

		return filename;
	}

	private String getSelectedCardFile(Card aCard) {
		String filename = getCardFile(aCard);
		filename = filename.substring(0, filename.length() - 4);
		filename += "Selected.png";

		return filename;
	}


	public void updateFrame(Phase10 currentGame) {

		current = currentGame.getCurrentPlayer();

		//begin update of opponent panels
		int y = 0;
		for(int x = 0; x < currentGame.getNumberOfPlayers(); x++) {
			if(currentGame.getPlayer(x) != currentGame.getCurrentPlayer()) {
				oppPanels.get(y).updatePanel(currentGame.getPlayer(x));
				y++;
			}
		}

		//end update of opponent panels

		updateYourPhasesPanel();

		/*
		 * begin update of cards
		 */

		for(int i = 0; i < handButtons.length; i++)
			handButtons[i].setSelected(false);

		selectedCards.clear();

		updateCardImages();

		if(currentGame.getRound().getTopOfDiscardStack() == null) {
			discardButton.setIcon(new ImageIcon(GameFrame.class.getResource("/images/cardImages/NoCardsLeft.png")));

		}
		else {
			discardButton.setIcon(new ImageIcon(GameFrame.class.getResource(
					getCardFile(currentGame.getRound().getTopOfDiscardStack())
					)));
			discardButton.setEnabled(true);
		}

		deckButton.setEnabled(true);

		/*
		 * end update of cards
		 */


		//begin update of infoPanel

		lblPlayername.setText(current.getName());
		phaseNumber.setText(Integer.toString(current.getPhase()));
		playerScore.setText(Integer.toString(current.getScore()));
		
		if(current.getHasDrawnCard())
			lblTurnMode.setText("You may discard");
		else {
			lblTurnMode.setText("you may draw");
			btnNewPhase.setVisible(false);
		}

		//end update of infoPanel

	}


	/**
	 * Updates each of the card's selected and unselected images to the current configuration
	 * of the current player's hand
	 */
	private void updateCardImages() {

		Hand currentHand = current.getHand();
		
		//begin hand button update
		int i = 0;
		for(; i < currentHand.getNumberOfCards(); i++) {

			handButtons[i].setVisible(true);

			handButtons[i].setIcon(new ImageIcon(GameFrame.class.getResource(
					getCardFile(currentHand.getCard(i))
					)));

			handButtons[i].setSelectedIcon(new ImageIcon(GameFrame.class.getResource(
					getSelectedCardFile(currentHand.getCard(i))
					)));
		}

		for(; i < handButtons.length; i++) {
			handButtons[i].setVisible(false);
		}
		//end hand button update

		if(gManage.mainManager.getGame().getRound().getTopOfDiscardStack() == null){
			discardButton.setIcon(new ImageIcon(GameFrame.class.getResource("/images/cardImages/NoCardsLeft.png")));
		}
		else {	
			discardButton.setIcon(new ImageIcon(GameFrame.class.getResource(
					getCardFile(gManage.mainManager.getGame().getRound().getTopOfDiscardStack())
					)));
		}
	}

	private class HandActionListener implements ActionListener {

		int i;

		public HandActionListener(int index) {
			i = index;
		}

		public void actionPerformed(ActionEvent arg0) {
			if(handButtons[i].isSelected()) {
				selectedCards.remove(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(i));
				handButtons[i].setSelected(false);
			}
			else {
				selectedCards.add(gManage.mainManager.getGame().getCurrentPlayer().getHand().getCard(i));
				handButtons[i].setSelected(true);
			}
			if(selectedCards.size() == 1) {
				discardButton.setEnabled(true);
			}
			else
				discardButton.setEnabled(false);
		}
	}

	private class PhaseActionListener implements ActionListener {

		boolean isPhasing;
		boolean isSecondPhaseGroup;
		private PhaseGroup newPhaseGroup;
		private PhaseGroup newPhaseGroup2;

		public PhaseActionListener() {
			isPhasing = false;
			isSecondPhaseGroup = false;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			if(isPhasing) {
				if(isSecondPhaseGroup == false) {

					newPhaseGroup = new PhaseGroup(gManage.mainManager.getGame());
					
					for(Card c : selectedCards) {
						newPhaseGroup.addCard(c);
					}

					for(int i = 0; i < handButtons.length; i++) {
						if(handButtons[i].isSelected())
							handButtons[i].setVisible(false);
					}

					selectedCards.clear();

					switch(current.getPhase()) {
					case 1:
						btnNewPhase.setText("add a set of 3");
						isSecondPhaseGroup = true;
						break;
					case 2:
						btnNewPhase.setText("add a run of 4");
						isSecondPhaseGroup = true;
						break;
					case 3:
						btnNewPhase.setText("add a run of 4");
						isSecondPhaseGroup = true;
						break;
					case 7:
						btnNewPhase.setText("add a set of 4");
						isSecondPhaseGroup = true;
						break;
					case 9:
						btnNewPhase.setText("add a set of 2");
						isSecondPhaseGroup = true;
						break;
					case 10:
						btnNewPhase.setText("add a set of 3");
						isSecondPhaseGroup = true;
						break;
					default:
						btnNewPhase.setText("Add a Phase!");
						btnNewPhase.setVisible(false);
						isSecondPhaseGroup = false;
						boolean isValid = current.addPhaseGroups(newPhaseGroup);
						if(!isValid) {
							MessageFrame notAGoodPhase = new MessageFrame("The phase you are trying to add is not valid for your phase", "Invalid move");
							notAGoodPhase.setVisible(true);

							for(int i = 0; i < handButtons.length; i++) {
								handButtons[i].setVisible(true);
							}

							selectedCards.clear();

							isPhasing = false;
							isSecondPhaseGroup = false;
							
							btnNewPhase.setText("Add a Phase!");
						}
						else { //the player played a valid phase with only one phase group in the phase
							pg1Start.setVisible(true);
							lblTo.setVisible(true);
							pg1End.setVisible(true);
							addToPG1.setVisible(true);

							pg1Start.setIcon((new ImageIcon(GameFrame.class.getResource(
									getCardFile(newPhaseGroup.getCard(0)))
									)));
							pg1End.setIcon((new ImageIcon(GameFrame.class.getResource(
									getCardFile(newPhaseGroup.getCard(newPhaseGroup.getNumberOfCards() - 1)))
									)));

							selectedCards.clear();

							btnNewPhase.setVisible(false);
							btnNewPhase.setText("Add a Phase!");

							isPhasing = false;
							isSecondPhaseGroup = false;
							break;
						}
					}
				}
				else { //is adding the second phase group

					int secondGroupStartIndex = newPhaseGroup.getNumberOfCards();

					newPhaseGroup2 = new PhaseGroup(gManage.mainManager.getGame());
					//adding phases to the phaseGroup
					for(Card c : selectedCards)
						newPhaseGroup2.addCard(c);

					boolean isValid = current.addPhaseGroups(newPhaseGroup, newPhaseGroup2);
					System.out.println("Phase Group Type: " + newPhaseGroup.getType());
					if(!isValid) {
						MessageFrame notAGoodPhase = new MessageFrame("The phase you are trying to add is not valid for your phase", "Invalid move");
						notAGoodPhase.setVisible(true);

						//set all buttons to visible and clear all selections
						hideAndClearSelectedCards();
						isPhasing = false;
						isSecondPhaseGroup = false;
						btnNewPhase.setText("Add a Phase!");
					}
					else { //the player played a valid phase
						pg1Start.setVisible(true);
						lblTo.setVisible(true);
						pg1End.setVisible(true);
						addToPG1.setVisible(true);

						pg1Start.setIcon((new ImageIcon(GameFrame.class.getResource(
								getCardFile(newPhaseGroup.getCard(0)))
								)));
						pg1End.setIcon((new ImageIcon(GameFrame.class.getResource(
								getCardFile(newPhaseGroup.getCard(secondGroupStartIndex - 1)))
								)));


						pg2Start.setVisible(true);
						lblTo2.setVisible(true);
						pg2End.setVisible(true);
						addToPG2.setVisible(true);

						pg2Start.setIcon((new ImageIcon(GameFrame.class.getResource(
								getCardFile(newPhaseGroup2.getCard(0)))
								)));
						pg2End.setIcon((new ImageIcon(GameFrame.class.getResource(
								getCardFile(newPhaseGroup2.getCard(newPhaseGroup2.getNumberOfCards()-1)))
								)));

						updateFrame(gManage.mainManager.getGame());
						selectedCards.clear();

						btnNewPhase.setVisible(false);
						btnNewPhase.setText("Add a Phase!");

						isPhasing = false;
						isSecondPhaseGroup = false;
					}
				}
			}
			else{ //is not phasing
				isPhasing = true;
				isSecondPhaseGroup = false;
				switch(current.getPhase()) {
				case 1:
					btnNewPhase.setText("add a set of 3");
					break;
				case 2:
					btnNewPhase.setText("add a set of 3");
					break;
				case 3:
					btnNewPhase.setText("add a set of 4");
					break;
				case 4:
					btnNewPhase.setText("add a run of 7");
					break;
				case 5:
					btnNewPhase.setText("add a run of 8");
					break;
				case 6:
					btnNewPhase.setText("add a run of 9");
					break;
				case 7:
					btnNewPhase.setText("add a set of 4");
					break;
				case 8:
					btnNewPhase.setText("add 7 cards of the same color");
					break;
				case 9:
					btnNewPhase.setText("add a set of 5");
					break;
				case 10:
					btnNewPhase.setText("add a set of 5");
					break;

				default:
					System.out.println("Error! phase number out of bounds in GameFrame");
					break;
				}
			}
		}
	}

	private class AddToPhaseListener implements ActionListener {

		private int phaseGroupIndex;

		public AddToPhaseListener(int phaseGroup) {
			this.phaseGroupIndex = phaseGroup;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for(int x = 0; x < selectedCards.size(); x++) {
				boolean isValid = current.getPhaseGroup(phaseGroupIndex).addCard(selectedCards.get(x));
				if(!isValid) {
					MessageFrame invalidAdd = new MessageFrame("A card you are trying to add is invlaid", "Invalid move");
					invalidAdd.setVisible(true);
					break;
				}
				else {
					hideAndClearSelectedCards();
					updateFrame(gManage.mainManager.getGame());
					updateYourPhasesPanel();
				}
			}

		}

	}
}


