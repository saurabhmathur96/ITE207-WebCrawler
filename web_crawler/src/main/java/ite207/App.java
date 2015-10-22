 package ite207;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws InterruptedException
    {
        Storage db = new Storage( "mongodb://localhost:27017", "crawler", "urls");

/*        URLData urlData = new URLData( "aurl", Arrays.asList( "a", "b", "c" ) );
        db.save( urlData );
        db.close( );

*/

        BlockingQueue<Runnable> runnables = new ArrayBlockingQueue<Runnable>(1024);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 16, 60, TimeUnit.SECONDS, runnables);


        BlockingQueue<String> urlQueue = new ArrayBlockingQueue<String>(100);
        String url = "https://www.reddit.com";

        //Thread r = new CrawlTask( url, executor, db, urlQueue );
        executor.submit( new CrawlTask( url, executor, db, urlQueue ) );

        //new CrawlTask( url, executor, db, urlQueue ).run();
        try{

            executor.awaitTermination(1, TimeUnit.MINUTES);

            for (int i=0; i<10;i++ ) {

              executor.submit( new CrawlTask( urlQueue.take(), executor, db, urlQueue ) );
            }
            executor.awaitTermination(1, TimeUnit.MINUTES);
            for (int i=0; i<10;i++ ) {

              executor.submit( new CrawlTask( urlQueue.take(), executor, db, urlQueue ) );
            }
            executor.awaitTermination(1, TimeUnit.MINUTES);
            executor.shutdown();
        }
        catch( Exception e )
        {

        }
        finally
        {
             System.out.println(urlQueue);
            db.close();
        }


        /*
        String url = "https://www.reddit.com";
        ArrayBlockingQueue<String> queue= new ArrayBlockingQueue<String>( 1000 );
        //queue.add( url );
        int nTasks = 5;
        CrawlTask[] ts = new CrawlTask[nTasks];

        int nIters = 12;
        while(nIters-->0)
        {
            for (int i=0; i<nTasks; i++ )
            {

                ts[i] = new CrawlTask( url, queue );
                ts[i].start();
                System.out.println( "Thread started: "+url );

                try
                {
                    url = queue.take();
                }
                catch( InterruptedException e )
                {
                    System.out.println( "Thread interrupted." );
                }



            }

            for (int i=0; i<nTasks; i++ )
            {
                try
                {
                    ts[i].join();
                    System.out.println( "Thread Ended: "+i );

                }
                catch( InterruptedException e )
                {
                    System.out.println( "Thread interrupted." );
                }


            }

        }

        System.out.println( queue );
        */
    }
}
