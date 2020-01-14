package com.jeanchristophe.sdes;

public class Main
{
    public static void main( String[] args )
    {
    	String key = "1010011010";
    	String msg = "hello github !";
    	System.out.println("Message to encode: " + msg + " with key: " + key);
    	SDES sdes = new SDES(key);
    	StringBuilder encMsg = new StringBuilder();
    	for (int i=0; i<msg.length();i++){
    		encMsg.append(sdes.encrypt(msg.charAt(i)));
    	}
    	System.out.println("Message to decode " + encMsg.toString() + "with key: " + key);
    	StringBuilder msgDec = new StringBuilder();
    	for (int i=0; i<encMsg.length();i++){
    		msgDec.append(sdes.decrypt(encMsg.charAt(i)));
    	}
    	System.out.println("Decoded message is: " + msgDec);
    }
}
