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
 * ...
 * 
 * @version {@value #encodedVersion}
 * @author  Michael Schäfers ;  Px@Hamburg-UAS.eu 
 */
public class UnitTestProposal {
    //
    //--VERSION:-------------------------------#---vvvvvvvvv---vvvv-vv-vv--vv
    //  ========                               #___~version~___YYYY_MM_DD__dd_
    final static private long encodedVersion = 2___00001_000___2022_01_17__01L;
    //-----------------------------------------#---^^^^^-^^^---^^^^-^^-^^--^^
    final static private Version version = new Version( encodedVersion );
    static public String getDecodedVersion(){ return version.getDecodedVersion(); }
    
    
    
    
    
    // limit for test time
    final static private int commonDefaultLimit = 4_000;                        // timeout resp. max. number of ms for test
    
    
    
    
    
    @BeforeAll
    public static void executeBeforeAllTests(){
        System.out.printf( "TestFrame information\n" );
        System.out.printf( "=====================\n" );
        System.out.printf( "\n\n" );
        System.out.printf( "Release:\n" );
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
    public void testStartAndShutDownAsDemo(){
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
                    class SimpleUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
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
                        maker[i].setUncaughtExceptionHandler( new SimpleUncaughtExceptionHandler() );
                        maker[i].start();
                    }//for
                    
                    for( int i=0; i<numberOfUsers; i++ ){
                        user[i] = new Thread( new User( bm ) );
                        user[i].setName( String.format( "User#%d", i ));
                        user[i].setUncaughtExceptionHandler( new SimpleUncaughtExceptionHandler() );
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
                    
                    // ... currently actually nothing is tested - it's your job ;-)
                    
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
