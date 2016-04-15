package ru.stqa.ptf.mantis.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Olga on 15.04.2016.
 */
public class Users extends ForwardingSet<User> {

    private Set<User> delegate;

    public Users(Users users) {
        this.delegate = new HashSet<User>(users.delegate());
    }

    public Users() {
        this.delegate = new HashSet<User>();
    }

    public Users(Collection<User> users) {
        this.delegate = new HashSet<User>(users);
    }

    @Override
    protected Set<User> delegate() {
        return delegate;
    }
}
