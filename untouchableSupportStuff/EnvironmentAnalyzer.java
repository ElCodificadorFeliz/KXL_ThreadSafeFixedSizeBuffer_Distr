package untouchableSupportStuff;


import java.io.Serializable;
//
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Version;
//
import java.util.Optional;
//
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;


/**
 * The EnvironmentAnalyzer analyzes the environment. 
 * 
 * @version {@value #encodedVersion}
 * @author  Michael Schäfers ;  Px@Hamburg-UAS.eu  
 */
public class EnvironmentAnalyzer implements Serializable {
    //
    //--VERSION:-------------------------------#---vvvvvvvvv---vvvv-vv-vv--vv
    //  ========                               #___~version~___YYYY_MM_DD__dd_
    final static private long encodedVersion = 2___00000_999___2021_01_05__03L;
    //-----------------------------------------#---^^^^^-^^^---^^^^-^^-^^--^^
    //
    static final private long serialVersionUID = encodedVersion;
    
    
    
    
    
    public static int getAvailableCores() {
        return Runtime.getRuntime().availableProcessors();
    }//method()
    
    public static String getJavaVersion(){
        final String rawVersion = System.getProperty( "java.version" );
        if( rawVersion.startsWith("1.") ){
            return String.format( "%s (%s)",  rawVersion.substring( 2 ), rawVersion );
        }else{
            return rawVersion;
        }//if
    }// method()
    
    public static String getJUnitJupiterVersion(){
        final String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try{
            final Class<Test> testClass = org.junit.jupiter.api.Test.class;
            final Module module = testClass.getModule();
            if( null != module ){
                final ModuleDescriptor moduleDescriptor = module.getDescriptor();
                if( null != moduleDescriptor ){
                    final Optional<Version> optionalVersion = moduleDescriptor.version();
                    if( optionalVersion.isPresent() ){
                        return optionalVersion.get().toString();
                    }//if
                }//if
            }//if
            final Package pakage = testClass.getPackage();
            if( null != pakage ){
                final String version = pakage.getImplementationVersion();
                if( null != version ){
                    return version;
                }//if
            }//if
        }catch( final Exception ex ){
            final StringBuilder sb = new StringBuilder();
            sb.append( "\n\n" );
            sb.append( "UNEXPECTED probably JUnit5 related exception occurred in :  " );
            sb.append( methodName );
            sb.append( "\n" );
            sb.append( "message :  " );
            sb.append( ex.getMessage() );
            sb.append( "\n" );
            Herald.proclaimError( sb );
            ex.printStackTrace();
            Herald.proclaimError( "\nCall advisor.\n\n" );
        }//try
        return "??? <- could not be determined as result of an unexpected exception";
    }//method()
    //
    public static String getJUnitPlatformVersion(){
        final String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try{
            final Class<JUnitPlatform> jUnitPlatformClass = org.junit.platform.runner.JUnitPlatform.class;
            final Module module = jUnitPlatformClass.getModule();
            if( null != module ){
                final ModuleDescriptor moduleDescriptor = module.getDescriptor();
                if( null != moduleDescriptor ){
                    final Optional<Version> optionalVersion = moduleDescriptor.version();
                    if( optionalVersion.isPresent() ){
                        return optionalVersion.get().toString();
                    }//if
                }//if
            }//if
            final Package pakage = jUnitPlatformClass.getPackage();
            if( null != pakage ){
                final String version = pakage.getImplementationVersion();
                if( null != version ){
                    return version;
                }//if
            }//if
        }catch( final Exception ex ){
            final StringBuilder sb = new StringBuilder();
            sb.append( "\n\n" );
            sb.append( "UNEXPECTED probably JUnit5 related exception occurred in :  " );
            sb.append( methodName );
            sb.append( "\n" );
            sb.append( "message :  " );
            sb.append( ex.getMessage() );
            sb.append( "\n" );
            Herald.proclaimError( sb );
            ex.printStackTrace();
            Herald.proclaimError( "\nCall advisor.\n\n" );
        }//try
        return "??? <- could not be determined as result of an unexpected exception";
    }//method()
    
}//class
