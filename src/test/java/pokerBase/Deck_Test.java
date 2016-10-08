package pokerBase;

import static org.junit.Assert.*;

import pokerBase.Deck;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pokerEnums.eRank;
import pokerEnums.eSuit;

import java.util.ArrayList;

public class Deck_Test {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	
	public void DeckBuildTest()
	{
		Deck NewDeck = new Deck();
		assertTrue(NewDeck.getCardsLeft() == 52);
	}
	
	@Test
	
	public void TestWithJoker(){
		
	}
		Deck NewDeck = new Deck(2);{
		
		int count = 0;
		
		for (int index = 0; index <  NewDeck.getDeck().size(); index ++){
			if(NewDeck.getDeck().get(index).geteRank() == eRank.JOKER){
				count+=1;
			}
		}
			
		assertTrue(count == 2);
		}
}
