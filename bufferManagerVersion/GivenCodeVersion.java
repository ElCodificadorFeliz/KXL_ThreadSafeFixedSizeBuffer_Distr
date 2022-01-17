// This source code is UTF-8 coded - see https://stackoverflow.com/questions/9180981/how-to-support-utf-8-encoding-in-eclipse
package bufferManagerVersion;


import java.io.Serializable;

import untouchableSupportStuff.Version;


/**
 * The single purpose of this class is to hold the &quot;project version&quot;.
 * Since the code/project is an exercise, it's named {@link GivenCodeVersion}.
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
 * @version {@value #encodedVersion}
 * @author  Michael Schäfers ;  Px@Hamburg-UAS.eu  
 */
public class GivenCodeVersion implements Serializable {
    //
    //--VERSION:-------------------------------#---vvvvvvvvv---vvvv-vv-vv--vv
    //  ========                               #___~version~___YYYY_MM_DD__dd_
    final static private long encodedVersion = 2___00001_002___2022_01_17__02L;
    //-----------------------------------------#---^^^^^-^^^---^^^^-^^-^^--^^
    final static private Version version = new Version( encodedVersion );
    //
    static final private long serialVersionUID = version.getVersionNumber();
    
    
    
    
    
    /**
     * The method {@link #getVersionNumber()} delivers the given project version.
     * 
     * @return version
     */
    static public long getVersionNumber(){
        return version.getVersionNumber();
    }//method()
    
    /**
     * The method {@link #getDecodedVersion()} delivers the given project version as reground/readable String.
     * 
     * @return version as decoded/readable String.
     */
    static public String getDecodedVersion(){
        return version.getDecodedVersion();
    }//method()
    
}//class
