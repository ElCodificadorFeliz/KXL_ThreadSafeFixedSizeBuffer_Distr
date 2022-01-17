package bufferManagerTest;


import static org.junit.jupiter.api.Assertions.*;


import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import bufferManagerImplementation.BufferManager;
import bufferManagerImplementation.Maker;
import bufferManagerImplementation.SolutionWrapper;
import bufferManagerImplementation.User;

import bufferManagerVersion.GivenCodeVersion;

import untouchableSupportStuff.EnvironmentAnalyzer;
import untouchableSupportStuff.Herald;
import untouchableSupportStuff.Version;


/**
 * Dieser JUnit(5)-TestFrame ist nur ein Vorschlag bzw. eine Art mögliches
 * Template für Ihre Testumgebung. Es ist also eher eine Anregung als ein Test
 * bzw. für einen ernstzunehmenden Test wird unzureichend getestet.
 * Gern können Sie eine eigene TestUmgebung entwickeln. (Die dann aber auch
 * ausreichend testet.)
 * 
 * Sollten Sie diesen JUnit-Testframe verwenden, beachten Sie, dass sie diesem
 * um weitere Tests erweitern müssen. Dieser TestFrame lässt Dinge ungestestet.
 * 
 * For further information see ReadMe.txt
 * 
 * 
 * @version {@value #encodedVersion}
 * @author  Michael Schäfers ;  Px@Hamburg-UAS.eu 
 */
public class UnitTestProposal {
    //
    //--VERSION:-------------------------------#---vvvvvvvvv---vvvv-vv-vv--vv
    //  ========                               #___~version~___YYYY_MM_DD__dd_
    final static private long encodedVersion = 2___00001_001___2022_01_17__02L;
    //-----------------------------------------#---^^^^^-^^^---^^^^-^^-^^--^^
    final static private Version version = new Version( encodedVersion );
    static public String getDecodedVersion(){ return version.getDecodedVersion(); }
    
    
    
    
    
    // limit for test time
    final static private int commonDefaultLimit = 4_000;                        // default value for timeout resp. max. number of ms for test
    
    
    
    
    
    @BeforeAll
    public static void executeBeforeAllTests(){
        System.out.printf( "General information\n" );
        System.out.printf( "===================\n" );
        System.out.printf( "\n\n" );
        System.out.printf( "Release(s):\n" );
        System.out.printf( "    Actual Test version: %s\n",  version.getDecodedVersion() );
        System.out.printf( "    GivenCode version:   %s\n",  GivenCodeVersion.getDecodedVersion() );
        System.out.printf( "\n\n" );
        System.out.printf( "Environment:\n" );
        System.out.printf( "    Java:                %s\n",  EnvironmentAnalyzer.getJavaVersion() );
        System.out.printf( "    JUnit5/Jupiter:      %s\n",  EnvironmentAnalyzer.getJUnitJupiterVersion() );
        System.out.printf( "    JUnit5/Platform:     %s\n",  EnvironmentAnalyzer.getJUnitPlatformVersion() );
        System.out.printf( "    #Cores:              %d\n",  EnvironmentAnalyzer.getAvailableCores() );
        System.out.printf( "\n\n\n\n" );
        System.out.printf( "Start of actual tests\n" );
        System.out.printf( "=====================\n" );
        System.out.printf( "\n" );
        System.out.flush();
    }//method()
    
    
    
    
    
    @BeforeEach
    public void executeBeforeEachSingleTest(){
        // ...
    }//method()
    
    
    
    
    
    @Test
    public void testForCrashingThreadsDuringStartAndShutDown(){
        final String testName = new Object(){}.getClass().getEnclosingMethod().getName();
        assertTimeoutPreemptively(
            Duration.ofMillis( 20_000 ),
            new Executable(){                                                   // Executable is executed in a different thread
                @Override
                public void execute() throws Throwable {
                    //vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
                    // start of actual test
                    
                    // print header - REMENBER, JUnit tests shall NOT print on screen
                    final StringBuilder sb = new StringBuilder();
                    sb.append( "\n\n" );
                    sb.append( "This is test : " );
                    sb.append( testName );
                    sb.append( "\n" );
                    Herald.proclaimMessage( sb );
                    
                    //
                    //
                    //  SETUP
                    //  =====
                    //
                    final AtomicInteger threadCrashCount = new AtomicInteger( 0 );
                    class SimpleUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {   // Klasse, die definiert wie auf unchecked exceptions reagiert werden soll, die threads werfen
                        @Override
                        public void uncaughtException( Thread t, Throwable ex ){
                            threadCrashCount.incrementAndGet();
                            Herald.proclaimError( String.format(
                                "###\n### [%s] -> %s: %s at line %d of %s\n###\n",
                                SimpleUncaughtExceptionHandler.class.getSimpleName(),
                                t.getName(),
                                ex,
                                ex.getStackTrace()[0].getLineNumber(),
                                ex.getStackTrace()[0].getFileName()
                            ));
                        }//method()
                    }//class
                    
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
                        maker[i].setUncaughtExceptionHandler( new SimpleUncaughtExceptionHandler() );   // define UncaughtExceptionHandler
                        maker[i].start();
                    }//for
                    
                    for( int i=0; i<numberOfUsers; i++ ){
                        user[i] = new Thread( new User( bm ) );
                        user[i].setName( String.format( "User#%d", i ));
                        user[i].setUncaughtExceptionHandler( new SimpleUncaughtExceptionHandler() );    // define UncaughtExceptionHandler
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
                            bm.insert( -1L );                                   // <- white box test :  insert death pill
                        }//for
                        for( int i=0; i<numberOfUsers; i++ ){
                            user[i].join();
                        }//for
                        
                        Herald.proclaimComingDeathOfExecutingThread();
                    }catch( final InterruptedException ex ){
                        ex.printStackTrace();
                    }//try
                    
                    final int numberOfCrashedThreads = threadCrashCount.get();
                    assertEquals( 0, numberOfCrashedThreads,  String.format( "#%d threads have crashed", numberOfCrashedThreads ));
                    
                    // end of actual test
                    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                }//method()
            },                                                                  // end of Executable
            "computation took too long"
        );
    }//method()
    
    
    
    @Test
    public void testSomethingElseAsDemo(){
        final String testName = new Object(){}.getClass().getEnclosingMethod().getName();
        assertTimeoutPreemptively(
            Duration.ofMillis( commonDefaultLimit ),
            new Executable(){                                                   // Executable is executed in a different thread
                @Override
                public void execute() throws Throwable {
                    //vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
                    // start of actual test
                    
                    // print header - REMENBER, JUnit tests shall NOT print on screen
                    final StringBuilder sb = new StringBuilder();
                    sb.append( "\n\n" );
                    sb.append( "This is test : " );
                    sb.append( testName );
                    sb.append( "\n" );
                    Herald.proclaimMessage( sb );
                    
                    // ... actually, there is nothing is tested currently - it's your job to implement test;-)
                    
                    // end of actual test
                    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                }//method()
            },                                                                  // end of Executable
            "computation took too long"
        );
    }//method()
    
    
    
    
    
    @AfterEach
    public void executeAfterEachSingleTest(){
        // ...
    }//method()
    
    
    
    
    
    @AfterAll
    public static void executeAfterAllTests(){
        // ...
    }//method()
    
}//class
