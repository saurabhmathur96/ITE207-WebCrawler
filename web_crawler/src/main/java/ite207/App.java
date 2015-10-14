package ite207;

import java.util.concurrent.ArrayBlockingQueue;


/**
 * Hello world!
 *
 */
public class App
{
    public static synchronized void main( String[] args )
    {
        System.out.println( "Hello World!" );
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
    }
}
