package org.example;


import java.math.BigInteger;
import java.nio.charset.StandardCharsets;


public class BitRepresntaion {
    private String key;
    int bitLength = 360;
    private BigInteger bitRepresentation;
    public BitRepresntaion(String key){
        this.key=key;
        byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
        this.bitRepresentation = new BigInteger(bytes);

    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BigInteger getBitRepresentation() {
        return bitRepresentation;
    }

    public void setBitRepresentation(BigInteger bitRepresentation) {
        this.bitRepresentation = bitRepresentation;
    }

    public static void main(String[] args) {
          BitRepresntaion b = new BitRepresntaion("cat");

   }

}
