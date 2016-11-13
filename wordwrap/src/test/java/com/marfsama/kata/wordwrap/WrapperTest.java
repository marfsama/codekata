package com.marfsama.kata.wordwrap;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** UnitTest for {@link Wrapper}. */
public class WrapperTest {

    @Test
    public void emptyStringShouldReturnSameString() {
        Wrapper wrapper = new Wrapper();
        assertEquals("", wrapper.wrap("", 5));
    }

    @Test
    public void shortStringShouldReturnSameString() {
        Wrapper wrapper = new Wrapper();
        assertEquals("doof", wrapper.wrap("doof", 5));
    }

    @Test
    public void longWordShouldBreakAtLimit() {
        Wrapper wrapper = new Wrapper();
        assertEquals("doofd\noof", wrapper.wrap("doofdoof", 5));
    }

    @Test
    public void veryLongWordShouldBreakSeveralTimesAtLimit() {
        Wrapper wrapper = new Wrapper();
        assertEquals("doofd\noofdo\nof", wrapper.wrap("doofdoofdoof", 5));
    }

    @Test
    public void wordsShouldWrapAtSpace() {
        Wrapper wrapper = new Wrapper();
        assertEquals("gut\ngut", wrapper.wrap("gut gut", 5));
    }

    @Test
    public void spaceAsLastCharInLineShouldWrapAndBeIgnored() {
        Wrapper wrapper = new Wrapper();
        assertEquals("doof\ndoof", wrapper.wrap("doof doof", 5));
    }

    @Test
    public void spaceAsFirstCharInNextLineShouldWrapAndBeIgnored() {
        Wrapper wrapper = new Wrapper();
        assertEquals("katze\nkatze", wrapper.wrap("katze katze", 5));
    }

    @Test
    public void multipleSpacesAsFirstCharInNextLineShouldWrapOnceAndIgnoreAllSpaces() {
        Wrapper wrapper = new Wrapper();
        assertEquals("katze\nkatze", wrapper.wrap("katze         katze", 5));
    }

    @Test
    public void multipleSpacesAsLastCharInLineShouldWrapOnceAndIgnoreAllSpaces() {
        Wrapper wrapper = new Wrapper();
        assertEquals("a\nkatze", wrapper.wrap("a    katze", 5));
    }