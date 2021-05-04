package programs.scoreBoard.main;


import presentationWindow.animations.AnimationQueue;

public class MainScoreBoardController {

    private ScoreBoardProgram program;

    private AnimationQueue animationQueue;

    MainScoreBoardController(ScoreBoardProgram program) {
        this.program = program;
        animationQueue = new AnimationQueue();
    }


    public void hide() {
        AnimationQueue.AnimationQueueItem animationQueueItem = new AnimationQueue.AnimationQueueItem();
        animationQueueItem.setAnimationAction(() -> program.getProgramPresentationView().exitAnimation(animationQueueItem));
        animationQueueItem.addOnFinishedAction(() -> program.getMainController().hidePresentationWindow());
        animationQueue.addAnimation(animationQueueItem);
    }

    public void show() {
        AnimationQueue.AnimationQueueItem animationQueueItem = new AnimationQueue.AnimationQueueItem();
        animationQueueItem.setAnimationAction(() -> {
            program.getMainController().showPresentationWindow();
            program.getProgramPresentationView().enterAnimation(animationQueueItem);
        });
        animationQueue.addAnimation(animationQueueItem);
    }

    void buzzerPressed(int buzzer) {
        program.getProgramModel().getScores()[buzzer - 1]++;
        program.getProgramController().updateScores();
        AnimationQueue.AnimationQueueItem animationQueueItem = new AnimationQueue.AnimationQueueItem();
        animationQueueItem.setAnimationAction(() -> program.getProgramPresentationView().buzzerAnimation(animationQueueItem, buzzer));
        animationQueueItem.addOnFinishedAction(this::buzzerAnimationFinished);
        animationQueue.addAnimation(animationQueueItem);

    }

    private void buzzerAnimationFinished() {
        program.getMainController().getControlModel().getBuzzerControl().setBlockAllBuzzer(false);
    }

    public void setBuzzerScore(int buzzer, int score) {
        program.getProgramModel().getScores()[buzzer - 1] = score;
        program.getRenderer().addActionToOpenGlThread(() -> program.getProgramPresentationView().setBuzzerScore(buzzer, score));
    }
}
