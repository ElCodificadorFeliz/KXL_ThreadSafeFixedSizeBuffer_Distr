// This source code is UTF-8 coded - see https://stackoverflow.com/questions/9180981/how-to-support-utf-8-encoding-in-eclipse
package bufferManager;


import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import untouchableSupportStuff.Herald;


/**
 * Maker/Task: For information see ReadMe.txt resp. task
 * 
 * @version {@value #encodedVersion}
 * @author  Michael Schäfers ;  P2@Hamburg-UAS.eu  
 */
public class Maker implements Runnable {
    //
    //--VERSION:-------------------------------#---vvvvvvvvv---vvvv-vv-vv--vv
    //  ========                               #___~version~___YYYY_MM_DD__dd_
    final static private long encodedVersion = 2___00001_001___2022_01_16__01L;
    //-----------------------------------------#---^^^^^-^^^---^^^^-^^-^^--^^
    
    
    
    final BufferManager<Long> bm;
    final AtomicLong counter;
    
    
    
    public Maker( final BufferManager<Long> bm,  final AtomicLong counter ){
        this.bm = bm;
        this.counter = counter;
    }//constructor()
    
    
    
    @Override
    public void run(){
        final Random randomGenerator = new Random();
        try{
            while( ! Thread.interrupted() ){
                final long data = counter.getAndIncrement();
                //
                final int randomDelay = 20 + randomGenerator.nextInt( 90 );
                TimeUnit.MILLISECONDS.sleep( randomDelay );
                //
                bm.insert(data);
            }//while
        }catch( final InterruptedException ex ){
            Herald.proclaimExecutingThreadInformation( "received interrupt" );
        }finally{
            Herald.proclaimComingDeathOfExecutingThread();
        }//try            
    }//method()
    
}//class
