
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class BasicWebCrawler {

    private HashSet<String> links;

    public BasicWebCrawler() {
        links = new HashSet<String>();
    }

    public void getPageLinks(String URL) {
        //4. Check if you have already crawled the URLs
        //(we are intentionally not checking for duplicate content in this example)
        if (!links.contains(URL)) {
            try {
                //4. (i) If not add it to the index
                if (links.add(URL)) {
                    System.out.println(URL);
                }

                //2. Fetch the HTML code
                Document document = Jsoup.connect(URL).get();
                //3. Parse the HTML to extract links to other URLs
                Elements linksOnPage = document.select("a[href]");
                Elements imagesOnPage = document.select("img[src");
                for (Element image : imagesOnPage) {
                    System.out.println(image.attr("abs:src")); // extracting images on page.
                }

                //5. For each extracted URL... go back to Step 4.
                for (Element page : linksOnPage) {
                    if(page.attr("abs:href").contains("https://wiprodigital.com")){
                        getPageLinks(page.attr("abs:href")); // Same domain links. Need to navigate.
                    }
                    else{
                        System.out.println(page.attr("abs:href")); // printing external links. NO need to navigate.
                    }

                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        //1. Pick a URL from the frontier
        new BasicWebCrawler().getPageLinks("https://wiprodigital.com/");
    }

}
