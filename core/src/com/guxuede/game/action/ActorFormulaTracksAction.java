package com.guxuede.game.action;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.guxuede.game.actor.AnimationEntity;

/**
 * Created by guxuede on 2016/5/29 .
 */
public class ActorFormulaTracksAction extends TemporalAction {

    private TracksFormula tracksFormula = new TracksEllipseFormula();

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
