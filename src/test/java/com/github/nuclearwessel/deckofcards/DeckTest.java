package com.github.nuclearwessel.deckofcards;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.Test;

public class DeckTest {
    @Test
    public void shouldShuffleCards() {
        final Deck sut = new Deck();

        final Card[] before = sut.getCards();
        assertEquals(52, before.length);

        sut.shuffle(new Random(1979)); // unlikely though it is, not risking it shuffling into the same order

        final Card[] after = sut.getCards();

        assertEquals(52, after.length);
        assertNotEquals(Arrays.asList(before), Arrays.asList(after));
    }

    @Test
    public void shouldDealOneCard() {
        final Deck sut = new Deck();
        sut.shuffle();

        assertEquals(52, sut.getRemainingCardCount());

        final Card card = sut.dealOneCard();
        assertNotNull(card);

        assertEquals(51, sut.getRemainingCardCount());
    }

    @Test
    public void shouldDealCardsUntilEmpty() {
        final Deck sut = new Deck();
        sut.shuffle();

        assertEquals(52, sut.getRemainingCardCount());

        for (int i = 51; i >= 0; i--) {
            final Card card = sut.dealOneCard();

            assertNotNull(card);
            assertEquals(i, sut.getRemainingCardCount());

            System.out.println(card.toString()); // visual inspection to ensure random order
        }

        assertEquals(0, sut.getRemainingCardCount());
    }

    @Test
    public void shouldDealNullOnOverdraw() {
        final Deck sut = new Deck();
        sut.shuffle();

        assertEquals(52, sut.getRemainingCardCount());

        IntStream.range(0, 51).parallel().forEach(i -> sut.dealOneCard()); // drain the deck to the last card

        assertEquals(1, sut.getRemainingCardCount()); // verify we haven't already overdrawn

        final Card card = sut.dealOneCard(); // deal the last card

        assertNotNull(card);

        assertEquals(0, sut.getRemainingCardCount()); // deck should be empty

        final Card nullCard = sut.dealOneCard();

        assertNull(nullCard);
    }

    @Test
    public void shouldResetDeckOnShuffle() {
        final Deck sut = new Deck();

        assertEquals(52, sut.getRemainingCardCount());

        sut.dealOneCard();

        assertEquals(51, sut.getRemainingCardCount());

        sut.shuffle();

        assertEquals(52, sut.getRemainingCardCount());
    }

    @Test
    public void shouldBeThreadSafe() {
        final Deck sut = new Deck();

        // FWIW, chance of this passing even without the synchronization depending on
        // hardware
        IntStream.range(0, 100).parallel().forEach(f -> {
            sut.shuffle();

            // without synchronization, the card count is all over the place here
            assertEquals(52, sut.getRemainingCardCount());
        });
    }
}