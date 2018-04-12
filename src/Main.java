import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {

  private final static int NUM_GUESSES = 8;
  private final static String DEFAULT_CHAR = "-";
  private final static String INTRO = "The game of Hangman.\n" +
      "args[0]: lexiconPath";

  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.out.println(INTRO);
      return;
    }

    HangmanLexicon lexicon = new HangmanLexicon(args[0]);
    Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    Random random = new Random();
    HangmanCanvas hangmanCanvas = new HangmanCanvas();
    System.out.println("Welcome to Hangman!");
    do {
      final String word = lexicon.getWord(random.nextInt(lexicon.getWordCount()));
      Map<Character, LinkedList<Integer>> map = new HashMap<>();
      for (int i = 0; i < word.length(); ++i) {
        final char key = word.charAt(i);
        if (!map.containsKey(key)) {
          map.put(key, new LinkedList<>());
        }
        map.get(key).add(i);
      }

      StringBuilder status = new StringBuilder();
      for (int i = 0; i < word.length(); ++i) {
        status.append(DEFAULT_CHAR);
      }

      int numGuesses = NUM_GUESSES;
      StringBuilder history = new StringBuilder();

      for (; ; ) {
        if (numGuesses == 0) {
          System.out.println("You're completely hung!");
          hangmanCanvas.show();
          System.out.println("The word was: " + word);
          System.out.println("You lose!");
          break;
        }

        if (status.indexOf(DEFAULT_CHAR) == -1) {
          System.out.println("You guessed the word: " + word);
          System.out.println("You win!");
          break;
        }

        System.out.println("The word now looks like this: " + status);
        System.out.println("You have " + numGuesses + " guess left.");
        hangmanCanvas.show();
        System.out.println("You have inputed:" + history);
        System.out.print("Your guess: ");
        char response;
        for (; ; ) {
          String str = in.next();
          if (str.length() != 1) {
            System.out.println("Input invaild!");
          } else {
            response = str.charAt(0);
            if (Character.isLetter(response)) {
              break;
            } else {
              System.out.println("Input invaild!");
            }
          }
        }
        response = Character.toLowerCase(response);
        history.append(' ').append(response);

        if (map.containsKey(response)) {
          System.out.println("That guess is correct!");
          List<Integer> list = map.get(response);
          for (int i : list) {
            status.setCharAt(i, response);
          }
        } else {
          System.out.println("There are no " + response + "'s in the word.");
          hangmanCanvas.nextStatus();
          --numGuesses;
        }
      }

      hangmanCanvas.reset();
      System.out.println("ENTER 'q' to quit,otherwise to continue.");
    } while (!in.next().equals("q"));
  }
}
