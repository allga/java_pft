package ru.stqa.pft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Olga on 12.03.2016.
 */
public class Collections {

    public static void main(String[] args) {
//        String[] langs = new String[4];
//        langs[0] = "Java";
//        langs[1] = "C#";
//        langs[2] = "Python";
//        langs[3] = "PHP";
        String[] langs = {"Java", "C#", "Python", "PHP"};

//        List<String> languages = new ArrayList<String>();

        List<String> languages = Arrays.asList("Java", "C#", "Python", "PHP");
//        languages.add("Java");
//        languages.add("C#");
//        languages.add("Python");

//        for (int i = 0; i < langs.length; i++) {
//            System.out.println("I want to learn " + langs[i]);
        for (String l : languages) {
            System.out.println("I want to learn " + l);

        }

    }
}
