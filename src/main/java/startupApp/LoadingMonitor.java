package startupApp;

/**
 * class monitors a loading process
 */
public class LoadingMonitor {

    /**
     * name of the process
     */
    private String processName;

    /**
     * creates a new loading process monitor
     *
     * @param processName name of the loading process
     */
    public LoadingMonitor(String processName) {
        this.processName = processName;
    }

    /**
     * notifies the loading handler that this loading process is finished
     *
     * @param loadingHandler loading handler this process belongs to
     */
    public void finishedProcess(LoadingHandler loadingHandler) {
        loadingHandler.processFinished(this);
    }

    /**
     * @return returns the name of the process
     */
    String getProcessName() {
        return processName;
    }
}
