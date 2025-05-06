package org.example;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class LinearHashTable implements HashTable {





    private final ArrayList<BitRepresntaion> keys;
    private  int n;
    private int tableSize;
    private ArrayList<BigInteger> hashCode;

    private SquareHash[] table;

    int row ,col;

    public ArrayList<BitRepresntaion> getKeys() {
        return keys;
    }

    public int getTableSize() {
        return tableSize;
    }


    public LinearHashTable(int n) {
        this.n = n;
        this.keys = new ArrayList<>();
        setSizes();
        hashCode = new ArrayList<>();
        randomizeHashCode();

    }

    public LinearHashTable() {
        this.n = 100;
        this.keys = new ArrayList<>();
        hashCode = new ArrayList<>();
        setSizes();
        randomizeHashCode();

    }

    public int nearestPowerOfTwo(int x) {
        int y = (int) Math.ceil(Math.log(x) / Math.log(2));
        return (int) Math.pow(2, y);
    }


    private void doubleSize(){
        n *=2;
    }


    public void setSizes(){

        // Call when size change
        tableSize = nearestPowerOfTwo(n);
        table = new SquareHash[tableSize];
          for(int i = 0;i<table.length;i++){
              table[i] = new SquareHash(2);
          }
        this.row = (int) (Math.log(tableSize) / Math.log(2));
        this.col = 360;
    }
    private void randomizeHashCode(){
        hashCode = new ArrayList<>();
        for (int i =0 ;i < row ; i++){
            SecureRandom random = new SecureRandom();
            BigInteger newBig=new BigInteger(360,random);
            hashCode.add(newBig);
        }
    }


    private void rehash(){

        table = new SquareHash[tableSize];
        for(int i = 0;i<table.length;i++){
            table[i] = new SquareHash(2);
        }
        randomizeHashCode();
        for(BitRepresntaion key : keys){
            int hashed = hash(key);
            table[hashed].insert(key.getKey());
        }

    }


    private int hash(BitRepresntaion Bit){
        BigInteger key = Bit.getBitRepresentation();
        int result = 0;
        for(BigInteger bigInt : hashCode){
            BigInteger tmpBigInt=bigInt.and(key);
            int count=tmpBigInt.bitCount();
            result = (result << 1) | count % 2;
        }
        return result;
    }

    @Override
    public int getNumberOfRehashing() {
        int count = 0 ;
        for(SquareHash h : table){
            count += h.getNumberOfRehashing();
        }
        return count;
    }

    @Override
    public boolean insert(String key){
        if(key==null) return false;
        BitRepresntaion bitRepresntaion = new BitRepresntaion(key);
        int index = hash(bitRepresntaion);
        if(table[index].search(key)){
            return false;
        }
        if(keys.size() == tableSize){
            doubleSize();
            setSizes();
            keys.add(bitRepresntaion);
            rehash();
        }else{
            keys.add(bitRepresntaion);
            table[index].insert(key);
        }
        return true;

    }
    @Override
    public boolean delete(String key){
        if(key==null) return false;
        BitRepresntaion bitRepresntaion=new BitRepresntaion(key);
        int index=hash(bitRepresntaion);
        return table[index].delete(key);

    }
    @Override
    public boolean search(String key){
        if(key==null) return false;
        BitRepresntaion bitRepresntaion=new BitRepresntaion(key);
        int index=hash(bitRepresntaion);
        return table[index].search(key);


    }
    @Override
    public boolean insert(ArrayList<String> keyList){
        ArrayList<BitRepresntaion> bitKeys = keyList.stream()
                .map(BitRepresntaion::new)
                .collect(Collectors.toCollection(ArrayList::new));


        int oldElement = removeFound(bitKeys);
        int newElement = 0;

        if(keyList.isEmpty()){
            return false;
        }
        if(bitKeys.size() + keys.size()> tableSize){
            while(keyList.size()+keys.size() > n ){
                doubleSize();
            }
            setSizes();
            rehash();
        }

        for (BitRepresntaion bitNum:bitKeys)
        {
            if(insert(bitNum.getKey())) newElement++;

        }
        System.out.println("Number of New Keys Added Is : "+newElement+" And Number Of Old Keys In Map Is : "+oldElement);

        return true;
    }
    public int removeFound(ArrayList<BitRepresntaion> keys){
        int count = 0 ;
        for(int i = 0;i<keys.size();i++){
            BitRepresntaion key = keys.get(i);
            int index = hash(key);
            if(table[index].search(key.getKey())){
                keys.remove(key);
                i--;
                count++;
            }

        }
        return count;
    }

    @Override
    public boolean delete(ArrayList<String> keyList){
        if(keyList.isEmpty()){
            return false;
        }
        boolean allDeleted = true;
        int deleted = 0;
        for(String key : keyList){
            if (!this.delete(key)) {
                allDeleted = false;
            }
            else{
                deleted++;
            }
        }
        System.out.println("Success to delete : "+deleted+" Keys.");
        System.out.println("Failed to delete : " +(keyList.size()-deleted)+ " Keys.");
        return allDeleted;
    }

    public static void test1(){
        HashTable hash = new LinearHashTable(3);
        hash.insert("cat");
        hash.insert("samaa");
        hash.insert("sama");
        hash.insert("nour");
        hash.insert("ahmed");
        hash.insert("maged");
        hash.insert("hello");
        hash.insert("ziad");
        hash.insert("dog");
        hash.insert("fares");
        hash.search("nour");
        hash.search("samaa");
        hash.search("sama");
        hash.search("cat");
        hash.delete("cat");
        hash.delete("cat");
        hash.search("cat");
    }
    public static void test2(){
        HashTable hash = new LinearHashTable(4);
        hash.insert("cat");
        hash.insert("hat");
        hash.insert("sam");
        hash.insert("nor");

    }
    public static void test4(){
        ArrayList<String> test = new ArrayList<>(Arrays.asList("A","B","C","D","E","F","G","H"));
        HashTable hash = new LinearHashTable(4);
        hash.insert("A");
        hash.insert("D");
        hash.insert(test);// Should say that A , D  Already found .



    }
    public static void test3(){
        ArrayList<String> list = new ArrayList<>();
        list.add("ahmed");
        list.add("sama");
        list.add("samaa");
        list.add("nour");
       list.add("maged");

        HashTable hash = new LinearHashTable(3);
        hash.insert(list);
        hash.search("ahmed");
        hash.search("sama");
        hash.search("samaa");
        hash.delete("nour");
        hash.search("nour");


    }


    public  static void test5(){
        HashTable hash = new LinearHashTable(5);
        hash.insert("sama");
        hash.insert("samaa");
        ArrayList<String> testlist =new ArrayList<>();
        testlist.add("sama");
        testlist.add("ahmed");
        testlist.add("samaa");
        testlist.add("lina");
        testlist.add("LAILA");
        hash.insert(testlist);

    }

    public  static void test6(){
        HashTable hash = new LinearHashTable(5);
        ArrayList<String> testlist =new ArrayList<>();
        testlist.add("sama");
        testlist.add("ahmed");
        testlist.add("samaa");
        testlist.add("lina");
        testlist.add("Lela");
        hash.insert(testlist);
        testlist.add("66");
        hash.delete(testlist);
        testlist.clear();
        hash.insert(testlist);
        hash.delete(testlist);
        hash.insert("sama");

    }

    public static void main(String[] args) {
        System.out.println("====================================================Test1======================================================= ");
        test1();
        System.out.println("====================================================Test2======================================================= ");
        test2();
        System.out.println("====================================================Test3======================================================= ");
        test3();
        System.out.println("====================================================Test4======================================================= ");
        test4();

        System.out.println("====================================================Test5======================================================= ");
        test5();

        System.out.println("====================================================Test6======================================================= ");
        test6();
    }

}
