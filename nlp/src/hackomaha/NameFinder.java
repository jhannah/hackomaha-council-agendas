package hackomaha;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

import java.util.*;

public class NameFinder {
    private final NameFinderME nameFinder;
    private SentenceDetector sentenceDetector;

    public NameFinder() {
        TokenNameFinderModel model = Builder.tokenNameFinderModel("resources/en-ner-person.bin");
        nameFinder = new NameFinderME(model);
        sentenceDetector = new SentenceDetector();
    }

    public Set<String> findNames(String phrase) throws Exception {
        HashSet<String> names = new HashSet<String>();
        for (String sentence : sentenceDetector.getSentences(phrase)) {
            handleSentence(names, sentence);
        }
        return names;
    }

    private void handleSentence(HashSet<String> names, String sentence) {
        String[] words = sentence.split(" ");
        Span[] spans = nameFinder.find(words);
        for (Span span : spans) {
            names.add(addPhrase(words, span));
        }
    }

    private String addPhrase(String[] words, Span span) {
        String val = "";
        for (int i = span.getStart(); i < span.getEnd(); i++) {
            val += words[i] + " " ;
        }
        return val.trim();
    }
}
