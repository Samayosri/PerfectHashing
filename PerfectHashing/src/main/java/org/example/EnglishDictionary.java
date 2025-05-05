package org.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.lang.invoke.StringConcatFactory;
import java.util.ArrayList;

public class EnglishDictionary {
    private HashTable hashTable;
    public  EnglishDictionary(String type){
        if(type.equalsIgnoreCase("square")){
            this.hashTable = new SquareHash();
            
        } else if (type.equalsIgnoreCase("linear")) {
            this.hashTable= new LinearHashTable();
        }
    }
    public boolean insert(String key){
          return this.hashTable.insert(key);
    }
    public boolean search(String key){
        return this.hashTable.search(key);
    }
    public boolean delete(String key){
        return this.hashTable.delete(key);
    }
    private ArrayList<String> readFile(String filePath ){
        ArrayList<String> keyList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                keyList.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  keyList;
    }
    public boolean insertBatch(String filePath){


        return this.hashTable.insert(readFile(filePath));
    }
    public boolean deleteBatch(String filePath){

        return this.hashTable.delete(readFile(filePath));
    }
}
