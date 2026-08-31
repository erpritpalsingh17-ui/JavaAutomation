package com.example.tests;

public class Reverse {
    public static void main(String[] args) {

 // Approch 1
//         String str = "Pritpal";
//         String rev ="";

//     for(int i=str.length()-1; i>=0; i--)
//     {
 
//  rev= rev+str.charAt(i);
//     }
// System.out.println("Reversed string is: " + rev);
    
    
//Approch 2

// StringBuffer s = new StringBuffer("Pritpal");
// s.reverse();
// System.out.println("Reversed string is: " + s);

 // Approch 3

 StringBuilder s = new StringBuilder("Pritpal");
s.reverse();
System.out.println("Reversed string is: " + s);
    }
}



