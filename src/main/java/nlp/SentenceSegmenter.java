/**
 *
 * @author Bushra
 */
package nlp;

import java.util.ArrayList;
import java.util.List;

public class SentenceSegmenter {

    public List<String> segment(String text) {
        List<String> sentences = new ArrayList<>();
        String[] splitSentences = text.split("(?<!\\w\\.\\w.)(?<![A-Z][a-z]\\.)(?<=\\.|\\?)\\s");
        for (String sentence : splitSentences) {
            sentences.add(sentence.trim());
        }
        return sentences;
    }

    public static void main(String[] args) {
        SentenceSegmenter segmenter = new SentenceSegmenter();
        String text = "እንኳን ደህና መጣህ! እንዴት ነህ?";
        List<String> sentences = segmenter.segment(text);
        System.out.println("Sentences: " + sentences);
    }
}
