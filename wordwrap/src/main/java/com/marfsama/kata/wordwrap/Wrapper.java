package com.marfsama.kata.wordwrap;

/**
 * word wrap kata.
 */
public class Wrapper {
    public String wrap(String text, int maxLength) {
        if (text.length() > maxLength) {
            String firstLine = text.substring(0, maxLength);

            int lastSpace = firstLine.lastIndexOf(' ');
            // Kein Leerzeichen gefunden
            if (lastSpace < 0) {
                String rest = text.substring(maxLength);
                return firstLine + "\n" + wrap(rest, maxLength);
            }

            int firstSpace = firstLine.indexOf(' ');
            if (firstSpace == 0) {
                // Leerzeichen an 0. Stelle: überspingen, in dem der Alghorithmus
                // ab dem nächsten Zeichen aufgerufen wird.
                String rest = text.substring(1);
                return wrap(rest, maxLength);
            }
            // Leerzeichen gefunden an Stelle "space"
            firstLine = text.substring(0, lastSpace);

            String rest = text.substring(lastSpace + 1);
            return firstLine.trim() + "\n" + wrap(rest, maxLength);
        }

        return text;
    }
}
