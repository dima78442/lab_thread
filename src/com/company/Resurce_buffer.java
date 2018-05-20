package com.company;

import java.nio.Buffer;

public class Resurce_buffer {
   private int tmp;
   private int buffer[];
   private int size;
   private int i = 0;
   private int j = 0;

   public Resurce_buffer(int size) {
      buffer = new int[size];
      this.size = size;
   }

   synchronized void consume(){
      if (i != size) {
         int tmp = buffer[i];
         System.out.println("CONSUMER VAL =" + tmp);
         i++;
      }else {
         //System.exit(0);
         //System.out.println("END");
      }
   }
   synchronized void produce(){
      if(j != size){
         buffer[j] = j;
         System.out.println("PRODUCER VAL =" + j);
         j++;
      }else {
         if (i >= size) {
            System.out.println("END");
            System.exit(0);
         }
      }
   }
}
