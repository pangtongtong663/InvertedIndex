package hust.cs.javacourse.search.util;

import java.util.HashSet;
import java.util.Set;

public class StopWordsSet {
    public static Set<String> STOP_WORDS_SET = new HashSet<>();
    static {
        for (String word : StopWords.STOP_WORDS) {
            STOP_WORDS_SET.add(word);
        }
    }

}
