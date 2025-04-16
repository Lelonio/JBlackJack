package org.jblackjack.model;

import java.util.*;
import java.util.stream.Collectors;


public class BlackJackModel extends CustomObservable {

    private ArrayList<Card> deck;
    private ArrayList<Card> dealerHand;

    private ArrayList<Player> players;
    private boolean gameOver;

    public BlackJackModel() {

        deck = new ArrayList<>();

        players = new ArrayList<>();
        dealerHand = new ArrayList<>();
        gameOver = false;

        initializeDeck();

    }

    public void initializeDeck() {
        String[] suits = {"H", "D", "C", "S"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        deck = Arrays.stream(ranks)
                .flatMap(rank -> Arrays.stream(suits).map(suit -> new Card(suit, rank)))
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(deck);
    }



    public boolean setAmount(Player player){

        return player.getAmount() < player.getCredits();

    }

    public void newGame(){

        dealerHand.clear();
        gameOver = false;
        Collections.shuffle(deck);

        for (Player player : players) {
            player.setFolded(false);
            player.setBusted(false);
            player.setStanding(false);
            player.getHand().clear();
            player.getHand().add(deck.remove(deck.size() - 1));
            player.getHand().add(deck.remove(deck.size() - 1));


        }

        dealerHand.add(deck.remove(deck.size() - 1));
        dealerHand.add(deck.remove(deck.size() - 1));
    }

    public void initializeGame(int numPlayers, int initialCredits, List<String> names) {
        // Clear players list before adding new players
        players.clear();
        dealerHand.clear();
        gameOver = false;
        Collections.shuffle(deck);
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(initialCredits,names.get(i)));

        }


        // Deal two cards to each player and dealer
        for (Player player : players) {
            player.getHand().add(deck.remove(deck.size() - 1));
            player.getHand().add(deck.remove(deck.size() - 1));

        }

        dealerHand.add(deck.remove(deck.size() - 1));
        dealerHand.add(deck.remove(deck.size() - 1));
    }

    public void playerHits(Player player) {
        if (getHandValue(player.getHand()) <= 21) {
            player.getHand().add(deck.remove(deck.size() - 1));
            setChanged();
            notifyObservers();
        } else if (getHandValue(player.getHand()) == 21) {
            dealerHit();
        }
    }

    // Player stands
    public void dealerHit() {
        if (!gameOver) {
            while (getHandValue(dealerHand) < 17) {
                dealerHand.add(deck.remove(deck.size() - 1));

            }

            gameOver = true;
        }
    }


    public int getHandValue(ArrayList<Card> hand) {
        int value = 0;
        int aceCount = 0;

        for (Card card : hand) {
            if (card.getValue().equals("A")) {
                aceCount++;
                value += 11;
            } else if (card.getValue().matches("[JQK]")) {
                value += 10;
            } else {
                value += Integer.parseInt(card.getValue());
            }
        }

        while (value > 21 && aceCount > 0) {
            value -= 10;
            aceCount--;
        }

        return value;
    }

    public ArrayList<Player> getPlayers() {

        return players;
    }
    public ArrayList<Card> getDealerHand() {
        return dealerHand;
    }

    public boolean isGameOver() {
        return gameOver;
    }


    public ArrayList<Integer> getAllPlayerSums() {
        return players.stream()
                .map(player -> getHandValue(player.getHand()))
                .collect(Collectors.toCollection(ArrayList::new));
    }


    public boolean playerHasBlackJack(Player player) {
        return player.getHand().size() == 2 && getHandValue(player.getHand()) == 21;
    }

    // Checks if the player is busted (hand value over 21)
    public boolean playerIsBusted(Player player) {
        if (getHandValue(player.getHand()) > 21){
            player.setBusted(true);
            player.stand();
            return true;
        }
        return false;
    }


    // Checks if the dealer is busted (hand value over 21)
    public boolean dealerIsBusted() {
        return getHandValue(dealerHand) > 21;
    }

    // Checks if the game is a tie (both player and dealer have the same hand value)
    public boolean isTie(Player player) {
        return getHandValue(player.getHand()) == getHandValue(dealerHand);
    }

    public boolean allPlayersFolded(){
        return players.stream().allMatch(Player::isFolded);
    }



    // Checks if the player wins (player has a higher hand value than the dealer, and neither is busted)
    public boolean playerWins(Player player) {
        int playerValue = getHandValue(player.getHand());
        int dealerValue = getHandValue(dealerHand);
        return !playerIsBusted(player) && !dealerIsBusted() && playerValue > dealerValue;
    }




    public void clearAmount() {
        players.forEach(Player::clearAmount);
    }

    public void isPlayerBroke() {
        players.removeIf(player -> player.getCredits() == 0);
    }

}
