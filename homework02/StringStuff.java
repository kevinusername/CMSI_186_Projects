
/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  StringStuff.java
 *  Purpose       :  A file full of stuff to do with the Java String class
 *  Author        :  B.J. Johnson
 *  Date          :  2017-01-19
 *  Author        :  Kevin Peters
 *  Date          :  2018-01-30
 *  Description   :  This file presents a bunch of String-style helper methods.  Although pretty much
 *                   any and every thing you'd want to do with Strings is already made for you in the
 *                   Jave String class, this exercise gives you a chance to do it yourself [DIY] for some
 *                   of it and get some experience with designing code that you can then check out using
 *                   the real Java String methods [if you want]
 *  Notes         :  None
 *  Warnings      :  None
 *  Exceptions    :  None
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision History
 *  ----------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2017-01-19  B.J. Johnson  Initial writing and release
 *  @version 1.1.0  2017-01-22  B.J. Johnson  Fill in methods to make the program actually work
 *  @version 2.0.0  2018-01-30  Kevin Peters  Added initial version of working code
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class StringStuff {

    /**
     * Method to determine if a string contains one of the vowels: A, E, I, O, U, and sometimes Y.
     * Both lower and upper case letters are handled.  In this case, the normal English rule for Y means
     * it gets included.
     *
     * @param s String containing the data to be checked for &quot;vowel-ness&quot;
     * @return  boolean which is true if there is a vowel, or false otherwise
     */
    public static boolean containsVowel(String s) {
        char[] vowels = { 'A', 'E', 'I', 'O', 'U', 'Y' };
        String upper_s = s.toUpperCase();
        for (int i = 0; i < vowels.length; i++) {
            if (upper_s.indexOf(vowels[i]) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to determine if a string is a palindrome.  Uses a StringBuilder to create a new string that is the reverse of the input.
     * Then tests if the input and its reverse are the same.
     *
     * @param s String containing the data to be checked for &quot;palindrome-ness&quot;
     * @return  boolean which is true if this a palindrome, or false otherwise
     */
    public static boolean isPalindrome(String s) {
        StringBuilder s_reverse = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            s_reverse.append(s.charAt(i));
        }
        String s_test = s_reverse.toString();
        if (s.equals(s_test)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method to return the characters in a string that correspond to the &quot;EVEN&quot; index
     * numbers of the alphabet.  The letters B, D, F, H, J, L, N, P, R, T, V, X, and Z are even,
     * corresponding to the numbers 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, and 26.
     *
     * @param s String containing the data to be parsed for &quot;even&quot; letters
     * @return  String containing the &quot;even&quot; letters from the input
     */
    public static String evensOnly(String s) {
        char[] even_letters = { 'B', 'D', 'F', 'H', 'J', 'L', 'N', 'P', 'R', 'T', 'V', 'X', 'Z' };
        StringBuilder s_evens = new StringBuilder();
        String upper_s = s.toUpperCase();
        //Adds all "even" letters to a string builder
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < even_letters.length; j++) {
                if (upper_s.charAt(i) == even_letters[j]) {
                    s_evens.append(s.charAt(i));
                    break;
                }
            }
        }
        return s_evens.toString();
    }

    /**
     * Method to return the characters in a string that correspond to the &quot;ODD&quot; index
     * numbers of the alphabet.  The letters A, C, E, G, I, K, M, O, Q, S, U, W, and Y are odd,
     * corresponding to the numbers 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, and 25.
     *
     * @param s String containing the data to be parsed for &quot;odd&quot; letters
     * @return  String containing the &quot;odd&quot; letters from the input
     */
    public static String oddsOnly(String s) {
        char[] odd_letters = { 'A', 'C', 'E', 'G', 'I', 'K', 'M', 'O', 'Q', 'S', 'U', 'W', 'Y' };
        StringBuilder s_odds = new StringBuilder();
        String upper_s = s.toUpperCase();
        //Adds all "odd" character to a StringBuilder 
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < odd_letters.length; j++) {
                if (upper_s.charAt(i) == odd_letters[j]) {
                    s_odds.append(s.charAt(i));
                    break;
                }
            }
        }
        return s_odds.toString();
    }

    /**
     * Method to return the characters in a string that correspond to the &quot;EVEN&quot; index
     * numbers of the alphabet, but with no duplicate characters in the resulting string.
     *
     * @param s String containing the data to be parsed for &quot;even&quot; letters
     * @return  String containing the &quot;even&quot; letters from the input without duplicates
     */
    public static String evensOnlyNoDupes(String s) {
        String s_evens = evensOnly(s);
        Set unique_evens = new LinkedHashSet();
        //Adds all elements to an ordered set, which cannot contain duplicates
        for (int i = 0; i < s_evens.length(); i++) {
            unique_evens.add(s_evens.charAt(i));
        }
        return unique_evens.toString().replace("[", "").replace(", ", "").replace("]", "");

    }

    /**
     * Method to return the characters in a string that correspond to the &quot;ODD&quot; index
     * numbers of the alphabet, but with no duplicate characters in the resulting string.
     *
     * @param s String containing the data to be parsed for &quot;odd&quot; letters
     * @return  String containing the &quot;odd&quot; letters from the input without duplicates
     */
    public static String oddsOnlyNoDupes(String s) {
        String s_odds = oddsOnly(s);
        Set unique_odds = new LinkedHashSet();
        //Adds all elements to an ordered set, which cannot contain duplicates
        for (int i = 0; i < s_odds.length(); i++) {
            unique_odds.add(s_odds.charAt(i));
        }
        return unique_odds.toString().replace("[", "").replace(", ", "").replace("]", "");
    }

    /**
     * Method to return the reverse of a string passed as an argument
     *
     * @param s String containing the data to be reversed
     * @return  String containing the reverse of the input string
     */
    public static String reverse(String s) {
        char[] s_array = s.toCharArray();
        StringBuilder s_reverse = new StringBuilder();
        for (int i = s_array.length - 1; i >= 0; i--) {
            s_reverse.append(s_array[i]);
        }
        return s_reverse.toString();

    }

    /**
     * Main method to test the methods in this class
     *
     * @param args String array containing command line parameters
     */
    public static void main(String args[]) {
        String blah = new String("Blah blah blah");
        String woof = new String("BCDBCDBCDBCDBCD");
        String pal1 = new String("a");
        String pal2 = new String("ab");
        String pal3 = new String("aba");
        String pal4 = new String("amanaplanacanalpanama");
        String pal5 = new String("abba");
        System.out.println(containsVowel(blah));
        System.out.println(containsVowel(woof));
        System.out.println(isPalindrome(pal1));
        System.out.println(isPalindrome(pal2));
        System.out.println(isPalindrome(pal3));
        System.out.println(isPalindrome(pal4));
        System.out.println(isPalindrome(pal5));
        System.out.println("evensOnly()        returns: " + evensOnly("REHEARSALSZ"));
        System.out.println("evensOnly()        returns: " + evensOnly("REhearSALsz"));
        System.out.println("evensOnlyNoDupes() returns: " + evensOnlyNoDupes("REhearSALsz"));
        System.out.println("oddsOnly()         returns: " + oddsOnly("xylophones"));
        System.out.println("oddsOnly()         returns: " + oddsOnly("XYloPHonES"));
        System.out.println("oddsOnlyNoDupes()  returns: " + oddsOnlyNoDupes("XYloPHonES"));
        System.out.println("reverse()          returns: " + reverse("REHEARSALSZ"));
    }
}