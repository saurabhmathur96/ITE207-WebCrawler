package WebCrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * Created by saurabh on 24/8/15.
 */
public class HTMLFetcher{
    private URL url;
    private InputStream inputStream = null;
    private BufferedReader bufferedReader;
    private StringBuffer HTMLDocument;

    public HTMLFetcher(String url) throws MalformedURLException
    {
        this.url = new URL(url);
        HTMLDocument = new StringBuffer();



    }

    public String fetchDocument()
    {
        try {
            this.inputStream = this.url.openStream();
            this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = bufferedReader.readLine())!=null)
            {
                HTMLDocument.append(line);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {

                if (inputStream != null)
                {
                    try {
                        inputStream.close();

                }            catch(IOException e)
                    {
                        e.printStackTrace();
                    }
            }

        }
        return HTMLDocument.toString();
    }



}
