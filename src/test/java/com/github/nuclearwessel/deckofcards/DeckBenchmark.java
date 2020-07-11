package com.github.nuclearwessel.deckofcards;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class DeckBenchmark {
    @Test
    public void runBenchmarks() throws Exception {
        Options options = new OptionsBuilder().include(this.getClass().getSimpleName()).forks(1).build();

        new Runner(options).run();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void shuffleDeck() {
        Deck sut = new Deck();
        sut.shuffle();
    }
}