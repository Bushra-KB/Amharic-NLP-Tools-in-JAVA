/**
 *
 * @author Bushra
 */
package nlp;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Tokenizer {

    public List<String> tokenize(String text) {
        List<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(text);
        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }
        return tokens;
    }

    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer();
        String text = "እንኳን ደህና መጣህ!";
        List<String> tokens = tokenizer.tokenize(text);
        System.out.println("Tokens: " + tokens);
    }
}
