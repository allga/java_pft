package ru.stqa.pft.addressbook.tests;

import ru.stqa.pft.addressbook.model.GroupData;
import template.GenericMap;
import template.GenericSet;

/**
 * Created by Olga on 08.04.2016.
 */
public class SetExample {

    public static void main(String[] args) {
        GenericSet<String> mySet = new GenericSet<>(10, new String[10]);
//        System.out.println(mySet.put("value1"));
//        System.out.println(mySet.put("value2"));
//        System.out.println(mySet.put("value3"));
//        System.out.println(mySet.put("value1"));
//        System.out.println(mySet.exists("value4"));

        GroupData group1 = new GroupData().setName("Group1");
        GroupData group2 = new GroupData().setName("Group2");
        GroupData group3 = new GroupData().setName("Group3");
        GroupData group4 = new GroupData().setName("Group3");
        GroupData group5 = new GroupData().setName("Group4");
        GroupData group6 = new GroupData().setName("Group4");

        GenericSet<GroupData> groupSet = new GenericSet<>(10, new GroupData[10]);
//        System.out.println(groupSet.put(group1));
//        System.out.println(groupSet.put(group2));
//        System.out.println(groupSet.put(group3));
//        System.out.println(groupSet.put(group3));
//        System.out.println(groupSet.exists(group2));

        GenericMap<String, String> students = new GenericMap<>(10, new String[10], new String[10]);
        System.out.println(students.put("Ivanov", "Voina i mir"));
        System.out.println(students.put("Petrov", "Kolobok"));
        System.out.println(students.put("Petrov", "Zapovit"));
        System.out.println(students.put("Sidorov", "Zapovit"));
        System.out.println(students.get("Ivanov"));
    }
}
