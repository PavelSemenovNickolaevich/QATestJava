package ru.stqa.pft.mantis.model;

import com.google.common.collect.ForwardingSet;

import java.util.*;

public class User extends ForwardingSet<UserData> {

    private Set<UserData> delegate;

    public User(User users) {
        this.delegate = new HashSet<UserData>(users.delegate);
    }

    public User() {
        this.delegate = new HashSet<UserData>();
    }

    public User(Collection<UserData> contacts) {
        this.delegate = new HashSet<UserData>(contacts);
    }

    @Override
    protected Set<UserData> delegate() {
        return delegate;
    }

    public User withAdded(UserData user){
        User users = new User(this);
        users.add(user);
        return users;
    }

    public User without(UserData user){
        User users = new User(this);
        users.remove(user);
        return users;
    }
}
