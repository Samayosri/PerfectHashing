package org.example;

import java.util.ArrayList;

public interface HashTable {

    public boolean insert(String key);
    public boolean delete(String key);
    public boolean search(String key);
    public boolean insert(ArrayList<String> keyList);
    public boolean delete(ArrayList<String> keyList);

}
