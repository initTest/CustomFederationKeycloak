package com.lockin.auth.provider.simple;

import java.util.HashMap;

public class HashMapUserStore {

    private HashMap<String, User> hashMapStorage;

    public HashMapUserStore(){
        this.hashMapStorage = new HashMap<>(3);
        this.hashMapStorage.put("alex", new User("alex", "LOAD "));
        this.hashMapStorage.put("kevin", new User("kevin", "kevin@123"));
    }

    public User getUser(String username){
        return this.hashMapStorage.get(username);
    }
}
