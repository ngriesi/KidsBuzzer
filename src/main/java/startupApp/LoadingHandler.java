package startupApp;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * handles the loading processes
 */
public class LoadingHandler {

    /**
     * tracks the number of stated loading processes
     */
    private int startedLoadingProcesses;

    /**
     * tracks the number of finished loading processes
     */
    private int finishedLoadingProcesses;

    /**
     * stores the finished process names
     */
    private ArrayList<String> finishedProcessNamesBuffer;

    /**
     * stores the current loading processes for debugging purposes
     */
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private CopyOnWriteArrayList<LoadingMonitor> loadingProcesses;

    /**
     * creates a new loading handler, creates the lists
     */
    public LoadingHandler() {
        finishedProcessNamesBuffer = new ArrayList<>();
        loadingProcesses = new CopyOnWriteArrayList<>();
    }

    /**
     * action performed when a process is finished
     *
     * @param loadingProcess finished process
     */
    synchronized void processFinished(LoadingMonitor loadingProcess) {
        finishedLoadingProcesses++;
        addNameToBuffer(loadingProcess.getProcessName());
        loadingProcesses.remove(loadingProcess);
    }

    /**
     * adds a loading process to this handler
     *
     * @param loadingProcess loading process to be added
     */
    public synchronized void addLoadingProcess(LoadingMonitor loadingProcess) {
        startedLoadingProcesses++;
        loadingProcesses.add(loadingProcess);
    }

    /**
     * adds a name to the finished process names buffer
     *
     * @param name name to be added
     */
    private synchronized void addNameToBuffer(String name) {
        finishedProcessNamesBuffer.add(name);
    }

    /**
     * @return returns the loading process
     */
    float getProgress() {
        return (float) finishedLoadingProcesses / (float) startedLoadingProcesses;
    }

    /**
     * @return returns the content of the finished processes buffer
     */
    synchronized String[] getCurrentBufferContent() {
        String[] content = new String[finishedProcessNamesBuffer.size()];
        for(int i = 0; i < content.length; i++) {
            content[i] = finishedProcessNamesBuffer.get(i);
        }
        finishedProcessNamesBuffer.clear();
        return content;
    }

    /**
     * @return returns the currently running loading processes
     */
    public CopyOnWriteArrayList<LoadingMonitor> getLoadingProcesses() {
        return loadingProcesses;
    }
}
