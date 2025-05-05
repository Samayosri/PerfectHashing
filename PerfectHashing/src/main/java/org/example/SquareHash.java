package org.example;
import java.security.SecureRandom;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

public class SquareHash implements HashTable {
    private final ArrayList<BitRepresntaion> keys;
    private  int n;
    private ArrayList<BigInteger> hashCode;

    private String[] table;

    int row ,col;


    public SquareHash(int n) {
        this.n = n;
        this.keys = new ArrayList<>();
        setSizes();
        hashCode = new ArrayList<>();
        randomizeHashCode();

    }

    public SquareHash() {
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
        System.out.println("size now doubled  = "+ n);
    }
    public void printHashCode(){
        for(BigInteger b : hashCode){
            System.out.println(b.toString());
        }
        System.out.println("-------------------");
        for(BigInteger b : hashCode){
            System.out.println(b.toString(2));
        }

    }

    public void setSizes(){

        // Call when size change
        int tableSize = nearestPowerOfTwo(n*n);
        table = new String[tableSize];
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
        printHashCode();
    }


    private void rehash(){

        boolean collision =false;
        do{
            randomizeHashCode();
            for(BitRepresntaion key : keys){
                int hashed=hash(key);
                if(table[hashed]!=null){
                    collision=true;
                     break;
                }
                else{
                    table[hashed]=key.getKey();
                }
            }

        }while(collision);

        System.out.println("rehash has just occured size is : "+ n+"number keys = "+keys.size());

    }


    private int hash(BitRepresntaion Bit){
        BigInteger key=Bit.getBitRepresentation();
        int result =0;
        for(BigInteger bigInt : hashCode){
           BigInteger tmpBigInt=bigInt.and(key);
            int count=tmpBigInt.bitCount();
            result = (result << 1) | count % 2;
        }
        System.out.println("Key "+Bit.getKey()+" has rep "+Bit.getBitRepresentation()+" index : "+ result);
        return result;
    }
    @Override
    public boolean insert(String key){
        if(key==null) return false;
        BitRepresntaion bitRepresntaion = new BitRepresntaion(key);
        int index = hash(bitRepresntaion);
        if(key.equals(table[index])){
            System.out.println("Key { "+key+" } , Already Mapped.");
            return false;
        }
        if(keys.size() == n){
            doubleSize();
            setSizes();
            keys.add(bitRepresntaion);
            rehash();
        }else{
            keys.add(bitRepresntaion);
            if(table[index] != null){
                rehash();
            }else{
                table[index] = key;

            }
        }
        System.out.println("Key { "+key+" } , Inserted Successfully.");
        return true;

    }
    @Override
    public boolean delete(String key){
             if(key==null) return false;
             BitRepresntaion bitRepresntaion=new BitRepresntaion(key);
             int index=hash(bitRepresntaion);
             if(table[index]==null){
                System.out.println("key "+key +" Not Found .");
               return false;
             }
             Iterator<BitRepresntaion> it = keys.iterator();
             boolean found = false;
             while(it.hasNext()) {
                 BitRepresntaion bt = it.next();
                 if (bt.getKey().equals(key)) {
                     it.remove();
                     table[index] = null;
                     found = true;
                     break;
                 }
             }
             if(!found){
                 System.out.println("Index found but with another key value .");
             }
             else{
                 System.out.println("key deleted : "+ key);
             }

        return found;
    }
    @Override
    public boolean search(String key){
        if(key==null) return false;
        BitRepresntaion bitRepresntaion=new BitRepresntaion(key);
        int index=hash(bitRepresntaion);
        if(table[index]==null){
            System.out.println("key : "+key+" not found");
            return false;
        }
        boolean found=false;
        for (BitRepresntaion bt : keys) {
            if (bt.getKey().equals(key)) {
                found = true;
                System.out.println("key : "+key+" found at index "+index);
                break;
            }
        }
        return found;

    }
    @Override
    public boolean insert(ArrayList<String> keyList){
        ArrayList<BitRepresntaion> bitKeys = keyList.stream()
                .map(BitRepresntaion::new)
                .collect(Collectors.toCollection(ArrayList::new));

        if(keyList.size()+keys.size()> n){
            System.out.println("Batch insert will exceed size");
            while(keyList.size()+keys.size()> n ){
                doubleSize();
            }
            setSizes();
            rehash();
        }
        int oldElement = 0,newElement = 0;
        for (BitRepresntaion bitNum:bitKeys)
        {
            if(insert(bitNum.getKey()))
            {
                newElement++;
            }
            else
                oldElement++;
        }
        System.out.println("Number of New Elements added is : "+newElement+" and Number of Old Elements in map is : "+oldElement);

     return true;
    }
    @Override
    public boolean delete(ArrayList<String> keyList){
        boolean allDeleted = true;
        int deleted = 0;
        for(String key : keyList){
           if (!this.delete(key)) {
               allDeleted = false;
               System.out.println("Failed to delete key : "+key);
           }
           else{
               deleted++;
           }
        }
        System.out.println("Success to delete : "+deleted+" Keys.");
        return allDeleted;
    }

    public static void test1(){
        // some times infine loop some times not
        SquareHash hash = new SquareHash(3);
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
        // some times infine loop some times not
        SquareHash hash = new SquareHash(2);
        hash.insert("cat");
        hash.insert("samaa");
        hash.insert("sama");
        hash.insert("nour");

    }

    public static void main(String[] args) {
        test1();
        System.out.println("test 2");
    }





}