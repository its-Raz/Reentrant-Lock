public class IllegalReleaseAttempt extends IllegalMonitorStateException{
    /**
     * Constructs an <code>IllegalMonitorStateException</code> with no
     * detail message.
     */
    public IllegalReleaseAttempt() {
    }

    /**
     * Constructs an <code>IllegalMonitorStateException</code> with the
     * specified detail message.
     *
     * @param s the detail message.
     */
    public IllegalReleaseAttempt(String s) {
        super(s);
    }
}
