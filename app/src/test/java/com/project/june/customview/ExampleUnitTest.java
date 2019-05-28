package com.project.june.customview;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
//        int i,j;
//        for (i =1;i < 100; i++) {
//            if (i % 3 !=0 || i % 7 !=0){
//                continue;
//            }
//            System.out.println("i:" + i);
//        }

//        int m = 53;
//        int j;
//        for (j = 2; j < m; j++) {
//            if (m % j == 0) {
//                break;
//            }
//        }
//        if (m >= j) {
//            System.out.println(m + "是素数");
//        } else {
//            System.out.println(m + "是不是素数" );
//        }

//        String string = "abcdefg";
//        char[] chars = new char[string.length()];
//        for (int i = 0; i < string.length(); i++) {
//            chars[i] = string.charAt(string.length() - i - 1);
//        }
//        System.out.print(new String(chars));

//        int sum = 0;
//        for (int i = 0; i <= 100; i++) {
//            sum = sum + i;
//        }
//        System.out.print(sum);

        int number = 52225;
        if ((number % 10 == number / 10000) && ((number % 1000) / 100 == (number % 10000) / 1000)) {
            System.out.print("回文");

        } else {
            System.out.print("非回文");
        }
    }
}