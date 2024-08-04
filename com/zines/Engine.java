/*     */ package com.zines;
/*     */ 
/*     */ public class Engine {
/*     */   public boolean ignoreCase = false;
/*   5 */   public int matchGap = 10;
/*   6 */   public float goldenRatio = 0.45F;
/*   7 */   public float minGoldenRatio = 0.01F;
/*     */ 
/*     */   
/*     */   public boolean doPreciseSearch = false;
/*     */ 
/*     */ 
/*     */   
/*     */   Result queryExact(String text, String query_text, String text_0, String query_text_0, int cur_page) {
/*  15 */     float max_scal_match = 0.0F;
/*  16 */     int max_matched = 0;
/*  17 */     int best_match_start = -1;
/*  18 */     int best_match_end = -1;
/*  19 */     int best_page_line = -1;
/*  20 */     int best_page_offset = -1;
/*  21 */     int page_line = 0;
/*  22 */     int page_offset = 0;
/*  23 */     int page = 0;
/*  24 */     int i = 0;
/*  25 */     while (i < text.length()) {
/*  26 */       float scal_match = 0.0F;
/*  27 */       int matched = 0;
/*  28 */       int last_match_pos = -1;
/*  29 */       int v_page = page;
/*  30 */       int v_page_line = page_line;
/*  31 */       int v_page_offset = page_offset;
/*  32 */       int first_match_pos = -1;
/*  33 */       boolean passed = (query_text.length() > 0 && i < text.length());
/*  34 */       int j = 0;
/*  35 */       int k = i;
/*     */       
/*  37 */       while (j < query_text.length() && k < text.length()) {
/*     */ 
/*     */         
/*  40 */         if (text.charAt(k) == query_text.charAt(j)) {
/*  41 */           int gap = 1;
/*  42 */           if (last_match_pos != -1) {
/*  43 */             gap = j - last_match_pos;
/*     */           }
/*  45 */           last_match_pos = j;
/*  46 */           if (first_match_pos == -1) {
/*  47 */             first_match_pos = i;
/*     */           }
/*  49 */           if (this.doPreciseSearch && gap > 1) {
/*  50 */             passed = false;
/*     */             break;
/*     */           } 
/*  53 */           int last_match_pos_text = i;
/*  54 */           matched++;
/*  55 */           scal_match += 1.0F / gap;
/*  56 */           if (scal_match > max_scal_match && !this.doPreciseSearch) {
/*  57 */             max_scal_match = scal_match;
/*  58 */             best_match_start = first_match_pos;
/*  59 */             best_match_end = k;
/*  60 */             best_page_line = v_page_line;
/*  61 */             best_page_offset = v_page_offset;
/*  62 */             int best_page = v_page;
/*  63 */             max_matched = matched;
/*     */           } 
/*     */         } 
/*  66 */         if (text.charAt(k) == '\n') {
/*  67 */           v_page_line++;
/*  68 */           v_page_offset = 0;
/*  69 */         } else if (text.charAt(k) == '\f') {
/*  70 */           v_page++;
/*  71 */           v_page_offset = 0;
/*  72 */           v_page_line = 0;
/*     */         } else {
/*  74 */           v_page_offset++;
/*     */         } 
/*  76 */         j++;
/*  77 */         k++;
/*     */       } 
/*  79 */       if (this.doPreciseSearch && passed && matched == query_text.length() && scal_match > max_scal_match) {
/*  80 */         max_scal_match = scal_match;
/*  81 */         best_match_start = first_match_pos;
/*  82 */         best_match_end = i + matched - 1;
/*  83 */         best_page_line = v_page_line;
/*  84 */         best_page_offset = v_page_offset;
/*  85 */         int best_page2 = v_page;
/*  86 */         max_matched = matched;
/*     */       } 
/*  88 */       if (text.charAt(i) == '\n') {
/*  89 */         page_line++;
/*  90 */         page_offset = 0;
/*  91 */       } else if (text.charAt(i) == '\f') {
/*  92 */         page++;
/*  93 */         page_offset = 0;
/*  94 */         page_line = 0;
/*     */       } else {
/*  96 */         page_offset++;
/*     */       } 
/*  98 */       i++;
/*     */     } 
/* 100 */     if (max_matched == 0) {
/* 101 */       return null;
/*     */     }
/* 103 */     float ratio = max_scal_match / max_matched;
/* 104 */     if (ratio < this.goldenRatio) {
/* 105 */       return null;
/*     */     }
/* 107 */     Result result = new Result();
/* 108 */     result.ratio = ratio;
/* 109 */     result.offset = best_match_start;
/* 110 */     result.length = best_match_end - best_match_start + 1;
/* 111 */     result.text = text_0;
/* 112 */     result.query = query_text_0;
/*     */ 
/*     */ 
/*     */     
/* 116 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   Result queryLarge(String text, String query_text, String text_0, String query_text_0, int cur_page) {
/* 121 */     float max_scal_match = 0.0F;
/* 122 */     int best_match_start = -1;
/* 123 */     int best_match_end = -1;
/* 124 */     int best_page_line = -1;
/* 125 */     int best_page_offset = -1;
/* 126 */     int page_line = 0;
/* 127 */     int page_offset = 0;
/* 128 */     int page = 0;
/* 129 */     float scal_match = 0.0F;
/* 130 */     int pos_text = 0;
/* 131 */     int pos_query = 0;
/* 132 */     int last_pos_text = 0;
/* 133 */     int matched = 0;
/* 134 */     int max_matched = 0;
/* 135 */     int last_match_pos_text = -1;
/* 136 */     int last_match_pos_query = -1;
/* 137 */     while (pos_text < text.length() && pos_query < query_text.length()) {
/* 138 */       if (text.charAt(pos_text) == query_text.charAt(pos_query) && (pos_text - last_match_pos_text <= 6 || last_match_pos_text == -1) && (pos_query - last_match_pos_query <= 6 || last_match_pos_query == -1)) {
/* 139 */         int gap; if (last_pos_text == -1) {
/* 140 */           last_pos_text = pos_text;
/*     */         }
/* 142 */         int gap2 = 1;
/* 143 */         if (last_match_pos_query != -1) {
/* 144 */           gap2 = pos_query - last_match_pos_query;
/*     */         }
/* 146 */         matched++;
/* 147 */         switch (gap2) {
/*     */           case 0:
/*     */           case 1:
/* 150 */             gap = 1;
/*     */             break;
/*     */           case 2:
/* 153 */             gap = 2;
/*     */             break;
/*     */           case 3:
/* 156 */             gap = 2;
/*     */             break;
/*     */           case 4:
/* 159 */             gap = 2;
/*     */             break;
/*     */           case 5:
/* 162 */             gap = 2;
/*     */             break;
/*     */           default:
/* 165 */             gap = 2;
/*     */             break;
/*     */         } 
/* 168 */         scal_match += 1.0F / gap;
/* 169 */         last_match_pos_text = pos_text;
/* 170 */         last_match_pos_query = pos_query;
/* 171 */         if (scal_match > max_scal_match) {
/* 172 */           max_scal_match = scal_match;
/* 173 */           best_match_start = last_pos_text;
/* 174 */           best_match_end = pos_text;
/* 175 */           best_page_line = page_line;
/* 176 */           best_page_offset = page_offset;
/* 177 */           int best_page = page;
/* 178 */           max_matched = matched;
/*     */         } 
/* 180 */         if (text.charAt(pos_text) == '\n' || text.charAt(pos_text) == '\013') {
/* 181 */           page_line++;
/* 182 */           page_offset = 0;
/* 183 */         } else if (text.charAt(pos_text) == '\f') {
/* 184 */           page++;
/* 185 */           page_offset = 0;
/* 186 */           page_line = 0;
/*     */         } else {
/* 188 */           page_offset++;
/*     */         } 
/* 190 */         pos_text++;
/* 191 */         pos_query++; continue;
/* 192 */       }  if (pos_text - last_match_pos_text > 6 || last_match_pos_text == -1 || pos_query - last_match_pos_query > 6 || last_match_pos_query == -1) {
/* 193 */         scal_match = 0.0F;
/* 194 */         matched = 0;
/* 195 */         last_match_pos_query = -1;
/* 196 */         last_match_pos_text = -1;
/* 197 */         last_pos_text = -1;
/* 198 */         pos_query = 0;
/* 199 */         if (pos_text < text.length() && query_text.charAt(0) != text.charAt(pos_text))
/* 200 */           pos_text++; 
/*     */         continue;
/*     */       } 
/* 203 */       boolean matched1 = false;
/* 204 */       int i = pos_query + 1;
/* 205 */       int k = 0;
/*     */       while (true) {
/* 207 */         if (i < query_text.length() && k < 4) {
/* 208 */           if (text.charAt(pos_text) == query_text.charAt(i)) {
/* 209 */             pos_query = i;
/* 210 */             matched1 = true;
/*     */             break;
/*     */           } 
/* 213 */           i++;
/* 214 */           k++;
/*     */         } 
/*     */       } 
/*     */       
/* 218 */       if (!matched1) {
/* 219 */         int v_page = page;
/* 220 */         int v_page_line = page_line;
/* 221 */         int v_page_offset = page_offset;
/* 222 */         int i2 = pos_text + 1;
/* 223 */         int k2 = 0;
/*     */         while (true) {
/* 225 */           if (i2 < text.length() && k2 < 4) {
/* 226 */             if (query_text.charAt(pos_query) == text.charAt(i2)) {
/* 227 */               page = v_page;
/* 228 */               page_line = v_page_line;
/* 229 */               page_offset = v_page_offset;
/* 230 */               pos_text = i2;
/* 231 */               matched1 = true;
/*     */               break;
/*     */             } 
/* 234 */             if (text.charAt(pos_text) == '\n' || text.charAt(pos_text) == '\013') {
/* 235 */               v_page_line++;
/* 236 */               v_page_offset = 0;
/* 237 */             } else if (text.charAt(pos_text) == '\f') {
/* 238 */               page++;
/* 239 */               page_offset = 0;
/* 240 */               v_page_line = 0;
/*     */             } else {
/* 242 */               v_page_offset++;
/*     */             } 
/* 244 */             i2++;
/* 245 */             k2++;
/*     */           } 
/*     */         } 
/*     */         
/* 249 */         if (!matched1) {
/* 250 */           pos_text++;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 255 */     float ratio = max_scal_match / max_matched;
/* 256 */     float ratio2 = max_matched / query_text.length();
/* 257 */     if (ratio < this.goldenRatio || ratio2 < this.minGoldenRatio) {
/* 258 */       return null;
/*     */     }
/* 260 */     Result result = new Result();
/* 261 */     result.ratio = ratio;
/* 262 */     result.offset = best_match_start;
/* 263 */     result.length = best_match_end - best_match_start + 1;
/* 264 */     result.text = text_0;
/* 265 */     result.query = query_text_0;
/*     */ 
/*     */ 
/*     */     
/* 269 */     return result;
/*     */   }
/*     */   
/*     */   public Result query(String text, String query_text, boolean doExact) {
/* 273 */     if (text == null || query_text == null) {
/* 274 */       return null;
/*     */     }
/* 276 */     String text_0 = text;
/* 277 */     String query_text_0 = query_text;
/* 278 */     String text2 = convert(text);
/* 279 */     String query_text2 = convert(query_text);
/* 280 */     if (doExact) {
/* 281 */       return queryExact(text2, query_text2, text_0, query_text_0, 0);
/*     */     }
/* 283 */     return queryLarge(text2, query_text2, text_0, query_text_0, 0);
/*     */   }
/*     */   
/*     */   String convert(String text) {
/* 287 */     if (text == null) {
/* 288 */       return null;
/*     */     }
/* 290 */     if (this.ignoreCase) {
/* 291 */       return text.toLowerCase();
/*     */     }
/* 293 */     return text;
/*     */   }
/*     */ }


/* Location:              E:\Windows\My\zines.jar!\com\zines\Engine.class
 * Java compiler version: 14 (58.0)
 * JD-Core Version:       1.1.3
 */