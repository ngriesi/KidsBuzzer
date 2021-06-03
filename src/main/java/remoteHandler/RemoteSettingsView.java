package remoteHandler;

import assets.standardAssets.MyPanel;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * view to select the actions the remote should cause
 */
class RemoteSettingsView extends MyPanel {

    /**
     * view element to select the action of the top left button of the remote
     */
    private RemoteSettingsViewBlock topLeft;

    /**
     * view element to select the action of the top right button of the remote
     */
    private RemoteSettingsViewBlock topRight;

    /**
     * view element to select the action of the bottom left button of the remote
     */
    private RemoteSettingsViewBlock bottomLeft;

    /**
     * view element to select the action of the bottom right button of the remote
     */
    private RemoteSettingsViewBlock bottomRight;


    /**
     * creates Panel with Layout
     *
     * @param actionListener  <code>ActionListener</code> of all the elements in this view
     * @param possibleActions possible actions of the remote
     * @param startIndices    start indices of the selection combo boxes
     * @param keys            keys used if the key press action is selected
     */
    RemoteSettingsView(ActionListener actionListener, String[] possibleActions, int[] startIndices, int[] keys) {
        super(new GridBagLayout());

        createButtonBlocks(actionListener, possibleActions, startIndices, keys);

        VirtualRemoteView remoteView = new VirtualRemoteView();
        this.addComponent(this, remoteView, 3, 1, 1, 0, 1f, 1f);
    }

    /**
     * creates the settings blocks to set the actions of the buttons of the remote
     *
     * @param actionListener  <code>ActionListener</code> of all the elements in this view
     * @param possibleActions possible actions of the remote
     * @param startIndices    start indices of the selection combo boxes
     * @param keys            keys used if the key press action is selected
     */
    private void createButtonBlocks(ActionListener actionListener, String[] possibleActions, int[] startIndices, int[] keys) {
        topLeft = new RemoteSettingsViewBlock(actionListener, possibleActions, startIndices[0], keys[0], RemoteHandler.RemoteButton.TOP_LEFT);
        this.addComponent(this, topLeft, 0, 0, 0.1f, 1);
        topRight = new RemoteSettingsViewBlock(actionListener, possibleActions, startIndices[1], keys[1], RemoteHandler.RemoteButton.TOP_RIGHT);
        this.addComponent(this, topRight, 2, 0, 0.1f, 1);
        bottomLeft = new RemoteSettingsViewBlock(actionListener, possibleActions, startIndices[2], keys[2], RemoteHandler.RemoteButton.BOTTOM_LEFT);
        this.addComponent(this, bottomLeft, 0, 2, 0.1f, 1);
        bottomRight = new RemoteSettingsViewBlock(actionListener, possibleActions, startIndices[3], keys[3], RemoteHandler.RemoteButton.BOTTOM_RIGHT);
        this.addComponent(this, bottomRight, 2, 2, 0.1f, 1);
    }

    /**
     * makes the text input field used to enter the key for a specific button visible
     *
     * @param button button of which the text field gets visible
     */
    void changeToKeySelection(RemoteHandler.RemoteButton button) {
        getSelectedViewBlock(button).setTextFieldVisibility(true);
    }

    /**
     * returns the settings view block of a specific button
     *
     * @param button button of which the view block is returned
     * @return returns the settings view block for the given button
     */
    private RemoteSettingsViewBlock getSelectedViewBlock(RemoteHandler.RemoteButton button) {
        switch (button) {
            case TOP_LEFT:
                return topLeft;
            case TOP_RIGHT:
                return topRight;
            case BOTTOM_LEFT:
                return bottomLeft;
            case BOTTOM_RIGHT:
                return bottomRight;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * makes the text input field used to enter the key for a specific button invisible
     *
     * @param button button of which the text field gets invisible
     */
    void changeFromKeySelection(RemoteHandler.RemoteButton button) {
        getSelectedViewBlock(button).setTextFieldVisibility(false);
    }
}
