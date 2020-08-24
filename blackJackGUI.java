import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class blackJackGUI extends JFrame implements ActionListener {

  private deckOfCards deck;
  public Player player = new Player("player");
  public Player dealer = new Player("dealer");

  private JButton jbtnHit = new JButton("Hit");
  private JButton jbtnStay = new JButton("Stay");
  private JButton jbtnDeal = new JButton("Deal");

  private JLabel jlblStatus = new JLabel(" ", JLabel.CENTER);

  JPanel playerPanel = new JPanel();
  JPanel dealerPanel = new JPanel();
  JPanel buttonsPanel = new JPanel();
  JPanel statusPanel = new JPanel();

  blackJackGUI() {
    JFrame gameFrame = new JFrame("IT211 - BlackJack");
    gameFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("cards/b.gif"));
    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JOptionPane.showMessageDialog(gameFrame, "Author: Salee Savitz\nCoAuthor: Lisa Sandoval", "Black Jack Game", JOptionPane.INFORMATION_MESSAGE);
    JOptionPane.showMessageDialog(gameFrame, "The goal of blackjack is to beat the dealer's hand without going over 21.\n\n"
                                              + "Face cards are worth 10. Aces are worth 1 or 11, whichever makes a better hand.\n\n"
                                              + "Each player starts with two cards, one of the dealer's cards is hidden until the end.\n\n"
                                              + "To 'Hit' is to ask for another card. To 'Stay' is to hold your total and end your turn.\n\n"
                                              + "If you go over 21 you bust, and the dealer wins regardless of the dealer's hand.\n\n"
                                              + "If you are dealt 21 from the start (Ace & 10), you got a blackjack.\n\n"
                                              + "Dealer will hit until his/her cards total 17 or higher.\n\n"
                                              , "Instructions", JOptionPane.INFORMATION_MESSAGE);
    buttonsPanel.add(jbtnHit);
    buttonsPanel.add(jbtnStay);
    buttonsPanel.add(jbtnDeal);
    statusPanel.add(jlblStatus);

    jbtnHit.addActionListener(this);
    jbtnStay.addActionListener(this);
    jbtnDeal.addActionListener(this);

    jbtnHit.setEnabled(false);
    jbtnStay.setEnabled(false);

    dealerPanel.setBackground(Color.GRAY);
    playerPanel.setBackground(Color.GRAY);
    buttonsPanel.setBackground(Color.GRAY);
    statusPanel.setBackground(Color.GRAY);

    gameFrame.setLayout(new BorderLayout());
    gameFrame.add(dealerPanel, BorderLayout.NORTH);
    gameFrame.add(playerPanel, BorderLayout.CENTER);
    gameFrame.add(buttonsPanel, BorderLayout.SOUTH);
    gameFrame.add(statusPanel, BorderLayout.WEST);
    gameFrame.repaint();
    gameFrame.setSize(450, 350);
    gameFrame.setVisible(true);
  }

  private void hitPlayer() {
    card newCard = player.dealTo(deck.deal());
    playerPanel.add(new JLabel(new ImageIcon(newCard.getCardImage())));
    playerPanel.updateUI();
  }

  private void hitDealerDown() {
    card newCard = dealer.dealTo(deck.deal());
    dealerPanel.add(new JLabel(new ImageIcon("cards/b.gif")));
    dealerPanel.updateUI();
  }

  private void hitDealer() {
    card newCard = dealer.dealTo(deck.deal());
    dealerPanel.add(new JLabel(new ImageIcon(newCard.getCardImage())));
    dealerPanel.updateUI();
  }

  private void deal() throws IOException {
        playerPanel.removeAll();
        dealerPanel.removeAll();
        playerPanel.updateUI();
        dealerPanel.updateUI();
        player.reset();
        dealer.reset();
        if (deck == null || deck.size() < 15) {
            deck = new deckOfCards();
            deck.shuffle();
            jlblStatus.setText("Shuffling");
        }
        hitPlayer();
        hitDealerDown();
        hitPlayer();
        hitDealer();
    }

    private void checkWinner() {
        dealerPanel.removeAll();
        for (int i = 0; i < dealer.inHand(); i++) {
            dealerPanel.add(new JLabel(new ImageIcon(dealer.cards[i].getCardImage())));
        }
        if (player.value() > 21) {
            jlblStatus.setText("Player Busts");
        } else if (dealer.value() > 21) {
            jlblStatus.setText("Dealer Busts");
        } else if (dealer.value() == player.value()) {
            jlblStatus.setText("Push");
        } else if (dealer.value() < player.value()) {
            jlblStatus.setText("Player Wins");
        } else {
            jlblStatus.setText("Dealer Wins");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbtnHit) {
            hitPlayer();
            if (player.value() > 21) {
                checkWinner();
                jbtnHit.setEnabled(false);
                jbtnStay.setEnabled(false);
                jbtnDeal.setEnabled(true);
            }
        }

        if (e.getSource() == jbtnStay) {
            while (dealer.value() < 17 || player.value() > dealer.value()) {
                hitDealer();
            }
            checkWinner();
            jbtnHit.setEnabled(false);
            jbtnStay.setEnabled(false);
            jbtnDeal.setEnabled(true);
        }

        if (e.getSource() == jbtnDeal) {
            try {
            deal();
          } catch (IOException e1) {
            e1.printStackTrace();
          }
            jlblStatus.setText(" ");
            jbtnHit.setEnabled(true);
            jbtnStay.setEnabled(true);
            jbtnDeal.setEnabled(false);
        }
    }
}
