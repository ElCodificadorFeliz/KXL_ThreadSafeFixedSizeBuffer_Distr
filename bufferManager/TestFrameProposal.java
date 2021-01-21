package bufferManager;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


/*
 *------------------------------------------------------------------------------
 * VCS: git@git.HAW-Hamburg.de:shf/Px/LabExercise/KXL_ThreadSafeFixedSizeBuffer_Distr[.git]
 * 
 * Dieser TestFrame ist nur ein Vorschlag bzw. eine Art mögliches Template für
 * Ihre Testumgebung. gern können Sie eine eigene TestUmgebung entwickeln.
 * (Die dann aber auch ausreichend testet.)
 * 
 * Sollten Sie diesen Testframe verwenden, beachten Sie, dass sie diesem um
 * weitere Tests erweitern müssen. Dieser TestFrame lässt Dinge ungestestet.
 * 
 * For further information see ReadMe.txt
 *                                                Michael Schaefers  2021/01/21
 *------------------------------------------------------------------------------
 */
public class TestFrameProposal {
    
    public static void main( final String... unused ){
        
        //
        //
        //  SETUP
        //  =====
        //
        final int capacity = 5;
        final int numberOfMakers = 13;
        final int numberOfUsers = 13;
        
        
        
        //
        //
        //  action
        //
        final Thread[] maker = new Thread[numberOfMakers];
        final Thread[] user = new Thread[numberOfUsers];
        
        final BufferManager<Long> bm = new SolutionWrapper<Long>( capacity );
        final AtomicLong counter = new AtomicLong( 0 );
        
        
        for( int i=0; i<numberOfMakers; i++ ){
            maker[i] = new Thread( new Maker( bm, counter ) );
            maker[i].setName( String.format( "Maker#%d", i ));
            maker[i].start();
        }//for
        
        for( int i=0; i<numberOfUsers; i++ ){
            user[i] = new Thread( new User( bm ) );
            user[i].setName( String.format( "User#%d", i ));
            user[i].start();
        }//for
        
        
        try{
            TimeUnit.SECONDS.sleep( 10 );
            Herald.proclaimMessage( "Starting shutdown\n" );
            
            
            for( int i=0; i<numberOfMakers; i++ ){
                maker[i].interrupt();
                maker[i].join();
            }//for
            
            for( int i=0; i<numberOfUsers; i++ ){
                bm.insert( -1L );
            }//for
            for( int i=0; i<numberOfUsers; i++ ){
                user[i].join();
            }//for
            
            Herald.proclaimComingDeathOfExecutingThread();
        }catch( InterruptedException ex ){
            ex.printStackTrace();
        }//try
        
    }//method()
    
}//class
