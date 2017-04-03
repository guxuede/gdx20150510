package com.guxuede.game.action;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.guxuede.game.actor.AnimationEntity;

/**
 * Created by guxuede on 2016/5/29 .
 */
public class ActorFormulaTracksAction extends TemporalAction {

    private TracksFormula tracksFormula;

    public ActorFormulaTracksAction(){
        tracksFormula = new TracksEllipseFormula();
    }

    public ActorFormulaTracksAction(TracksFormula tracksFormula) {
        this.tracksFormula = tracksFormula;
    }

    public ActorFormulaTracksAction(float duration, TracksFormula tracksFormula) {
        super(duration);
        this.tracksFormula = tracksFormula;
    }

    public ActorFormulaTracksAction(float duration, Interpolation interpolation, TracksFormula tracksFormula) {
        super(duration, interpolation);
        this.tracksFormula = tracksFormula;
    }

    public TracksFormula getTracksFormula() {
        return tracksFormula;
    }

    public ActorFormulaTracksAction setTracksFormula(TracksFormula tracksFormula) {
        this.tracksFormula = tracksFormula;
        return this;
    }

    @Override
    protected void update(float percent) {
        AnimationEntity actor = ((AnimationEntity)target);

        float z= 100*percent*10;
        if(z > 200)z=200;

        actor.drawOffSetX = tracksFormula.getX(percent);
        actor.drawOffSetY = tracksFormula.getY(percent)+z;
    }

    protected void end () {
        AnimationEntity actor = ((AnimationEntity)target);
        actor.drawOffSetX = 0;
        actor.drawOffSetY = 0;
    }

}
