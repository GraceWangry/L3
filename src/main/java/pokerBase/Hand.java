package pokerBase;

import java.lang.reflect.InvocationTargetException;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import org.hibernate.mapping.Set;

import pokerEnums.eCardNo;
import pokerEnums.eHandStrength;
import pokerEnums.eRank;
import pokerEnums.eSuit;
import pokerEnums.eHand;
import pokerExceptions.HandException;
import pokerExceptions.emptyDeckException;
import pokerExceptions.exHand;

//  Bryan Leach; Muyi Liu; Ruoyang Wang;


public class Hand implements Comparable{

	private ArrayList<Card> CardsInHand = new ArrayList<Card>();
	private boolean bScored;
	private HandScore hs;
	private int Cards;

	private ArrayList<Card> getCardsInHand() {
		return CardsInHand;
	}

	void AddToCardsInHand(Card c) {
		CardsInHand.add(c);
	}

	public boolean isbScored() {
		return bScored;
	}

	public HandScore getHs() {
		return hs;
	}

	public void EvaulateHand() {
		try {
			Hand h = EvaluateHand(this);
			h.hs = h.getHs();
			h.bScored = h.bScored;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * <b>EvaluateHand</b> is a static method that will score a given Hand of
	 * cards
	 * 
	 * @param h
	 * @return
	 * @throws HandException
	 */
	static Hand EvaluateHand(Hand h) throws Exception {

		Collections.sort(h.getCardsInHand());
		// for(Card c: h.getCardsInHand()){System.out.println(c.geteRank());}
		if (h.getCardsInHand().size() != 5) {
			throw new HandException(h, eHand.ShortHand);
		}

		ArrayList<Hand> ExplodedHands = new ArrayList<Hand>();
		ExplodedHands.add(h);

		ExplodedHands = ExplodeHands(ExplodedHands);

		for (Hand hEval : ExplodedHands) {

			HandScore hs = new HandScore();
			try {
				Class<?> c = Class.forName("pokerBase.Hand");

				for (eHandStrength hstr : eHandStrength.values()) {
					Class[] cArg = new Class[2];
					cArg[0] = pokerBase.Hand.class;
					cArg[1] = pokerBase.HandScore.class;

					Method meth = c.getMethod(hstr.getEvalMethod(), cArg);
					Object o = meth.invoke(null, new Object[] { hEval, hs });

					if ((Boolean) o) {
						break;
					}
				}

				hEval.bScored = true;
				hEval.hs = hs;

			} catch (ClassNotFoundException x) {
				x.printStackTrace();
			} catch (IllegalAccessException x) {
				x.printStackTrace();
			} catch (NoSuchMethodException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		/*
		 *Find the best hand in Exploded Hands
		 *h=bestHand
		 */
		Collections.sort(ExplodedHands);
		h=ExplodedHands.get(0);
		// TODO - Lab 3. ExplodedHands has a bunch of hands.
		// Either 1, 52, 2
		return h;
	}
    public static Comparator<Hand> HandRank = new Comparator<Hand>() {

        public int compare(Hand h1, Hand h2) {

            int result = 0;

            result = h2.getHs().getHandStrength() - h1.getHs().getHandStrength();

            if (result != 0) {
                return result;
            }

            result = h2.getHs().getHiHand() - h1.getHs().getHiHand();
            if (result != 0) {
                return result;
            }

            result = h2.getHs().getLoHand() - h1.getHs().getLoHand();
            if (result != 0) {
                return result;
            }

            if (h2.getHs().getKickers().size() > 0) {
                if (h1.getHs().getKickers().size() > 0) {
                    result = h2.getHs().getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr()
                            - h1.getHs().getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr();
                }
                if (result != 0) {
                    return result;
                }
            }

            if (h2.getHs().getKickers().size() > 1) {
                if (h1.getHs().getKickers().size() > 1) {
                    result = h2.getHs().getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr()
                            - h1.getHs().getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr();
                }
                if (result != 0) {
                    return result;
                }
            }

            if (h2.getHs().getKickers().size() > 2) {
                if (h1.getHs().getKickers().size() > 2) {
                    result = h2.getHs().getKickers().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr()
                            - h1.getHs().getKickers().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr();
                }
                if (result != 0) {
                    return result;
                }
            }

            if (h2.getHs().getKickers().size() > 3) {
                if (h1.getHs().getKickers().size() > 3) {
                    result = h2.getHs().getKickers().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr()
                            - h1.getHs().getKickers().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr();
                }
                if (result != 0) {
                    return result;
                }
            }
            return 0;
        }
    };
	protected eRank HandStrength() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param h
	 * @param hs
	 * @return
	 */

	private static ArrayList<Hand> ExplodeHands(ArrayList<Hand> Hands) {
		// TODO - Lab3 Implement this
		ArrayList<Hand> explodeHands = new ArrayList<Hand>();
		for (Hand firstHand : Hands) {
			Hand tempHand = new Hand();
			int i = 0;
			for (Card c : firstHand.CardsInHand) {
				if (c.geteRank() != eRank.JOKER && !c.isbWild()) {
					tempHand.AddToCardsInHand(c);
				} else {
					i++;
					tempHand.AddToCardsInHand(new Card(eSuit.JOKER, eRank.JOKER, 99));
				}
			}
			if (i == 0) {
				explodeHands.add(tempHand);
			} else {
				Collections.sort(tempHand.getCardsInHand());
				Deck d = new Deck();
				/*
				 * Create a new temporary hand (Do not call it tempHand) Set the
				 * cards in the new hand to be equal to the cards in tempHand 1
				 * Take a card form the deck, and add it to the new hand 2 Add
				 * the new hand to the explodeHands 3 Repeat for all cards in
				 * the deck
				 */
				for (int i1 = 0; i1 < 52; i1++) {
					Hand t = new Hand();
					for (Card c : tempHand.getCardsInHand()) {
						t.AddToCardsInHand(c);
					}
					Card c = null;
					try {
						c = d.Draw();
					} catch (emptyDeckException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					t.AddToCardsInHand(c);
					explodeHands.add(t);

				}
			}
		}

		return explodeHands;
	}

	private static ArrayList<Hand> explodeHelp(Hand h) {
		ArrayList<Hand> Hands = new ArrayList<Hand>();
		return Hands;
	}

	public static Hand PickBestHand(ArrayList<Hand> Hands) throws exHand {
		Hand winner = Hands.get(0);
		for (Hand h : Hands) {
			if (h.getHs().getHandStrength() > winner.getHs().getHandStrength()) {
				winner = h;
			} else if (h.getHs().getHandStrength() == winner.getHs().getHandStrength()) {
				throw new exHand();
			}
		}
		return winner;
	}

	public static boolean isHandRoyalFlush(Hand h, HandScore hs) {

		Card c = new Card();
		boolean isRoyalFlush = false;
		if ((isHandFlush(h.getCardsInHand())) && (isStraight(h.getCardsInHand(), c))) {
			if (c.geteRank() == eRank.ACE) {
				isRoyalFlush = true;
				hs.setHandStrength(eHandStrength.RoyalFlush.getHandStrength());
				hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
				hs.setLoHand(0);
			}
		}
		return isRoyalFlush;
	}

	/**
	 * isHandStraightFlush - Will return true if the hand is a straight flush
	 * 
	 * @param h
	 * @param hs
	 * @return
	 */
	public static boolean isHandStraightFlush(Hand h, HandScore hs) {
		Card c = new Card();
		boolean isRoyalFlush = false;
		if ((isHandFlush(h.getCardsInHand())) && (isStraight(h.getCardsInHand(), c))) {
			isRoyalFlush = true;
			hs.setHandStrength(eHandStrength.StraightFlush.getHandStrength());
			hs.setHiHand(c.geteRank().getiRankNbr());
			hs.setLoHand(0);
		}

		return isRoyalFlush;
	}

	/**
	 * isHandFourOfAKind - this method will determine if the hand is a four of a
	 * kind
	 * 
	 * @param h
	 * @param hs
	 * @return
	 */
	public static boolean isHandFourOfAKind(Hand h, HandScore hs) {

		boolean bHandCheck = false;

		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank()) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.FourOfAKind.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
			hs.setKickers(kickers);

		} else if (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.FourOfAKind.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			hs.setKickers(kickers);
		}

		return bHandCheck;
	}

	public static boolean isHandFiveOfAKind(Hand h, HandScore hs) {

		boolean isFiveOfAKind = false;

		if (((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank() == eRank.JOKER))) {
			isFiveOfAKind = true;
			hs.setHandStrength(eHandStrength.FourOfAKind.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
		}

		return isFiveOfAKind;
	}

	/**
	 * isHandFullHouse - This method will determine if the hand is a full house
	 * 
	 * @param h
	 * @param hs
	 * @return
	 */
	public static boolean isHandFullHouse(Hand h, HandScore hs) {

		boolean isFullHouse = false;
		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isFullHouse = true;
			hs.setHandStrength(eHandStrength.FullHouse.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr());
		} else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isFullHouse = true;
			hs.setHandStrength(eHandStrength.FullHouse.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
		}

		return isFullHouse;

	}

	public static boolean isHandFlush(Hand h, HandScore hs) {

		boolean bIsFlush = false;
		if (isHandFlush(h.getCardsInHand())) {
			hs.setHandStrength(eHandStrength.Flush.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
			hs.setKickers(kickers);
			bIsFlush = true;
		}

		return bIsFlush;
	}

	public static boolean isHandFlush(ArrayList<Card> cards) {
		int cnt = 0;
		boolean bIsFlush = false;
		for (eSuit Suit : eSuit.values()) {
			cnt = 0;
			for (Card c : cards) {
				if (c.geteSuit() == Suit) {
					cnt++;
				}
			}
			if (cnt == 5)
				bIsFlush = true;

		}
		return bIsFlush;
	}

	public static boolean isStraight(ArrayList<Card> cards, Card highCard) {
		boolean bIsStraight = false;
		boolean bAce = false;

		int iStartCard = 0;
		highCard.seteRank(cards.get(eCardNo.FirstCard.getCardNo()).geteRank());
		highCard.seteSuit(cards.get(eCardNo.FirstCard.getCardNo()).geteSuit());

		if (cards.get(eCardNo.FirstCard.getCardNo()).geteRank() == eRank.ACE) {
			// First card is an 'ace', handle aces
			bAce = true;
			iStartCard++;
		}

		for (int a = iStartCard; a < cards.size() - 1; a++) {
			if ((cards.get(a).geteRank().getiRankNbr() - cards.get(a + 1).geteRank().getiRankNbr()) == 1) {
				bIsStraight = true;
			} else {
				bIsStraight = false;
				break;
			}
		}

		if ((bAce) && (bIsStraight)) {
			if (cards.get(eCardNo.SecondCard.getCardNo()).geteRank() == eRank.KING) {
				highCard.seteRank(cards.get(eCardNo.FirstCard.getCardNo()).geteRank());
				highCard.seteSuit(cards.get(eCardNo.FirstCard.getCardNo()).geteSuit());
			} else if (cards.get(eCardNo.SecondCard.getCardNo()).geteRank() == eRank.FIVE) {
				highCard.seteRank(cards.get(eCardNo.SecondCard.getCardNo()).geteRank());
				highCard.seteSuit(cards.get(eCardNo.SecondCard.getCardNo()).geteSuit());
			} else {
				bIsStraight = false;
			}
		}
		return bIsStraight;
	}

	public static boolean isHandStraight(Hand h, HandScore hs) {

		boolean bIsStraight = false;
		Card highCard = new Card();
		if (isStraight(h.getCardsInHand(), highCard)) {
			hs.setHandStrength(eHandStrength.Straight.getHandStrength());
			hs.setHiHand(highCard.geteRank().getiRankNbr());
			hs.setLoHand(0);
			bIsStraight = true;
		}
		return bIsStraight;
	}

	public static boolean isHandThreeOfAKind(Hand h, HandScore hs) {

		boolean isThreeOfAKind = false;
		ArrayList<Card> kickers = new ArrayList<Card>();
		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank()) {
			isThreeOfAKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
		} else if (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank()) {
			isThreeOfAKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr());
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));

		} else if (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			isThreeOfAKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr());
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));

		}

		if (isThreeOfAKind) {
			hs.setHandStrength(eHandStrength.ThreeOfAKind.getHandStrength());
			hs.setLoHand(0);
			hs.setKickers(kickers);
		}

		return isThreeOfAKind;
	}

	public static boolean isHandTwoPair(Hand h, HandScore hs) {

		boolean isTwoPair = false;
		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FourthCard.getCardNo()).geteRank())) {
			isTwoPair = true;
			hs.setHandStrength(eHandStrength.TwoPair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr());
			kickers.add(h.getCardsInHand().get((eCardNo.FifthCard.getCardNo())));
			hs.setKickers(kickers);
		} else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isTwoPair = true;
			hs.setHandStrength(eHandStrength.TwoPair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr());
			kickers.add(h.getCardsInHand().get((eCardNo.ThirdCard.getCardNo())));
			hs.setKickers(kickers);
		} else if ((h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isTwoPair = true;
			hs.setHandStrength(eHandStrength.TwoPair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr());
			kickers.add(h.getCardsInHand().get((eCardNo.FirstCard.getCardNo())));
			hs.setKickers(kickers);
		}
		return isTwoPair;
	}

	public static boolean isHandPair(Hand h, HandScore hs) {
		boolean isPair = false;
		ArrayList<Card> kickers = new ArrayList<Card>();
		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank()) {
			isPair = true;
			hs.setHandStrength(eHandStrength.Pair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
			kickers.add(h.getCardsInHand().get((eCardNo.ThirdCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.FourthCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.FifthCard.getCardNo())));
			hs.setKickers(kickers);
		} else if (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank()) {
			isPair = true;
			hs.setHandStrength(eHandStrength.Pair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
			kickers.add(h.getCardsInHand().get((eCardNo.FirstCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.FourthCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.FifthCard.getCardNo())));
			hs.setKickers(kickers);
		} else if (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank()) {
			isPair = true;
			hs.setHandStrength(eHandStrength.Pair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
			kickers.add(h.getCardsInHand().get((eCardNo.FirstCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.SecondCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.FifthCard.getCardNo())));
			hs.setKickers(kickers);
		} else if (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			isPair = true;
			hs.setHandStrength(eHandStrength.Pair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
			kickers.add(h.getCardsInHand().get((eCardNo.FirstCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.SecondCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.ThirdCard.getCardNo())));
			hs.setKickers(kickers);
		}
		return isPair;
	}

	public static boolean isHandHighCard(Hand h, HandScore hs) {
		hs.setHandStrength(eHandStrength.HighCard.getHandStrength());
		hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
		hs.setLoHand(0);
		ArrayList<Card> kickers = new ArrayList<Card>();
		kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
		kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
		kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
		kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
		hs.setKickers(kickers);
		return true;
	}

	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Hand h = (Hand)o;
		return HandRank.compare(this, (Hand)o);
	}
}