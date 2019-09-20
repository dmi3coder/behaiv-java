package de.dmi3y.behaiv.tools;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PairTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getKey() {
        final Pair<String, String> stringStringPair = new Pair<>("KeyTest", "ValueTest");
        assertEquals("KeyTest", stringStringPair.getKey());
    }

    @Test
    public void getValue() {
        final Pair<String, String> stringStringPair = new Pair<>("KeyTest", "ValueTest");
        assertEquals("ValueTest", stringStringPair.getValue());
    }

    @Test
    public void hashCode1() {
        final Pair<String, String> stringStringPair = new Pair<>("KeyTest", "ValueTest");
        final int hash = stringStringPair.hashCode();
        final int expectedHash = 37 * "KeyTest".hashCode() + "ValueTest".hashCode() ^ "ValueTest".hashCode() >>> 16;
        assertEquals(expectedHash, hash);
    }

    @Test
    public void toString1() {
        final Pair<String, String> stringStringPair = new Pair<>("KeyTest", "ValueTest");
        assertEquals("[KeyTest, ValueTest]", stringStringPair.toString());
    }

    @Test
    public void create() {
        final Pair<String, String> pair = Pair.create("KeyTest", "ValueTest");
        assertEquals("KeyTest", pair.getKey());
        assertEquals("ValueTest", pair.getValue());
    }
}