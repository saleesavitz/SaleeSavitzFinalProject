import java.io.IOException;
import java.util.Scanner;

public class cardGame {
	public static deckOfCards deck;

	public static void main(String[] args) throws IOException {
		deck = new deckOfCards();
		deck.shuffle();
		gameSelect();
	}
	public static void gameSelect() throws IOException {
		System.out.println("Select game with corresponding number:");
		System.out.println("1. Card Guessing Game");
		System.out.println("2. Black Jack");
		Scanner input = new Scanner(System.in);

		if(input.nextInt() == 1)
			new cardGUI();
		else
			new blackJackGUI();
		input.close();
	}
}

