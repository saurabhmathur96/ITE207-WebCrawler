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

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;




/**
 * Crawl Task
 *
 */
public class CrawlTask extends Thread
{
    static int calls = 0;
    private final String urlToCrawl;
    private ThreadPoolExecutor executor;
    private Storage db;
    private BlockingQueue<String> urlQueue;
    public CrawlTask(String url, ThreadPoolExecutor executor, Storage db, BlockingQueue<String> urlQueue)
    {
        this.urlToCrawl = url;
        this.executor = executor;
        this.db = db;
        this.urlQueue = urlQueue;
    }

    @Override
    public void run()
    {
        /*synchronized (this){
          calls++;
          System.out.println("calls="+calls);
          if(calls > 10){
            return;
          }
        }*/


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
        System.out.println("\""+urlToCrawl+"\"");

        URL url = new URL(this.urlToCrawl);
        List<String> links = new ArrayList<String>();
        URLConnection urlConnection = url.openConnection();
        try( InputStream input = urlConnection.getInputStream() )
        {
            Document document = Jsoup.parse(input, "UTF-8", "");
            //System.out.println(document);
            Elements anchorTags = document.select("a");
            for( Element tag : anchorTags )
            {
              //System.out.println(tag);
                //System.out.println(tag.attr("abs:href"));
                if( tag.attr("abs:href").length() > 2 )
                {
                    urlQueue.offer( tag.attr("abs:href") );
                    String link = tag.attr("abs:href");
                    links.add( link );
                    //executor.submit( new CrawlTask( link, executor, db, urlQueue ) );
                }


            }
            //Storage db = new Storage( "mongodb://localhost:27017", "crawler", "urls");
            db.save( new URLData( urlToCrawl, links ) );
            //db.close();
            //System.out.println(urlQueue);
        }
    }
}
