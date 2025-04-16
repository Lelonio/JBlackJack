package org.jblackjack.model;

import java.util.ArrayList;

public class Player {

    private String name;
    private ArrayList<Card> hand;
    private int amount;
    private int credits;

    private int wins;

    private int losses;
    private boolean isBusted;

    private boolean isStanding;

    public boolean isFolded() {
        return isFolded;
    }

    public void setFolded(boolean folded) {
        isFolded = folded;
    }

    private boolean isFolded;

    public Player(int credits, String name) {
        this.name=name.toUpperCase();
        this.hand = new ArrayList<>();
        this.credits = credits;
        this.isBusted = false;
        this.isStanding = false;
        this.wins = 0;
        this.losses = 0;
        this.amount = 0;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getCredits() {
        return credits;
    }

    public int getLosses() {
        return losses;
    }


    public void incrementWins(){
        this.wins ++;
    }

    public void incrementLosses(){
        this.losses ++;

    }

    public String getName() {
        return name;
    }


    public int getWins(){
        return wins;
    }

    public boolean isBusted() {
        return isBusted;
    }

    public void setBusted(boolean busted) {
        isBusted = busted;
    }

    public boolean isStanding() {
        return isStanding;
    }

    public void stand() {
        this.isStanding = true;
    }

    public void winBet() {
        this.credits += this.amount*2;
    }

    public void loseBet() {
        this.credits -= this.amount;
    }


    public void setStanding(boolean b) {
        this.isStanding=b;
    }

    public void setAmount() {
        this.amount+=10;
    }

    public void decreaseAmount(){this.amount-=10;}

    public void clearAmount(){
        this.amount=0;
    }

    public int getAmount(){
        return this.amount;
    }
}
