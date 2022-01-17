// This source code is UTF-8 coded - see https://stackoverflow.com/questions/9180981/how-to-support-utf-8-encoding-in-eclipse
package bufferManager;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import untouchableSupportStuff.EnvironmentAnalyzer;
import untouchableSupportStuff.GivenCodeVersion;
import untouchableSupportStuff.Herald;


/**
 * Dieser TestFrame ist nur ein Vorschlag bzw. eine Art mögliches Template
 * für Ihre Testumgebung. Es ist also eher eine Anregung als ein Test
 * bzw. für einen ernstzunehmenden Test wird unzureichend getestet.
 * Gern können Sie eine eigene TestUmgebung entwickeln. (Die dann aber auch
 * ausreichend testet.)
 * 
 * Sollten Sie diesen Testframe verwenden, beachten Sie, dass sie diesem um
 * weitere Tests erweitern müssen. Dieser TestFrame lässt Dinge ungestestet.
 * 
 * For further information see ReadMe.txt
 * 
 * 
 * @version {@value #encodedVersion}
 * @author  Michael Schäfers ;  P2@Hamburg-UAS.eu  
 */
public class TestFrameProposal {
    //
    //--VERSION:-------------------------------#---vvvvvvvvv---vvvv-vv-vv--vv
    //  ========                               #___~version~___YYYY_MM_DD__dd_
    final static private long encodedVersion = 2___00001_001___2022_01_16__01L;
    //-----------------------------------------#---^^^^^-^^^---^^^^-^^-^^--^^
    
    
    
    public static void main( final String... unused ){
        // print test frame version
        System.out.printf( "Given code version:     %s\n",   GivenCodeVersion.getDecodedVersion() );
        // print some informations about environment
        System.out.printf( "Environment:\n" );
        System.out.printf( "    Java:               %s\n",  EnvironmentAnalyzer.getJavaVersion() );
        System.out.printf( "    JUnit5/Jupiter:     %s\n",  EnvironmentAnalyzer.getJUnitJupiterVersion() );
        System.out.printf( "    JUnit5/Platform:    %s\n",  EnvironmentAnalyzer.getJUnitPlatformVersion() );
        System.out.printf( "    #Cores:             %d\n",  EnvironmentAnalyzer.getAvailableCores() );
        System.out.printf( "==================================\n" );
        System.out.printf( "\n\n" );
        System.out.flush();
        
        
        
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
                bm.insert( -1L );                                               // insert death pill
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
