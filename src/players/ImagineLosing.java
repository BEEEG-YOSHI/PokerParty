package players;
import game.Card;
import game.HandRanks;
import game.Player;

import java.util.*;

public class ImagineLosing extends Player{

    public ImagineLosing(String name) {
        super(name);
    }

    @Override
    protected void takePlayerTurn() {
        if(shouldAllIn()){
            allIn();
        } else if(shouldRaise()){
            if(getGameState().getTableBet() < (int)(getBank() * 0.1)) {
                raise((int) (getBank() * 0.1));
            } else {
                call();
            }
        } else if(shouldCall()){
            call();
        } else if(shouldCheck()){
            check();
        } else if(shouldFold()){
            fold();
        }
    }

    public int returnRank(){
        HandRanks myHand = evaluatePlayerHand();

        switch(myHand) {
            case HIGH_CARD:
                return 0;
            case PAIR:
                if(uniquePair()){
                    return 1;
                }
                return 0;
            case TWO_PAIR:
                return 2;
            case THREE_OF_A_KIND:
                if(uniquePair()){
                    return 3;
                }
                return 2;
            case STRAIGHT:
                return 4;
            case FLUSH:
                return 5;
            case FULL_HOUSE:
                return 6;
            case FOUR_OF_A_KIND:
                return 7;
            case STRAIGHT_FLUSH:
                return 8;
            case ROYAL_FLUSH:
                return 9;
        }
        return 0;
    }

    public boolean uniquePair(){
        List<Card> hand = getHandCards();
        int hand1 = hand.get(0).getValue();
        int hand2 = hand.get(1).getValue();
        for(Card card: getGameState().getTableCards()){
            if(hand1 == card.getValue() || hand2 == card.getValue()){
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean shouldFold() {
        if(returnRank() == 0 && isBetActive()) {
            return true;
        } if(returnRank() < 3 && getGameState().getTableBet() > ((int)(getBank() * 0.6))){
            return true;
        }
        return false;
    }

    @Override
    protected boolean shouldCheck() {
        if(getGameState().isActiveBet()){
            return false;
        }
        return true;
    }

    @Override
    protected boolean shouldCall() {
        if(isBetActive()){

            if(returnRank() > 3 && getBet() <= (int)(0.25*getBank())){
                return true;
            } else if (returnRank() > 4){
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean shouldRaise() {
        if(returnRank() > 2){
            return true;
        }
        if(returnRank() > 1 && !isBetActive()){
            return true;
        }
        return false;
    }

    @Override
    protected boolean shouldAllIn() {
        if(returnRank() > 6){
            return true;
        }
        return false;
    }


    /*if( getHandCards().get(0).getValue() == getHandCards().get(1).getValue()) {
        //in my hand
    }
                else {
        //its on the table
    }*/


}
