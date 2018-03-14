package com.tigerzhang.classloader;

/**
 * Created by tigerzhang on 2018/3/13.
 */
public class XXX {
    public boolean validWordSquare(String[] words) {
        // Write your code here
        for (int i = 0; i < words.length; i++) {
            String rowStr = words[i];

            for (int j = 0; j < words.length; j++) {
                char a = rowStr.charAt(j);
                char b = words[j].charAt(i);
                if (a != b) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int wordsTyping(String[] sentence, int rows, int cols) {
        // Write your code here
        int row = 0;
        int idx = 0;
        int count = 0;

        while (row < rows) {
            int colRemain = cols - sentence[idx++].length();
            if (idx == sentence.length) {
                idx = 0;
                count += 1;
            }
            while (colRemain > 0) {
                if (sentence[idx].length() <= colRemain - 1) {
                    colRemain = colRemain - sentence[idx].length() - 1;
                    idx += 1;
                    if (idx >= sentence.length) {
                        idx = 0;
                        count += 1;
                    }
                } else {
                    break;
                }
            }

            row += 1;
        }
        return count;
    }

    public static boolean validPalindrome(String s) {
        // Write your code here
        for (int i = -1; i < s.length(); i++) {
            if (isPalindrome(s, i)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isPalindrome(String s, int omit) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (left == omit) {
                left += 1;
            }
            if (right == omit) {
                right -= 1;
            }
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left += 1;
            right -= 1;
        }
        return true;
    }

    public static void main(String[] args) {
        String[] word = new String[]{"a", "bcd", "e"};
        System.out.println(validPalindrome("abca"));
        System.out.println(wordsTyping(word, 3, 6));
    }
}
