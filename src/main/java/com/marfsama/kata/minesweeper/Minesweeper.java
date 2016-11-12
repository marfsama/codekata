package com.marfsama.kata.minesweeper;


/**
 * Mine Sweeper Kata
 */
public class Minesweeper {
    public String cheat(String input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }

        char[][] array = resolveToArray(input);
        int[][] bombs = findBombs(array);

        String result = resultToString(bombs);

        return result;
    }

    /** Wandelt den Eingabestring in ein zweidimensionales Array um. */
    char[][] resolveToArray(String input)  {
        // einzelne Zeilen lesen

        String[] lines = input.split("\n");
        int height = lines.length;
        int width = lines[0].length();

        char[][] chars = new char[height][width];

        for (int i = 0; i < lines.length; i++)
        {
            String line = lines[i];
            for (int c = 0; c < line.length(); c++) {
                char currentChar = line.charAt(c);
                chars[i][c] = currentChar;
            }
        }

        return chars;
    }

    int[][] findBombs(char[][] bombs) {
        int[][] result = new int[bombs.length][bombs[0].length];

        for (int x = 0; x < result.length; x++) {
            for (int y = 0; y < result[0].length; y++) {
                char in = bombs[x][y];
                if (in == '*') {
                    result[x][y] = -1;
                }
                else {
                    int bombsInNeighbourhood = 0;
                    // oben
                    if (x > 0 && bombs[x - 1][y] == '*') {
                        bombsInNeighbourhood++;
                    }
                    // unten
                    if (x < result.length-1 && bombs[x + 1][y] == '*') {
                        bombsInNeighbourhood++;
                    }
                    // links
                    if (y > 0 && bombs[x][y-1] == '*') {
                        bombsInNeighbourhood++;
                    }
                    // rechts
                    if (y < result[0].length-1 && bombs[x][y+1] == '*') {
                        bombsInNeighbourhood++;
                    }

                    // oben links
                    if (x > 0 && y > 0 && bombs[x-1][y-1] == '*') {
                        bombsInNeighbourhood++;
                    }
                    // oben rechts
                    if (x > 0 && y < result[0].length-1 && bombs[x-1][y+1] == '*') {
                        bombsInNeighbourhood++;
                    }
                    // unten links
                    if (x < result.length-1 && y > 0 && bombs[x+1][y-1] == '*') {
                        bombsInNeighbourhood++;
                    }
                    // unten rechts
                    if (x < result.length-1 && y < result[0].length-1 && bombs[x+1][y+1] == '*') {
                        bombsInNeighbourhood++;
                    }

                    result[x][y] = bombsInNeighbourhood;
                }
            }
        }

        return result;
    }

    String resultToString(int[][] cheats) {
        StringBuilder result = new StringBuilder();
        for (int x = 0; x < cheats.length;x++) {
            for (int y = 0; y < cheats[0].length;y++) {
                int i = cheats[x][y];
                if (i == -1) {
                    result.append("*");
                } else {
                    result.append(i);
                }
            }
            result.append("\n");
        }
        return result.toString().trim();
    }
}
