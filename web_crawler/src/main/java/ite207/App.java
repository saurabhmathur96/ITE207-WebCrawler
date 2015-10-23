 package ite207;


import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
/**
 * Main App Class
 *
 */
public class App
{
    public static void main( String[] args ) throws InterruptedException
    {

        String mongouri = "";
        int maxMinutes = 0;
        int nIters = 0 ;
        List<String> seeds = null;
        int corePoolSize = 0;
        int maxPoolSize = 0;
        int keepAliveTime = 0;
        if( args.length != 1 )
        {
            System.out.println( "Please specify config file" );
            System.exit( 0 );
        }
        try
        {
            XMLConfiguration config = new XMLConfiguration( args[0] );
            mongouri = config.getString( "MongoURI" );
            maxMinutes = config.getInt( "MaxTimeInMinutes" );
            nIters = config.getInt( "NIters" );
            seeds = config.getList( "SeedURLs" );
            corePoolSize = config.getInt( "CorePoolSize" );
            maxPoolSize = config.getInt( "MaxPoolSize" );
            keepAliveTime = config.getInt( "KeepAliveTimeMS" );

        }
        catch(ConfigurationException cex)
        {
            System.out.println( "Config file not found" );
            System.exit( 0 );
        }




        BlockingQueue<Runnable> runnables = new ArrayBlockingQueue<Runnable>(1024);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, runnables);


        BlockingQueue<String> urlQueue = new ArrayBlockingQueue<String>(1024);



        for( String seed:seeds )
        {
            urlQueue.offer( seed );
        }

        Storage db = new Storage( mongouri, "crawler", "urls" );

        try{



            for (int i=0; i<nIters;i++ )
            {
                executor.submit( new CrawlTask( urlQueue.take(), executor, db, urlQueue ) );
            }
            executor.awaitTermination(maxMinutes, TimeUnit.MINUTES);
            executor.shutdown();
        }
        catch( Exception e )
        {

        }
        finally
        {
            db.close();
        }



    }
}
