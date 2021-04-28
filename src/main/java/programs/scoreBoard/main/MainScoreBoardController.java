package programs.scoreBoard.main;

import programs.quiztime.main.view.AnimationQueue;

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
        animationQueueItem.addOnFinishedAction(() -> program.getControlModel().hidePresentationWindow());
        animationQueue.addAnimation(animationQueueItem);
    }

    public void show() {
        AnimationQueue.AnimationQueueItem animationQueueItem = new AnimationQueue.AnimationQueueItem();
        animationQueueItem.setAnimationAction(() -> {
            program.getControlModel().showPresentationWindow();
            program.getProgramPresentationView().enterAnimation(animationQueueItem);
        });
        animationQueue.addAnimation(animationQueueItem);
    }

    public void buzzerPressed(int buzzer) {
        program.getProgramModel().getScores()[buzzer - 1]++;
        program.getProgramController().updateScores();
        AnimationQueue.AnimationQueueItem animationQueueItem = new AnimationQueue.AnimationQueueItem();
        animationQueueItem.setAnimationAction(() -> program.getProgramPresentationView().buzzerAnimation(animationQueueItem, buzzer));
        animationQueueItem.addOnFinishedAction(this::buzzerAnimationFinished);
        animationQueue.addAnimation(animationQueueItem);

    }

    private void buzzerAnimationFinished() {
        program.getControlModel().getBuzzerControl().setBlockAllBuzzer(false);
    }

    public void setBuzzerScore(int buzzer, int score) {
        program.getProgramModel().getScores()[buzzer - 1] = score;
        program.getRenderer().addActionToOpenGlThread(() -> program.getProgramPresentationView().setBuzzerScore(buzzer, score));
    }
}
