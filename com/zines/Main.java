/*     */ package com.zines;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ 
/*     */ public class Main {
/*     */   public static String readFile(String path) {
/*   8 */     StringBuilder content = new StringBuilder();
/*     */     
/*     */     try {
/*  11 */       BufferedReader br = new BufferedReader(new FileReader(path));
/*     */       
/*     */       int c;
/*  14 */       while ((c = br.read()) != -1) {
/*  15 */         content.append((char)c);
/*     */       }
/*     */       
/*  18 */       br.close();
/*  19 */     } catch (Exception e) {
/*  20 */       return null;
/*     */     } 
/*     */     
/*  23 */     return content.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/*  34 */     Engine engine = new Engine();
/*  35 */     boolean doExact = false;
/*  36 */     boolean doRecrusive = false;
/*  37 */     String query = null;
/*     */     
/*  39 */     int i = 0;
/*     */     
/*  41 */     while (i < args.length) {
/*  42 */       String arg = args[i].toLowerCase();
/*     */       
/*  44 */       if (arg.equals("-g")) {
/*  45 */         i++;
/*     */         
/*  47 */         engine.matchGap = Integer.parseInt(args[i]);
/*     */         
/*  49 */         i++; continue;
/*  50 */       }  if (arg.equals("-r")) {
/*  51 */         i++;
/*     */         
/*  53 */         engine.goldenRatio = Float.parseFloat(args[i]);
/*     */         
/*  55 */         i++; continue;
/*  56 */       }  if (arg.equals("-mr")) {
/*  57 */         i++;
/*     */         
/*  59 */         engine.minGoldenRatio = Float.parseFloat(args[i]);
/*     */         
/*  61 */         i++; continue;
/*  62 */       }  if (arg.equals("-i")) {
/*  63 */         i++;
/*     */         
/*  65 */         engine.ignoreCase = true; continue;
/*  66 */       }  if (arg.equals("-p")) {
/*  67 */         i++;
/*     */         
/*  69 */         engine.doPreciseSearch = true; continue;
/*  70 */       }  if (arg.equals("-x")) {
/*  71 */         i++;
/*     */         
/*  73 */         doExact = true; continue;
/*  74 */       }  if (arg.equals("-recursive")) {
/*  75 */         i++;
/*     */         
/*  77 */         doRecrusive = true; continue;
/*  78 */       }  if (arg.equals("-q")) {
/*  79 */         i++;
/*     */         
/*  81 */         query = args[i];
/*     */         
/*  83 */         i++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  89 */     if (query == null && i < args.length) {
/*  90 */       query = readFile(args[i]);
/*     */       
/*  92 */       i++;
/*     */     } 
/*     */     
/*  95 */     if (i >= args.length) {
/*  96 */       System.out.println("Zines NoSQL Database System");
/*     */       
/*     */       return;
/*     */     } 
/* 100 */     if (query == null) {
/* 101 */       System.out.println("Empty query");
/*     */       
/*     */       return;
/*     */     } 
/* 105 */     for (; i < args.length; i++) {
/* 106 */       System.out.println("Searching: " + args[i]);
/*     */       
/* 108 */       Result.printlnHeader();
/*     */       
/* 110 */       int total = search(engine, query, args[i], doExact, doRecrusive);
/*     */       
/* 112 */       System.out.println("Total results: " + total);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int search(Engine engine, String query, String path, boolean doExact, boolean doRecursive) {
/* 117 */     int total = 0;
/*     */     
/* 119 */     File file = new File(path);
/*     */     
/* 121 */     if (file.isDirectory()) {
/* 122 */       File[] files = file.listFiles();
/*     */       
/* 124 */       if (files != null) {
/* 125 */         for (File value : files) {
/* 126 */           if (value.isDirectory()) {
/* 127 */             if (doRecursive) {
/* 128 */               total += search(engine, query, value.getPath(), doExact, doRecursive);
/*     */             }
/*     */           } else {
/* 131 */             total += search(engine, query, value.getPath(), doExact, doRecursive);
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 136 */       return total;
/*     */     } 
/* 138 */     String content = readFile(path);
/*     */     
/* 140 */     if (content == null) {
/* 141 */       System.out.println("Error reading file: " + path);
/* 142 */       return 0;
/*     */     } 
/*     */     
/* 145 */     int offset = 0;
/*     */     
/* 147 */     while (offset < content.length()) {
/* 148 */       Result result = engine.query(content.substring(offset), query, doExact);
/*     */       
/* 150 */       if (result != null) {
/* 151 */         result.path = path;
/*     */         
/* 153 */         result.offset += offset;
/*     */         
/* 155 */         result.println();
/*     */         
/* 157 */         total++;
/*     */         
/* 159 */         offset += result.offset + result.length; continue;
/*     */       } 
/* 161 */       return total;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 166 */     return total;
/*     */   }
/*     */   
/*     */   public static void test1() {
/* 170 */     System.out.println(search(new Engine(), "was", "src/com/zines/tests", true, true));
/*     */   }
/*     */   
/*     */   public static void test2() {
/* 174 */     System.out.println(search(new Engine(), "was", "src/com/zines/tests", false, true));
/*     */   }
/*     */ }


/* Location:              E:\Windows\My\zines.jar!\com\zines\Main.class
 * Java compiler version: 14 (58.0)
 * JD-Core Version:       1.1.3
 */