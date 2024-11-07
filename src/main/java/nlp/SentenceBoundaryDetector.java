/**
 *
 * @author Bushra
 */
package nlp;

import java.util.ArrayList;
import java.util.List;

public class SentenceBoundaryDetector {

    public List<Integer> detectBoundaries(String text) {
        List<Integer> boundaries = new ArrayList<>();
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '.' || chars[i] == '!' || chars[i] == '?') {
                boundaries.add(i);
            }
        }
        return boundaries;
    }

    public static void main(String[] args) {
        SentenceBoundaryDetector detector = new SentenceBoundaryDetector();
        String text = "እንኳን ደህና መጣህ! እንዴት ነህ?";
        List<Integer> boundaries = detector.detectBoundaries(text);
        System.out.println("Boundaries: " + boundaries);
    }
}
