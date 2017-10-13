/* Naruth Kongurai (1429760)
 * CSE 373 AB
 * TA: Raquel Van Hofwegen
 * 02/10/17
 * 
 * MyClient is a basic game of matching a word with its synonyms. For instance,
 * given the word "casual", we know that one of its synonyms is "relaxing".
 * Here the user is being asked to type in the word (from the word bank) which
 * he/she thinks shares the same meaning as the given word. 
 * 
 * The implementation of MyClient begins with a brute addition of a bunch of
 * sentences in which each sentence contains words separated by commas. Using
 * these sentences we construct a TextAssociation, taking in the word in the
 * String array first index and associating it with any other words after it.
 * When that's taken cared of, we build an entirely new TreeSet containing all
 * the words considered to be associations. We are able to obtain the
 * associations by simply calling getAssociations(). However, MyClient does not
 * try to break the Set that is returned by calling getAssociations(); MyClient
 * only calls the method to obtain the associations.
 * 
 * These associations are used as a word bank for the player to pick from.
 * This Matching of Synonym game has five rounds in total and at any point
 * after a round is done the user has the option of continuing the game or
 * stopping.
 * 
 * Because MyClient works the way I would like it to be, I presume that the
 * public methods available for using from the TextAssociator class works
 * accordingly. There were no errors produced that stem out of the
 * TextAssociator class. Furthermore, the constructor in TextAssociator and its
 * public methods were able to properly add each word and its associations
 * with no failures.
 */ 

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class MyClient {
    
    public static TextAssociator sc = null;
    public static String[] mainWord = new String[5];
    
    public static void main(String[] args) {
        String[] lines = {"sad,lonely,unhappy,stressful", 
                "casual,relaxing,soothing,calm",
                "thinks,ponders,questions,searches,doubts",
                "courage,wit,will,determination,power",
                "tedious,hard work,uneasy,takes a long time"};
        
        sc = new TextAssociator();
        Set<String> collectionOfAssociations = new TreeSet<String>();
        int j = 0;
        for (String line : lines) {
            String[] words = line.split(",");
            String currentWord = words[0].trim();
            mainWord[j] = currentWord;
            j++;
            sc.addNewWord(currentWord);
            for (int i = 1; i < words.length; i++) {
                sc.addAssociation(currentWord, words[i].trim());
                collectionOfAssociations.add(words[i]);
            }
        }
        printIntroduction();
        tabBreak();
        test(collectionOfAssociations);
        tabBreak();
        System.out.println("Thanks for playing the game! Until next time!");
    }
    
    public static void tabBreak() {
        System.out.println("\n\n");
    }
    
    public static void printIntroduction() {
        System.out.println("Welcome to the basic English grammar course. I'm here to help you\n"
                + "learn English. As an introductory course, you'll learn synonyms! Some\n"
                + "words in the English language are similar in meaning so it's crucial to\n"
                + "know some of them in order to better express our feelings.");
    }
    
    public static void test(Set<String> collectionOfAssociations) {
        Scanner scanner = new Scanner(System.in);
        char response = 'a';
        int i = 0;
        do {
            System.out.println("Question #:" + (i + 1));
            if (i == 4) { // if we're at the last question
                System.out.println("\nOh boy here we go... this is your last round. Make it count!\n");
            }
            System.out.println("Given the word \"" + mainWord[i] + "\" and a list of words to choose from below,\n"
                    + "do you think you know which other word shares the same meaning too?\n");
            
            // Word Bank
            System.out.println("Word Bank:");
            int counter = 0;
            for (String word : collectionOfAssociations) {
                System.out.print(word);
                if (counter < 5) {
                    System.out.print(" ... ");
                }
                counter++;
                if (counter > 5) {
                    System.out.println("");
                    counter = 0;
                }
            }
            
            // User's Response
            System.out.print("\n\nWhich word has the same meaning as " + mainWord[i] + "? ");
            String userAnswer = scanner.nextLine().trim();
            while (!collectionOfAssociations.contains(userAnswer)) {
                System.out.println("You must choose a word from the word bank.");
                System.out.print("\nYour response: ");
                userAnswer = scanner.nextLine().trim();
            }
            
            // Response = WRONG
            while (!sc.getAssociations(mainWord[i]).contains(userAnswer)) {
                System.out.println("Boo! That's wrong. No worries because I am giving you another chance!");
                System.out.print("\nThink carefully. Which of the word above has the same meaning as " + mainWord[i] + "? ");
                userAnswer = scanner.nextLine().trim();
            }
            
            // Response = RIGHT
            System.out.println("\nThat's right! Both " + mainWord[i] + " and " + userAnswer + " share similar meanings.\n"
                    + "When two words share similar meanings, they are called synonyms.");
            System.out.println("\n- - - - - - - - -");
            i++;
            if (i == 5) {
                break; // last question already so exit
            }
            
            // Play again? (assuming if not at the last round yet)
            System.out.print("Do you want to play again? (Y for yes. Anything other letter to exit)  ");
            response = scanner.next().toLowerCase().charAt(0);
            System.out.println("\n- - - - - - - - -");
            tabBreak();
            
        } while (response == 'y');
    }
   
}
