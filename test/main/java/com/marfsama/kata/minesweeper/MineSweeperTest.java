package com.marfsama.kata.minesweeper;

import org.junit.Test;

import static org.junit.Assert.*;

/** UnitTest für {@link Minesweeper}.  */
public class MineSweeperTest {

    @Test(expected = Exception.class)
    public void nullInputShouldFail() {
        new Minesweeper().cheat(null);
    }

    @Test
    public void resolveShouldCreateCorrectlySizedArray() {
        char[][] result = new Minesweeper().resolveToArray("*...\n....\n.*..\n....");

        assertEquals("height", 4, result.length);
        assertEquals("width", 4, result[0].length);
    }

    @Test
    public void resolveShouldFillArray() {
        char[][] result = new Minesweeper().resolveToArray("*...\n....\n.*..\n....");

        assertEquals("Element 0,0", result[0][0], '*');
        assertEquals("Element 3,3", result[3][3], '.');
    }

    @Test
    public void findBombsResultShouldHaveSameSizeAsInput() {
        char[][] testdata = getTestdata();

        int[][] result = new Minesweeper().findBombs(testdata);

        assertEquals("height", 4, result.length);
        assertEquals("width", 4, result[0].length);
    }

    @Test
    public void findBombsShouldCopyBombs() {
        int[][] result = new Minesweeper().findBombs(getTestdata());

        assertEquals("bomb at 0,0", -1, result[0][0]);
        assertEquals("bomb at 2,1", -1, result[2][1]);
    }

    @Test
    public void findBombsShouldCountBombsHorizontallyAndVertically() {
        int[][] result = new Minesweeper().findBombs(getCircleTestdata());
        assertEquals("above", 1, result[3][1]);
        assertEquals("below", 1, result[1][1]);
        assertEquals("left", 1, result[2][2]);
        assertEquals("right", 1, result[2][0]);
    }

    @Test
    public void findBombsShouldCountBombsDiagonally() {
        int[][] result = new Minesweeper().findBombs(getCircleTestdata());
        assertEquals("above left", 1, result[3][2]);
        assertEquals("above right", 1, result[3][0]);
        assertEquals("below left", 1, result[1][2]);
        assertEquals("below right", 1, result[1][0]);
    }

    @Test
    public void findBombsShouldCountManyBombs() {
        int[][] result = new Minesweeper().findBombs(getTestdata());
        assertEquals("2 bombs field 1", 2, result[1][0]);
        assertEquals("2 bombs field 2", 2, result[1][1]);
    }

    @Test
    public void resultToString() {
        int[][] testdata = {
                {-1, 1, 0, 1},
                {0, 1, -1, 1},
                {0, 2, 0, 1},
                {3, 3, 3, -1},
        }
                ;
        String result = new Minesweeper().resultToString(testdata);
        assertEquals("2 bombs field 1", "*101\n01*1\n0201\n333*", result);
    }

    @Test
    public void cheat1() {
        String result = new Minesweeper().cheat("*...\n....\n.*..\n....");
        assertEquals("cheat", "*100\n2210\n1*10\n1110", result);
    }
    @Test
    public void cheat2() {
        String result = new Minesweeper().cheat("**...\n.....\n.*...");
        assertEquals("cheat", "**100\n33200\n1*100", result);
    }


    private char[][] getCircleTestdata() {
        return new char[][]{
                {
                        '.', '.', '.'
                },
                {
                        '.', '.', '.'
                },
                {
                        '.', '*', '.'
                },
                {
                        '.', '.', '.'
                }
        };
    }

    private char[][] getTestdata() {
        return new char[][]{
                {
                        '*', '.', '.', '.'
                },
                {
                        '.', '.', '.', '.'
                },
                {
                        '.', '*', '.', '.'
                },
                {
                        '.', '.', '.', '.'
                }
        };
    }
}
