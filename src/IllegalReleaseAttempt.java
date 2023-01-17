public class IllegalReleaseAttempt extends IllegalMonitorStateException{
    /**
     * Constructs an IllegalReleaseAttempt with no
     * detail message.
     */
    public IllegalReleaseAttempt() {
    }

    /**
     * Constructs an IllegalReleaseAttempt with the
     * specified detail message.
     *
     * @param s the detail message.
     */
    public IllegalReleaseAttempt(String s) {
        super(s);
    }
}
