package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Olga on 23.03.2016.
 */
public class Groups extends ForwardingSet<GroupData> {

    private Set<GroupData> delegate;

    // конструктор создает копию объекта класса Groups
    public Groups(Groups groups) {
        this.delegate = new HashSet<GroupData>(groups.delegate);
    }

    public Groups() {
        this.delegate = new HashSet<GroupData>();
    }

    @Override
    protected Set<GroupData> delegate() {
        return delegate;
    }

    public Groups withAdded(GroupData group) {
        Groups groups = new Groups(this); // создаем копию множества групп
        groups.add(group); // добавляем к нему новую группу
        return groups; // возвращаем копию множества с добавленной группой
    }

    public Groups without(GroupData group) {
        Groups groups = new Groups(this);
        groups.remove(group);
        return groups;
    }
}
