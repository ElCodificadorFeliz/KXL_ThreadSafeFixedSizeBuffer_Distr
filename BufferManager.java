package about_11to12_FixedSizeThreadSafeBufferManager_Distr;


/*
 *------------------------------------------------------------------------------
 * VCS: git@BitBucket.org:schaefers/Prg_Px_K-L_FixedSizeThreadSafeBufferManager_Distr[.git]
 * For further information see ReadMe.txt
 *                                                Michael Schaefers  2017/06/06
 *------------------------------------------------------------------------------
 */
/**
 * Ein BufferMannager verwaltet einen Puffer konstanter Gr&ouml;&szlig;e.
 * Die Gr&ouml;&szlig;e bzw. (Aufnahme-)Kapazit&auml;t wird dem (zu implementierenden) Konstruktor als Parameter mitgegeben.
 * Die Gr&ouml;&szlig;e bzw. (Aufnahme-)Kapazit&auml;t ist konstant und kann f&uuml;r nicht ver&auml;ndert werden.
 * Die im Puffer enthaltenen Datens&auml;tze d&uuml;rfen <u>nicht</u> <b><code>null</code></b> sein.
 */
public interface BufferManager<T> {
    
    /**
     * F&uuml;gt den als Parameter &uuml;bergebenen Datensatz in den Puffer ein.
     * Sofern kein Platz im Puffer vorhanden ist, wird blockierend gewartet bis Platz verf&uuml;gbar ist.
     * 
     * @param data - der einzuf&uuml;gende Datensatz
     * 
     * @throws InterruptedException falls der als Parameter &uuml;bergebene Datensatz <b><code>null</code></b> ist.
     * @throws NullPointerException falls eine Unterbrechung per Interrupt beim blockierenden Warten erfolgt.
     */
    public abstract void insert( final T data ) throws InterruptedException;
    
    /**
     * Liefert und entfernt den &auml;testen Datensatz im Puffer.
     * Sofern kein Datensatz im Puffer vorhanden ist, wird blockierend gewartet bis ein Datensatz verf&uuml;gbar ist.
     * 
     * @return der &auml;lteste Datensatz im Puffer
     * 
     * @throws InterruptedException falls der als Parameter &uuml;bergebene Datensatz <b><code>null</code></b> ist.
     */
    public abstract T remove() throws InterruptedException;
    
    /**
     * Liefert den bereits verbrauchten Platz im Puffer bzw. die Anzahl Datens&auml;tze, die im Puffer enthalten sind. 
     * 
     * @return die Anzahl Datens&auml;tze, die im Puffer enthalten sind
     */
    public abstract int getUsage();
    
    /**
     * Liefert den noch zur Verf&uuml;gung stehenden Platz im Puffer bzw. die Anzahl Datens&auml;tze, die der Puffer noch aufnehmen kann. 
     * 
     * @return die Anzahl Datens&auml;tze, die der Puffer noch aufnehmen kann
     */
    public abstract int getRemainingCapacity();
    
    /**
     * Liefert den zur Verf&uuml;gung stehenden Platz im Puffer bzw. die macimale Anzahl Datens&auml;tze, die der Puffer aufnehmen kann. 
     * 
     * @return die Anzahl Datens&auml;tze, die der Puffer maximal aufnehmen kann
     */
    public abstract int getCapacity();
    
}//interface
