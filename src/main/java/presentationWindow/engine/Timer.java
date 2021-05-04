package presentationWindow.engine;

/**
 * contains timing methods used in the GameEngine class
 * for the loop
 */
class Timer {

    /**
     * time when init() or getElapsedTime() were called last
     */
    private double lastLoopTime;

    /**
     * Sets lastLoopTime to current Time in Seconds
     */
    void init() {
        lastLoopTime = getTime();
    }

    /**
     * Calculates current time in seconds
     *
     * @return current time in seconds
     */
    double getTime() {
        return System.nanoTime() / 1000_000_000.0;
    }

    /**
     * Calculates the time since last call of getElapsedTime() or init()
     *
     * @return elapsed Time in seconds
     */
    float getElapsedTime() {
        double time = getTime();
        float elapsedTime = (float) (time - lastLoopTime);
        lastLoopTime = time;
        return elapsedTime;
    }

    /**
     * @return time when getElapsedTime() or init() were last called in seconds
     */
    double getLastLoopTime() {
        return lastLoopTime;
    }
}
