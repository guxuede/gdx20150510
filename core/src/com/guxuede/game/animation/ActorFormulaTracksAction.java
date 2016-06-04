package com.guxuede.game.animation;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.guxuede.game.actor.AnimationEntity;

import java.util.Random;

/**
 * Created by guxuede on 2016/5/29 .
 */
public class ActorFormulaTracksAction extends TemporalAction {

    private TracksFormula tracksFormula = new TracksEllipseFormula();

    @Override
    protected void update(float percent) {
        AnimationEntity actor = ((AnimationEntity)target);
        System.out.println(360*percent);

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
