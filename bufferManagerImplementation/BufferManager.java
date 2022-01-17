// This source code is UTF-8 coded - see https://stackoverflow.com/questions/9180981/how-to-support-utf-8-encoding-in-eclipse
package bufferManagerImplementation;


/**
 * Ein BufferMannager verwaltet einen Puffer konstanter Größe.
 * Die Größe bzw. (Aufnahme-)Kapazität wird dem (zu implementierenden) Konstruktor als Parameter mitgegeben.
 * Die Größe bzw. (Aufnahme-)Kapazität ist konstant und kann für nicht verändert werden.
 * Die im Puffer enthaltenen Datensätze dürfen <u>nicht</u> <b><code>null</code></b> sein.
 * 
 * @version {@value #encodedInterfaceVersion}
 * @author  Michael Schäfers ;  P2@Hamburg-UAS.eu  
 */
public interface BufferManager<T> {
    //
    //--VERSION:--------------------------------#---vvvvvvvvv---vvvv-vv-vv--vv
    //  ========                                #___~version~___YYYY_MM_DD__dd_
    final static long encodedInterfaceVersion = 2___00001_001___2022_01_16__01L;
    //------------------------------------------#---^^^^^-^^^---^^^^-^^-^^--^^
    
    
    
    /**
     * Fügt den als Parameter übergebenen Datensatz in den Puffer ein.
     * Sofern kein Platz im Puffer vorhanden ist, wird blockierend gewartet bis Platz verfügbar ist.
     * 
     * @param data - der einzufügende Datensatz
     * 
     * @throws InterruptedException falls eine Unterbrechung per Interrupt beim blockierenden Warten erfolgt.
     * @throws NullPointerException falls der als Parameter übergebene Datensatz <b><code>null</code></b> ist.
     */
    public abstract void insert( final T data ) throws InterruptedException;
    
    /**
     * Liefert und entfernt den ältesten Datensatz im Puffer.
     * Sofern kein Datensatz im Puffer vorhanden ist, wird blockierend gewartet bis ein Datensatz verfügbar ist.
     * 
     * @return der älteste Datensatz im Puffer
     * 
     * @throws InterruptedException falls eine Unterbrechung per Interrupt beim blockierenden Warten erfolgt.
     */
    public abstract T remove() throws InterruptedException;
    
    /**
     * Liefert den bereits verbrauchten Platz im Puffer bzw. die Anzahl Datensätze, die im Puffer enthalten sind. 
     * 
     * @return die Anzahl Datensätze, die im Puffer enthalten sind
     */
    public abstract int getUsage();
    
    /**
     * Liefert den noch zur Verfügung stehenden Platz im Puffer bzw. die Anzahl Datensätze, die der Puffer noch aufnehmen kann. 
     * 
     * @return die Anzahl Datensätze, die der Puffer noch aufnehmen kann
     */
    public abstract int getRemainingCapacity();
    
    /**
     * Liefert den zur Verfügung stehenden Platz im Puffer bzw. die maximale Anzahl Datensätze, die der Puffer aufnehmen kann. 
     * 
     * @return die Anzahl Datensätze, die der Puffer maximal aufnehmen kann
     */
    public abstract int getCapacity();
    
}//interface
