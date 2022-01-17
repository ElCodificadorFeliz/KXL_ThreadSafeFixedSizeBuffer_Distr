// This source code is UTF-8 coded - see https://stackoverflow.com/questions/9180981/how-to-support-utf-8-encoding-in-eclipse
package untouchableSupportStuff;


import java.io.Serializable;


/**
 * The single purpose of this class is to hold the &quot;project version&quot;.
 * Since the code/project is an exercise, it's named {@link Version}.
 * It's not expected or wanted that there are different release branches.
 * There has to be only one single release branch (for the "given code")!!!
 * Hence, there is a single/central project version
 * that is stored in this class.<br />
 * <br />
 * <code>
 * Coding/format of (Given) Code Version<br />
 * &nbsp;&nbsp;c: coding format<br />
 * &nbsp;&nbsp;m: main version<br />
 * &nbsp;&nbsp;s: sub version<br />
 * &nbsp;&nbsp;Y: year<br />
 * &nbsp;&nbsp;M: month<br />
 * &nbsp;&nbsp;D: day<br />
 * &nbsp;&nbsp;d: version of day<br />
 * <br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; c___mmmm_sss___YYYY_MM_DD__ddd<br />
 * &nbsp;&nbsp;e.g.&nbsp;&nbsp;                     1___0001_014___2021_11_20__001<br />
 * <br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; c___mmmmm_sss___YYYY_MM_DD__dd<br />
 * &nbsp;&nbsp;e.g.&nbsp;&nbsp;                     2___00001_014___2021_11_20__01
 * <code />
 * 
 * @version {@value #ownClassVersion}
 * @author  Michael Schäfers ;  Px@Hamburg-UAS.eu  
 */
public class Version implements Serializable {
    //
    //  VERSION of "Version-class"              #---vvvvvvvvv---vvvv-vv-vv--vv
    //  =======                                 #___~version~___YYYY_MM_DD__dd_
    final static private long ownClassVersion = 2___00001_001___2021_12_10__01L;
    //                                          #---^^^^^-^^^---^^^^-^^-^^--^^
    //
    private static boolean isCodingValid( final long version ){
        final int leadingDigit = (int)( version / 1__000_000__000_000__000_000L );
        return 1<=leadingDigit && leadingDigit<=2;
    }//method(()
    //
    static {
        assert isCodingValid( ownClassVersion ) : "setup error : faulty version number coded";  // we are all humans - check that "leading one" has NOT get lost
    }//static block resp. "static initializer" ~ "class-constructor()"
    
    private static final long serialVersionUID = ownClassVersion;
    
    
    
    
    
    /**
     * The encoded version number
     */
    private final long encodedVersionNumber;
    
    
    
    /**
     * The constructor checks given version number if correctly coded and stores it
     * 
     * @param encodedVersionNumber  the encoded version number
     */
    public Version( final long encodedVersionNumber ){
        if( ! isCodingValid( encodedVersionNumber )){ throw new IllegalArgumentException( "Faulty coding of version"); }
        this.encodedVersionNumber = encodedVersionNumber;
    }//constructor()
    
    
    
    /**
     * The method {@link #getVersionNumber()} delivers the project version.
     * 
     * @return version
     */
    public long getVersionNumber(){
        return encodedVersionNumber;
    }//method()
    
    /**
     * The method {@link #getDecodedVersion()} delivers the given code version as
     * reground/readable String.
     * 
     * @return version as decoded/readable String.
     */
    public String getDecodedVersion(){
        int mainVersion = 0;
        int subVersion = 0;
        int year = 0;
        int month = 0;
        int day = 0;
        int dailyVersion = 0;
        //
        long tmp = encodedVersionNumber;
        final int leadingDigit = (int)( encodedVersionNumber / 1__000_000__000_000__000_000L );
        switch( leadingDigit ){
            //  _1___mmmm_sss___YYYY_MM_DD__ddd
            case 1:
                dailyVersion = (int)( tmp %   1_000 );
                tmp /=   1_000;
                day =          (int)( tmp %     100 );
                tmp /=     100;
                month =        (int)( tmp %     100 );
                tmp /=     100;
                year =         (int)( tmp %  10_000 );
                tmp /=  10_000;
                subVersion =   (int)( tmp %   1_000 );
                tmp /=   1_000;
                mainVersion =  (int)( tmp %  10_000 );
                tmp /=  10_000;
                assert 1 == tmp : "Uuuupppss : internal error - there (should) have been checks before";
            break;
            //
            //  _2___mmmmm_sss___YYYY_MM_DD__dd
            case 2:
                dailyVersion = (int)( tmp %     100 );
                tmp /=     100;
                day =          (int)( tmp %     100 );
                tmp /=     100;
                month =        (int)( tmp %     100 );
                tmp /=     100;
                year =         (int)( tmp %  10_000 );
                tmp /=  10_000;
                subVersion =   (int)( tmp %   1_000 );
                tmp /=   1_000;
                mainVersion =  (int)( tmp % 100_000 );
                tmp /= 100_000;
                assert 2 == tmp : "Uuuupppss : internal error - there (should) have been checks before";
            break;
            //
            //  undefined
            default:
                assert false : "Uuuupppss : internal error - there (should) have been checks before";
        }//switch
        //
        final StringBuffer sb = new StringBuffer( "" );
        sb.append( Long.toString( mainVersion ));
        sb.append( "." );
        sb.append( String.format( "%03d", subVersion ));
        sb.append( "   ( " );
        sb.append( Long.toString( year ));
        sb.append( "/" );
        sb.append( Long.toString( month ));
        sb.append( "/" );
        sb.append( Long.toString( day ));
        sb.append( " [#" );
        sb.append( Long.toString( dailyVersion ));
        sb.append( "] )" );
        return sb.toString();
    }//method()
    
}//class
