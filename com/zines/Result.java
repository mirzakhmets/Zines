/*    */ package com.zines;
/*    */ 
/*    */ public class Result {
/*  4 */   public String path = null;
/*  5 */   public int length = -1;
/*  6 */   public int offset = -1;
/*  7 */   public String query = null;
/*  8 */   public float ratio = 0.0F;
/*  9 */   public String text = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void printlnHeader() {
/* 16 */     System.out.println("\"FILE\",\"OFFSET\",\"LENGTH\",\"RATIO\"");
/*    */   }
/*    */   
/*    */   public void println() {
/* 20 */     System.out.println("\"" + this.path + "\"," + this.offset + "," + this.length + "," + this.ratio);
/*    */   }
/*    */ }


/* Location:              E:\Windows\My\zines.jar!\com\zines\Result.class
 * Java compiler version: 14 (58.0)
 * JD-Core Version:       1.1.3
 */