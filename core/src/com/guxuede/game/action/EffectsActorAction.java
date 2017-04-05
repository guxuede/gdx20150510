package com.guxuede.game.action;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.guxuede.game.actor.ActorFactory;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.libgdx.GdxAction;

/**
 * Created by guxuede on 2017/4/4 .
 */
public class EffectsActorAction extends GdxAction {

    private String actorName;
    private final Vector2 pos = new Vector2();
    private Action[] actions;

    @Override
    protected boolean update(float delta) {
        ActorFactory.createEffectsActor(actorName,((AnimationEntity)actor).getWorld(),null).setCenterPosition(pos.x,pos.y).addToStage().addAllAction(actions);
        return true;
    }

    @Override
    public void reset() {
        super.reset();
        actorName = null;
        pos.set(0,0);
        actions = null;
    }


    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public void setPos(Vector2 pos) {
        this.pos.set(pos);
    }

    public void setActions(Action... actions) {
        this.actions = actions;
    }
}
