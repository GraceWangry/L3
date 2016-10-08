package pokerBase;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.Collections;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pokerEnums.eHandStrength;
import pokerEnums.eRank;
import pokerEnums.eSuit;
import pokerExceptions.exHand;
import pokerExceptions.HandException;


public class Hand_Test {

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
	public void TestTwoJoker(){
		Hand h = new Hand();
	    h.AddToCardsInHand(new Card(eSuit.JOKER, eRank.JOKER,1));
	    h.AddToCardsInHand(new Card(eSuit.JOKER, eRank.JOKER,1));
	    h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.QUEEN,1));
	    h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.KING,1));
	    h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.ACE,1));
	    try {
			h = Hand.EvaluateHand(h);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    assertTrue(h.getHs().getHandStrength() == eHandStrength.RoyalFlush.getHandStrength());
		assertTrue(h.getHs().getHiHand() == eRank.ACE.getiRankNbr());
	    
	}
	
	@Test
	public void TestRoyalFlush() {
		Hand h = new Hand();
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.TEN,1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.JACK,1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.QUEEN,1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.KING,1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.ACE,1));
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		assertTrue(h.getHs().getHandStrength() == eHandStrength.RoyalFlush.getHandStrength());
		assertTrue(h.getHs().getHiHand() == eRank.ACE.getiRankNbr());
		
	}
	
	@Test
	public void TestRoyalFlush2() {
		Hand h = new Hand();
		h.AddToCardsInHand(new Card(eSuit.SPADES, eRank.TEN,1));
		h.AddToCardsInHand(new Card(eSuit.SPADES, eRank.ACE,1));
		h.AddToCardsInHand(new Card(eSuit.SPADES, eRank.QUEEN,1));
		h.AddToCardsInHand(new Card(eSuit.SPADES, eRank.JACK,1));
		h.AddToCardsInHand(new Card(eSuit.SPADES, eRank.KING,1));
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		assertTrue(h.getHs().getHandStrength() == eHandStrength.RoyalFlush.getHandStrength());
		assertTrue(h.getHs().getHiHand() == eRank.ACE.getiRankNbr());
		
	}
	
	@Test
	public void TestStraightFlush() {
		Hand h = new Hand();
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.TEN,1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.JACK,1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.QUEEN,1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.KING,1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.NINE,1));
		
		try {
			h.EvaulateHand();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(h.getHs().getHandStrength());
	}
	
	@Test
	public void TestStraightFlush2() {
		Hand h = new Hand();
		h.AddToCardsInHand(new Card(eSuit.HEARTS, eRank.TEN,1));
		h.AddToCardsInHand(new Card(eSuit.HEARTS, eRank.SIX,1));
		h.AddToCardsInHand(new Card(eSuit.JOKER, eRank.JOKER,1));
		h.AddToCardsInHand(new Card(eSuit.HEARTS, eRank.EIGHT,1));
		h.AddToCardsInHand(new Card(eSuit.HEARTS, eRank.NINE,1));
		
		try {
			h.EvaulateHand();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(h.getHs().getHandStrength());
		
		assertTrue(h.getHs().getHandStrength() == eHandStrength.StraightFlush.getHandStrength());
		assertTrue(h.getHs().getHiHand() == eRank.TEN.getiRankNbr());
	}
	
	
	@Test
	public void TestFourOfAKind() {
		Hand h = new Hand();
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.TEN,1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.TEN,1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.TEN,1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.TEN,1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.ACE,1));
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		assertTrue(h.getHs().getHandStrength() == eHandStrength.FourOfAKind.getHandStrength());
		assertTrue(h.getHs().getHiHand() == eRank.TEN.getiRankNbr());
		
	}
	
	@Test
	public void TestFourOfAKind2() {
		Hand h = new Hand();
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.TEN,1));
		h.AddToCardsInHand(new Card(eSuit.HEARTS, eRank.TEN,1));
		h.AddToCardsInHand(new Card(eSuit.SPADES, eRank.TEN,1));
		h.AddToCardsInHand(new Card(eSuit.JOKER, eRank.JOKER,1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.ACE,1));
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		assertTrue(h.getHs().getHandStrength() == eHandStrength.FourOfAKind.getHandStrength());
		assertTrue(h.getHs().getHiHand() == eRank.TEN.getiRankNbr());
		
	}
	
	
	@Test
	public void TestFullHouse(){
		Hand h = new Hand();
		
		h.AddToCardsInHand(new Card(eSuit.SPADES, eRank.TEN, 1));
		h.AddToCardsInHand(new Card(eSuit.SPADES, eRank.KING, 1));
		h.AddToCardsInHand(new Card(eSuit.DIAMONDS, eRank.TEN, 1));
		h.AddToCardsInHand(new Card(eSuit.HEARTS, eRank.KING, 1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.KING, 1));
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		assertTrue(h.getHs().getHandStrength() == eHandStrength.FullHouse.getHandStrength());
		assertTrue(h.getHs().getHiHand() == eRank.KING.getiRankNbr());

	}
	
	@Test
	public void TestFullHouse2(){
		Hand h = new Hand();
		
		h.AddToCardsInHand(new Card(eSuit.SPADES, eRank.TEN, 1));
		h.AddToCardsInHand(new Card(eSuit.DIAMONDS, eRank.TEN, 1));
		h.AddToCardsInHand(new Card(eSuit.JOKER, eRank.JOKER, 1));
		h.AddToCardsInHand(new Card(eSuit.HEARTS, eRank.KING, 1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.KING, 1));

		try {
			h = Hand.EvaluateHand(h);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		assertTrue(h.getHs().getHandStrength() == eHandStrength.FullHouse.getHandStrength());
		assertTrue(h.getHs().getHiHand() == eRank.KING.getiRankNbr());

		}
	
	@Test
	public void TestFlush(){
		Hand h = new Hand();

		h.AddToCardsInHand(new Card(eSuit.SPADES, eRank.TEN, 1));
		h.AddToCardsInHand(new Card(eSuit.SPADES, eRank.KING, 1));
		h.AddToCardsInHand(new Card(eSuit.SPADES, eRank.TWO, 1));
		h.AddToCardsInHand(new Card(eSuit.SPADES, eRank.FOUR, 1));
		h.AddToCardsInHand(new Card(eSuit.SPADES, eRank.SIX, 1));

		try {
			h = Hand.EvaluateHand(h);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		assertTrue(h.getHs().getHandStrength() == eHandStrength.Flush.getHandStrength());
		assertTrue(h.getHs().getHiHand() == eRank.KING.getiRankNbr());
		
		}
	
	@Test
	public void TestFlush2(){
		Hand h = new Hand();
		
		h.AddToCardsInHand(new Card(eSuit.HEARTS, eRank.TEN, 1));
		h.AddToCardsInHand(new Card(eSuit.HEARTS, eRank.KING, 1));
		h.AddToCardsInHand(new Card(eSuit.JOKER, eRank.JOKER, 1));
		h.AddToCardsInHand(new Card(eSuit.HEARTS, eRank.FOUR, 1));
		h.AddToCardsInHand(new Card(eSuit.HEARTS, eRank.SEVEN, 1));

		try {
			h = Hand.EvaluateHand(h);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		assertTrue(h.getHs().getHandStrength() == eHandStrength.FullHouse.getHandStrength());
		assertTrue(h.getHs().getHiHand() == eRank.KING.getiRankNbr());
		
		}
	
	@Test
	public void TestStraight(){
		Hand h = new Hand();
		
		h.AddToCardsInHand(new Card(eSuit.HEARTS, eRank.TEN, 1));
		h.AddToCardsInHand(new Card(eSuit.SPADES, eRank.NINE, 1));
		h.AddToCardsInHand(new Card(eSuit.JOKER, eRank.JOKER, 1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.SIX, 1));
		h.AddToCardsInHand(new Card(eSuit.HEARTS, eRank.SEVEN, 1));
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		assertFalse(h.getHs().getHandStrength() == eHandStrength.Straight.getHandStrength());
		assertTrue(h.getHs().getHiHand() == eRank.TEN.getiRankNbr());
		
	}
	
	@Test
	public void TestStraight2(){
		Hand h = new Hand();
		
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.TEN, 1));
		h.AddToCardsInHand(new Card(eSuit.SPADES, eRank.JACK, 1));
		h.AddToCardsInHand(new Card(eSuit.HEARTS, eRank.EIGHT, 1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.NINE, 1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.SEVEN, 1));
		
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		assertTrue(h.getHs().getHandStrength() == eHandStrength.Straight.getHandStrength());
		assertTrue(h.getHs().getHiHand() == eRank.JACK.getiRankNbr());
		
	}
	
	@Test
	public void TestThreeOfAKind(){
		Hand h = new Hand();
		
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.FIVE, 1));
		h.AddToCardsInHand(new Card(eSuit.DIAMONDS, eRank.TEN, 1));
		h.AddToCardsInHand(new Card(eSuit.HEARTS, eRank.TEN, 1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.FOUR, 1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.TEN, 1));
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		assertTrue(h.getHs().getHandStrength() == eHandStrength.ThreeOfAKind.getHandStrength());
		assertTrue(h.getHs().getHiHand() == eRank.TEN.getiRankNbr());
		
		
	}
	
	@Test
	public void TestThreeOfAKind2(){
		Hand h = new Hand();
		
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.TEN, 1));
		h.AddToCardsInHand(new Card(eSuit.DIAMONDS, eRank.TEN, 1));
		h.AddToCardsInHand(new Card(eSuit.JOKER, eRank.JOKER, 1));
		h.AddToCardsInHand(new Card(eSuit.SPADES, eRank.EIGHT, 1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.SEVEN, 1));
		
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		assertTrue(h.getHs().getHandStrength() == eHandStrength.ThreeOfAKind.getHandStrength());
		assertTrue(h.getHs().getHiHand() == eRank.TEN.getiRankNbr());
		
	}
	
	@Test
	public void TestTwoPair(){
		Hand h = new Hand();
		
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.TEN, 1));
		h.AddToCardsInHand(new Card(eSuit.DIAMONDS, eRank.TEN, 1));
		h.AddToCardsInHand(new Card(eSuit.HEARTS, eRank.SIX, 1));
		h.AddToCardsInHand(new Card(eSuit.SPADES, eRank.SIX, 1));
		h.AddToCardsInHand(new Card(eSuit.CLUBS, eRank.EIGHT, 1));
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		assertTrue(h.getHs().getHandStrength() == eHandStrength.TwoPair.getHandStrength());
		assertTrue(h.getHs().getHiHand() == eRank.TEN.getiRankNbr());
		
		
	}
	

	
}

