package ite207;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


import java.util.concurrent.ArrayBlockingQueue;

/**
 * Crawl Task
 *
 */
public class CrawlTask extends Thread
{
    private String urlToCrawl = null;
    private ArrayBlockingQueue<String> urlQueue;
    public CrawlTask(String url, ArrayBlockingQueue<String> queue)
    {
        this.urlToCrawl = url;
        this.urlQueue = queue;
    }

    @Override
    public void run()
    {
        try
        {
            crawl();
        }
        catch(MalformedURLException e)
        {
            System.out.println( "URL not valid: "+urlToCrawl );//continue
        }
        catch(IOException e)
        {
            System.out.println( "Could not fetch "+urlToCrawl );
        }
    }

    public void crawl() throws IOException, MalformedURLException
    {
        URL url = new URL(this.urlToCrawl);

        URLConnection urlConnection = url.openConnection();
        try( InputStream input = urlConnection.getInputStream() )
        {
            Document document = Jsoup.parse(input, "UTF-8", "");
            Elements anchorTags = document.select("a");
            for( Element tag : anchorTags )
            {
                //System.out.println(tag.attr("href"));
                urlQueue.offer( tag.attr("abs:href") );
            }
        }


    }
}
