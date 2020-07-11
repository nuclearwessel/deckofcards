package com.github.nuclearwessel.deckofcards;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Deck {
    // ordered base deck of 52 cards
    private static final Card[] FULL_DECK = Stream.of(Suit.values())
            .flatMap(s -> Stream.of(CardValue.values()).map(cv -> new Card(cv, s))).toArray(Card[]::new);

    private Deque<Card> cards;

    public Deck() {
        // initialize with an unshuffled deck
        cards = new ArrayDeque<Card>(Arrays.asList(FULL_DECK));
    }

    // For testing purposes
    protected synchronized Card[] getCards() {
        return cards.toArray(new Card[cards.size()]);
    }

    public synchronized int getRemainingCardCount() {
        return cards.size();
    }

    /**
     * Shuffle returns no value, but results in the cards in the deck being randomly
     * permuted. Please do not use library-provided “shuffle” operations to
     * implement this function. You may use library provided random number
     * generators in your solution if needed.
     * 
     * sdb - Shuffles by pulling a random card from a deck and adding to a stack,
     * repeating until the starting deck is empty.
     */
    public void shuffle() {
        shuffle(new Random());
    }

    public synchronized void shuffle(Random rand) {
        cards.clear();

        final List<Card> remainingCards = new LinkedList<Card>(Arrays.asList(FULL_DECK)); // start with a full deck

        while (remainingCards.size() > 0) { // iterate until all cards have been shuffled in
            final int i = rand.nextInt(remainingCards.size());
            final Card v = remainingCards.remove(i);
            cards.push(v); // add cards to the stack in random order
        }
    }

    /**
     * This function should return one card from the deck to the caller.
     * Specifically, a call to shuffle followed by 52 calls to dealOneCard() should
     * result in the caller being provided all 52 cards of the deck in a random
     * order. If the caller then makes a 53rd call dealOneCard(), no card is dealt.
     */
    public synchronized Card dealOneCard() {
        if (cards.isEmpty()) {
            return null;
        }

        return cards.pop();
    }
}