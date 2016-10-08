package pokerBase;

import java.util.ArrayList;
import java.util.Collections;
import pokerExceptions.emptyDeckException;
import pokerEnums.eRank;
import pokerEnums.eSuit;

public class Deck {

	private ArrayList<Card> deckCards = new ArrayList<Card>();
	public int getCardsLeft(){
		return deckCards.size();
	}
	public ArrayList<Card> getDeck(){
		return deckCards;
	}
	public Deck() {
		int iCardNbr = 1;
		for (eSuit eSuit : eSuit.values()) {
			for (eRank eRank : eRank.values()) {
				if (eSuit != eSuit.JOKER)
				{
					deckCards.add(new Card(eSuit, eRank, iCardNbr++));
				}
			}
			 
		}
		Collections.shuffle(deckCards);
	}
	
	public Deck(int NbrOfJokers) {
		this();
		int size = deckCards.size();
		for (int i = 0; i < NbrOfJokers; i++){
			Card  = new Card(eSuit.JOKER, eRank.JOKER, size++);
			deckCards.add(joker);
		}
	}
	
	public Deck(int NbrOfJokers, ArrayList<Card> Wilds) {
		this(NbrOfJokers);
		for (Card wild: Wilds) {
			if (deckCards.contains(wild)) {
				int index = deckCards.indexOf(wild);
				deckCards.get(index).setbWild(true);
			}
		}
	}

	public Card Draw() throws emptyDeckException {
		if (deckCards.isEmpty()) {
			throw new emptyDeckException();
		}
		return deckCards.remove(0);
	}
}
