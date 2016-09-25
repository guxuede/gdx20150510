package com.guxuede.game.action;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.resource.ResourceManager;
import com.guxuede.game.resource.AnimationHolder;

/**
 * Created by guxuede on 2016/5/28 .
 */
public class ActorChangeAppearanceAction extends TemporalAction {

    AnimationHolder oldAnimationHolder;

    @Override
    protected void update(float percent) {
        //System.out.println(percent);
    }

    protected void begin () {
        AnimationEntity actor = ((AnimationEntity)target);
        oldAnimationHolder = actor.animationPlayer.animationHolder;
        actor.animationPlayer.setAnimationHolder(ResourceManager.getAnimationHolder("chicken"));
    }

    protected void end () {
        AnimationEntity actor = ((AnimationEntity)target);
        actor.animationPlayer.setAnimationHolder(oldAnimationHolder);
    }

    @Override
    public void reset() {
        super.reset();
        oldAnimationHolder = null;
    }
}
