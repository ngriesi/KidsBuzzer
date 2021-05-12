package programs.abstractProgram;

import java.awt.event.ActionListener;

/**
 * controller for the views of the program that are placed in the control window
 *
 * @param <P> program this belongs to
 * @param <V> view that belongs to this
 * @param <M> model of the program
 */
@SuppressWarnings("unused")
public abstract class ProgramController<P extends Program, V extends ProgramView, M extends ProgramModel> implements ActionListener {

    /**
     * program this controller belongs to
     */
    private P program;

    /**
     * view that belongs to this controller
     */
    private V programView;

    /**
     * model that belongs to this class
     */
    private M programModel;


    /**
     * creates a new controller
     *
     * @param program      program this controller belongs to
     * @param programModel model of this program
     */
    public ProgramController(P program, M programModel) {
        this.program = program;
        this.programModel = programModel;
        this.programView = createView();
        getProgramModel().addChangeListener(this::updateView);
    }

    /**
     * abstract creation method
     *
     * @return returns the created view of the controller
     */
    protected abstract V createView();

    /**
     * abstract update method
     */
    protected abstract void updateView();

    /**
     * @return returns the program of this controller
     */
    public P getProgram() {
        return program;
    }

    /**
     * sets the program of this controller
     *
     * @param program new program for this controller
     */
    public void setProgram(P program) {
        this.program = program;
    }

    /**
     * @return returns the view of this controller
     */
    public V getProgramView() {
        return programView;
    }

    /**
     * @return returns the model of this program
     */
    public M getProgramModel() {
        return programModel;
    }

    /**
     * sets the model of this program
     *
     * @param programModel new program model
     */
    public void setProgramModel(M programModel) {
        this.programModel = programModel;
    }
}
