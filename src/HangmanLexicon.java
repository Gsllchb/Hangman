//Copyright (c) 2017 - present Gsllchb

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

class HangmanLexicon {
    private LinkedList<String> lexicon;

    HangmanLexicon(final String path) throws Exception {
        Scanner in = new Scanner(new BufferedReader(new FileReader(path)));
        lexicon = new LinkedList<>();
        while (in.hasNext()) {
            lexicon.add(in.next().toLowerCase());
        }
    }

    int getWordCount() {
        return lexicon.size();
    }

    String getWord(final int index) {
        return lexicon.get(index);
    }
}
