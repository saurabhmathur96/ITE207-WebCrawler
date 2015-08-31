package WebCrawler;

/**
 * Created by saurabh on 24/8/15.
 */

public class Main {
    public static void main(String[] args){
        try{
            HTMLFetcher htmlFetcher = new HTMLFetcher("https://www.twitter.com");
            System.out.println(htmlFetcher.fetchDocument());
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        

    }
}

