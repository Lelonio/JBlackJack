package org.jblackjack.view;

import org.jblackjack.controller.BlackJackController;
import org.jblackjack.model.BlackJackModel;
import org.jblackjack.model.Card;
import org.jblackjack.model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class BlackJackView extends JPanel implements Observer {
    private CardPanel cardPanel;
    JFrame frame = new JFrame("JBlackJack");

    int boardWidth = 1024;
    int boardHeight = 736;

    public JLabel gameOver;

    public JButton newGameButton;

    public JButton numPlayersButton;


    JPanel buttonPanel = new JPanel();

    private BlackJackController controller;


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof BlackJackModel) {
            this.updateHands(((BlackJackModel) o).getDealerHand(), ((BlackJackModel) o).getPlayers(),
                    ((BlackJackModel) o).getAllPlayerSums(), ((BlackJackModel) o).getHandValue(((BlackJackModel) o).getDealerHand()));
        }
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;
    
        public BackgroundPanel(Image backgroundImage) {
            this.backgroundImage = backgroundImage;
            this.setLayout(new BorderLayout()); // fondamentale per posizionare altri pannelli
        }
    
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
    

    public BlackJackView(){

        ImageIcon icon = new ImageIcon(getClass().getResource("/tableEmpty.png"));
        Image image = icon.getImage();
        BackgroundPanel backgroundPanel = new BackgroundPanel(image);
        frame.setContentPane(backgroundPanel);

        gameOver = new JLabel("GAME OVER!"+"\n"+"No credits left!", SwingConstants.CENTER);
        gameOver.setVisible(false);

        frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




        numPlayersButton = new JButton("Reset Game");
        newGameButton = new JButton("New Game");


        int gap = 50;
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, gap, 0));
        buttonPanel.add(newGameButton);
        buttonPanel.add(numPlayersButton);
        buttonPanel.setBackground(new Color(73,43,15));
        frame.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);

    }

    public void startCountdown(Timer timer){


        getCardPanel().setCountdown(timer);
    }

    public void updateHands(ArrayList<Card> dealerHand, ArrayList<Player> players, ArrayList<Integer> playerSums, int dealerSum) {
        if (cardPanel != null) {
            frame.remove(cardPanel);
        }
        cardPanel = new CardPanel(dealerHand, players, playerSums,dealerSum,this);

        frame.add(cardPanel, BorderLayout.CENTER);


        frame.revalidate();
        frame.repaint();

    }

    public void gameOver(){
        frame.remove(cardPanel);
        newGameButton.setEnabled(false);
        gameOver.setVisible(true);
        gameOver.setFont(new Font("Times New Roman", Font.BOLD, 50));
        gameOver.setForeground(Color.white);
        frame.add(gameOver,BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();

    }


    public void setController(BlackJackController controller) {
        this.controller=controller;

        this.newGameButton.addActionListener(e -> controller.newGame());
        this.numPlayersButton.addActionListener(e -> controller.changePlayers());
    }

    public BlackJackController getController(){
        return this.controller;
    }


    public CardPanel getCardPanel() {
        return cardPanel;
    }

    public JFrame getFrame() {
        return frame;
    }


}

