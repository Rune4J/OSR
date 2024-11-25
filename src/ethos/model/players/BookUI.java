package ethos.model.players;

import java.util.HashMap;
import java.util.Map;

public class BookUI {

private static Map<Integer, String> bookTitles = new HashMap<>();
private static Map<Integer, Map<Integer, String[]>> bookPages = new HashMap<>();
static {
 
// START OF BOOKS
    	
        		// TITLES FOR EACH BOOK (ITEM ID)
        		bookTitles.put(757, "The Diary of Dionysius");
        		bookTitles.put(6767, "Ending the 5th Age");
        		bookTitles.put(7681, "Game Book - A Guide to Getting Started");
        		// END OF TITLES
        	
        		// START OF BOOK 2 - DIARY OF DIONYSUYS
        		Map<Integer, String[]> book1Pages = new HashMap<>();
        		book1Pages.put(1, new String[]{
        	    "@dre@05-Jan-168", // Frame 1868
        	    "As I sit by the", // Frame 1869
        	    "window of my study,", // Frame 1870
        	    "watching the bustling", // Frame 1871
        	    "city of Draynor, I", // Frame 1872
        	    "am struck by the", // Frame 1873
        	    "paradox of my emotions.", // Frame 1874
        	    "The vibrant, humming", // Frame 1875
        	    "metropolis sprawls", // Frame 1876
        	    "before me is both a", // Frame 1877
        	    "testament to resilience", // Frame 1878
        	    "and a lament for a", // Frame 1879
        	    "bygone simplicity. It", // Frame 1880
        	    "feels like just yesterday", // Frame 1881
        	    "that I penned my last", // Frame 1882
        	    "entry in this very", // Frame 1883
        	    "diary, chronicling", // Frame 1884
        	    "the early tremors", // Frame 1885
        	    "of the world’s upheaval.", // Frame 1886
        	    "Now, the magnitude", // Frame 1887
        	    "of those changes is", // Frame 1888
        	    "impossible to ignore." // Frame 1889
        		});
        		book1Pages.put(2, new String[]{
        	    "The assassination", // Frame 1868
        	    "of Guthix was a", // Frame 1869
        	    "tragedy that echoed", // Frame 1870
        	    "through the fabric", // Frame 1871
        	    "of our world, shaking", // Frame 1872
        	    "the foundations of", // Frame 1873
        	    "existence itself. The", // Frame 1874
        	    "great god’s departure", // Frame 1875
        	    "left a chasm not only", // Frame 1876
        	    "in the heavens but", // Frame 1877
        	    "in the hearts of", // Frame 1878
        	    "every soul who revered", // Frame 1879
        	    "the balance he once", // Frame 1880
        	    "maintained. In his", // Frame 1881
        	    "absence, chaos reigned,", // Frame 1882
        	    "and the world was cast", // Frame 1883
        	    "into a tumultuous age", // Frame 1884
        	    "of conflict and", // Frame 1885
        	    "uncertainty. Draynor,", // Frame 1886
        	    "once a quaint and", // Frame 1887
        	    "modest village", // Frame 1888
        	    "nestled by the sea" // Frame 1889
        		});
        		book1Pages.put(3, new String[]{
        	    "has metamorphosed into", // Frame 1868
        	    "a sprawling port city.", // Frame 1869
        	    "Where humble cottages", // Frame 1870
        	    "once stood, towering", // Frame 1871
        	    "buildings now rise,", // Frame 1872
        	    "their silhouettes", // Frame 1873
        	    "cutting through the", // Frame 1874
        	    "sky like jagged teeth.", // Frame 1875
        	    "The cobbled streets I", // Frame 1876
        	    "once wandered are now", // Frame 1877
        	    "a labyrinth of", // Frame 1878
        	    "marketplaces and grand", // Frame 1879
        	    "thoroughfares, lined", // Frame 1880
        	    "with shops that cater", // Frame 1881
        	    "to sailors, warriors,", // Frame 1882
        	    "and adventurers from", // Frame 1883
        	    "all corners of the realm.", // Frame 1884
        	    "It is as if every", // Frame 1885
        	    "merchant and craftsman", // Frame 1886
        	    "from across the land", // Frame 1887
        	    "has converged here,", // Frame 1888
        	    "drawn by the promise" // Frame 1889
        		});
        		book1Pages.put(4, new String[]{
        	    "of opportunity and", // Frame 1868
        	    "the allure of the sea.", // Frame 1869
        	    "I often find myself", // Frame 1870
        	    "reminiscing about", // Frame 1871
        	    "the days when Draynor", // Frame 1872
        	    "was a sanctuary of", // Frame 1873
        	    "peace, where life", // Frame 1874
        	    "unfolded at a gentler", // Frame 1875
        	    "pace. Those were days", // Frame 1876
        	    "of simplicity and warmth,", // Frame 1877
        	    "where every face was", // Frame 1878
        	    "familiar, and every", // Frame 1879
        	    "story was shared over", // Frame 1880
        	    "a mug of ale. Now, the", // Frame 1881
        	    "city hums with the", // Frame 1882
        	    "ceaseless activity of", // Frame 1883
        	    "travelers and traders,", // Frame 1884
        	    "each with their own", // Frame 1885
        	    "tales of adventure and", // Frame 1886
        	    "woe. Yet, despite the", // Frame 1887
        	    "overwhelming change,", // Frame 1888
        	    "I cannot help but marvel" // Frame 1889
        		});
        		book1Pages.put(5, new String[]{
        	    "at the city’s resilience.", // Frame 1868
        	    "The people of Draynor", // Frame 1869
        	    "have adapted and thrived,", // Frame 1870
        	    "rebuilding from the ashes", // Frame 1871
        	    "of their former lives and", // Frame 1872
        	    "embracing the new", // Frame 1873
        	    "opportunities that have", // Frame 1874
        	    "arisen from the chaos. The", // Frame 1875
        	    "docks are perpetually busy,", // Frame 1876
        	    "and the harbor is a", // Frame 1877
        	    "kaleidoscope of ships from", // Frame 1878
        	    "every corner of the world.", // Frame 1879
        	    "The clang of blacksmiths’", // Frame 1880
        	    "hammers and the cheerful", // Frame 1881
        	    "banter of merchants fill", // Frame 1882
        	    "the air, creating a", // Frame 1883
        	    "symphony of progress and", // Frame 1884
        	    "prosperity. In the quiet", // Frame 1885
        	    "moments, however, when", // Frame 1886
        	    "the city’s din fades into", // Frame 1887
        	    "", // Frame 1888
        	    "" // Frame 1889
        		});
        		book1Pages.put(6, new String[]{
        	    "the background, I find", // Frame 1868
        	    "solace in the simple", // Frame 1869
        	    "rituals of my daily life.", // Frame 1870
        	    "My study, though modest", // Frame 1871
        	    "compared to the grand", // Frame 1872
        	    "edifices outside, remains", // Frame 1873
        	    "my sanctuary—a place where", // Frame 1874
        	    "I can reflect on the profound", // Frame 1875
        	    "changes that have swept", // Frame 1876
        	    "across the world. Here, I", // Frame 1877
        	    "am reminded that even amidst", // Frame 1878
        	    "the grandeur and chaos,", // Frame 1879
        	    "there is still room for", // Frame 1880
        	    "contemplation and wisdom.", // Frame 1881
        	    "The world is on the edge", // Frame 1882
        	    "of a new age. One shaped", // Frame 1883
        	    "by Guthix’s passing and", // Frame 1884
        	    "the relentless drive of those", // Frame 1885
        	    "who seek to forge their own", // Frame 1886
        	    "paths. As I look out over", // Frame 1887
        	    "the city that has become", // Frame 1888
        	    "a beacon of hope and" // Frame 1889
        		});
        		book1Pages.put(7, new String[]{
        	    "opportunity, I am filled", // Frame 1868
        	    "with both pride and sorrow.", // Frame 1869
        	    "Draynor has grown beyond", // Frame 1870
        	    "my wildest imaginings,", // Frame 1871
        	    "yet I cannot forget the", // Frame 1872
        	    "village that once was—a", // Frame 1873
        	    "symbol of a simpler time,", // Frame 1874
        	    "now overshadowed by the", // Frame 1875
        	    "relentless march of progress.", // Frame 1876
        	    "In these pages, I record", // Frame 1877
        	    "not just the history of a", // Frame 1878
        	    "city but the essence of an", // Frame 1879
        	    "era that has passed and", // Frame 1880
        	    "the dawn of a new one", // Frame 1881
        	    "that is still unfolding.", // Frame 1882
        	    "As the sun sets over the", // Frame 1883
        	    "horizon, I am reminded of", // Frame 1884
        	    "the enduring spirit of", // Frame 1885
        	    "Draynor—forever a beacon", // Frame 1886
        	    "in the ever-changing", // Frame 1887
        	    "", // Frame 1888
        	    "" // Frame 1889
        		});
        		bookPages.put(757, book1Pages);
        		// END OF BOOK 1 - DIARY OF DIONYSUYS

        		// START OF BOOKE 2 - ENDING THE 5TH AGE
        		Map<Integer, String[]> book2Pages = new HashMap<>();
        		book2Pages.put(1, new String[]{
        		"Page 1 Line 1", // Frame 1868
        		"Page 1 Line 2", // Frame 1869
        		"Page 1 Line 3", // Frame 1870
        		"Page 1 Line 4", // Frame 1871
        		"Page 1 Line 5", // Frame 1872
        		"Page 1 Line 6", // Frame 1873
        		"Page 1 Line 7", // Frame 1874
        		"Page 1 Line 8", // Frame 1875
        		"Page 1 Line 9", // Frame 1876
        		"Page 1 Line 10", // Frame 1877
        		"Page 1 Line 11", // Frame 1878
        		"Page 1 Line 12", // Frame 1879
        		"Page 1 Line 13", // Frame 1880
        		"Page 1 Line 14", // Frame 1881
        		"Page 1 Line 15", // Frame 1882
        		"Page 1 Line 16", // Frame 1883
        		"Page 1 Line 17", // Frame 1884
        		"Page 1 Line 18", // Frame 1885
        		"Page 1 Line 19", // Frame 1886
        		"Page 1 Line 20", // Frame 1887
        		"Page 1 Line 21", // Frame 1888
        		"Page 1 Line 22"  // Frame 1889
        		});
        		book2Pages.put(2, new String[]{
        		"Page 2 Line 1", // Frame 1868
        		"Page 2 Line 2", // Frame 1869
        		"Page 2 Line 3", // Frame 1870
        		"Page 2 Line 4", // Frame 1871
        		"Page 2 Line 5", // Frame 1872
        		"Page 2 Line 6", // Frame 1873
        		"Page 2 Line 7", // Frame 1874
        		"Page 2 Line 8", // Frame 1875
        		"Page 2 Line 9", // Frame 1876
        		"Page 2 Line 10", // Frame 1877
        		"Page 2 Line 11", // Frame 1878
        		"Page 2 Line 12", // Frame 1879
        		"Page 2 Line 13", // Frame 1880
        		"Page 2 Line 14", // Frame 1881
        		"Page 2 Line 15", // Frame 1882
        		"Page 2 Line 16", // Frame 1883
        		"Page 2 Line 17", // Frame 1884
        		"Page 2 Line 18", // Frame 1885
        		"Page 2 Line 19", // Frame 1886
        		"Page 2 Line 20", // Frame 1887
        		"Page 2 Line 21", // Frame 1888
        		"Page 2 Line 22"  // Frame 1889
        		});
        		bookPages.put(6767, book2Pages);
        		// END OF BOOK 2 - ENDING THE 5TH AGE
        
        		// START OF BOOK 3 - GAME BOOK
        		Map<Integer, String[]> book3Pages = new HashMap<>();
        		book3Pages.put(1, new String[]{
        		"@dre@Getting Started", // Frame 1868
        		"", // Frame 1869
        		"Welcome to the world newbie!", // Frame 187
        		"I am sure you are wondering", // Frame 1871
        		"where you have found", // Frame 1872
        		"yourself. A quick answer,", // Frame 1873
        		"somewhere you've never", // Frame 1874
        		"been before.", // Frame 1875
        		"We offer a unique gameplay", // Frame 1885
        		"experience which is full of", // Frame 1876
        		"challenges and features that", // Frame 1877
        		"are unique to only us!", // Frame 1878
        		"Gameplay starts slow and", // Frame 1879
        		"steady to offer you the", // Frame 1880
        		"full immersive experience.", // Frame 1881
        		"We offer a Journey", // Frame 1882
        		"System, Job system, and", // Frame 1883
        		"our favorite, a Custom", // Frame 1884
        		"Sailing Skill, and much more!", // Frame 1885
        		"", // Frame 1886
        		"You can learn more about", // Frame 1887
        		"how to play, if you continue" // Frame 1888
               	});
        		book3Pages.put(2, new String[]{
        		"reading or you can learn as", // Frame 1889
        		"you go by diving right in!", // Frame 1868
        		"", // Frame 1869
        		"We suggest you at least", // Frame 1871
        		"Talk to the ", // Frame 1872
        		"Draynor Representative", // Frame 1873
        		"near the Draynor Port.", // Frame 1874
        		"Or you can complete the", // Frame 1875
        		"'Learning the ropes'", // Frame 1876
        		"Journey which can be", // Frame 1877
        		"found in the Quest Tab", // Frame 1878
        		"and located in the ", // Frame 1879
        		"'Player Summary' section.", // Frame 1880
        		"", // Frame 1881
        		"@dre@Notice:",  // Frame 1889
        		"If you would like more", // Frame 1882
        		"information on getting", // Frame 1883
        		"started, please continue", // Frame 1884
        		"reading onto the next page.", // Frame 1885
        		"", // Frame 1888
        		"", // Frame 1886
        		" - @cr2@Happy Gaming!" // Frame 1887
        		});
        		bookPages.put(7681, book3Pages);
        		// END OF BOOK 3 - GAME BOOK
        	
        
    
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    }    
// END OF BOOKS   
    
    
// SUPPORTING CODE
public static void openBookInterface(Player c, int itemId) {
c.setCurrentBookId(itemId);
c.setCurrentBookPage(1);
String title = bookTitles.get(itemId);
if (title == null) {
title = "Unknown Book"; // DEFAULT TITLE
}
c.getPA().sendFrame126(title, 1819); // GIVES THE SPECIFIED BOOK A TITLE NAME
c.getPA().sendFrame126("Close", 1818); // OBVIOUSLY CLOSES THE INTERFACE

// SHOWS THE FIRST PAGE
sendBookPage(c);


c.getPA().showInterface(1779);
}

public static void sendBookPage(Player c) {
int page = c.getCurrentBookPage();
int itemId = c.getCurrentBookId();
Map<Integer, String[]> pages = bookPages.get(itemId);
int totalPages = pages != null ? pages.size() : 1;

if (pages != null) {
String[] texts = pages.get(page);
if (texts != null) {
for (int i = 0; i < texts.length; i++) {
c.getPA().sendFrame126(texts[i], 1868 + i); // THIS UPDATES THESE FRAMESs 1868-1889
}
}

// UPDATES THE PAGES
int currentPageNumber = page * 2 - 1; // CALCULATES THE CURRENT PAGE NUMBER
int nextPageNumber = page * 2;       // CALCULATES THE NEXT PAGE NUMBER
            
c.getPA().sendFrame126(String.valueOf(currentPageNumber), 1890); // CURRENT PAGE #
c.getPA().sendFrame126(String.valueOf(nextPageNumber), 1891);   // NEXT PAGE # (PREVIOUS CODE CALCULATES A +2 SINCE THE PAGES ARE OFFSET)

// THIS IS NOT USED RIGHT NOW, BUT IF NEEDED IT WILL ALLOW THE TOTAL NUMBER OF PAGES, IT GOES ON THE RIGHT SIDE OF THE INTERFACE AT ALL TIMES
c.getPA().sendFrame126(String.valueOf(totalPages), 1892); // TOTAL NUMBER OF PAGES CAN BE DISPLAYED WITH THIS, IF WANTED
}
}
    
public static void handleBookInterfaceClick(Player c, int buttonId) {
int currentPage = c.getCurrentBookPage();
int itemId = c.getCurrentBookId();
Map<Integer, String[]> pages = bookPages.get(itemId);
int totalPages = pages != null ? pages.size() : 1;

if (buttonId == 1864) { // LEFT ARROW FRAME ID NOT THE CLICKING BUTTONS ID
if (currentPage > 1) {
c.setCurrentBookPage(currentPage - 1); // GO BACK ONE PAGE
} else {
c.setCurrentBookPage(totalPages); // IF YOU'RE ALREADY ON PAGE 1 THEN WHEN YOU CLICK BACK IT WILL UPDATE THE INTERFACE WITH WHATEVER YOUR "LAST PAGE" IS
}
sendBookPage(c);
} else if (buttonId == 1866) { // RIGHT ARRIW FRAME ID, ALSO NOT THE CLICKING BUTTONS ID
if (currentPage < totalPages) {
c.setCurrentBookPage(currentPage + 1); // GO FORWARD ONE PAGE
} else {
c.setCurrentBookPage(1); // IF YOU'RE AT THE LAST PAGE THEN THIS JUST SENDS YOU BACK TO "PAGE 1"
}
sendBookPage(c);
}
}
}