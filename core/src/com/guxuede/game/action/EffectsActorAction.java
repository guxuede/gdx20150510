package com.guxuede.game.action;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.guxuede.game.actor.ActorFactory;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.actor.EffectsEntity;
import com.guxuede.game.libgdx.GdxAction;

import static com.guxuede.game.actor.AnimationEntity.LIFE_STATUS_DEAD;
import static com.guxuede.game.actor.AnimationEntity.LIFE_STATUS_DESTROY;

/**
 * Created by guxuede on 2017/4/4 .
 */
public class EffectsActorAction extends GdxAction {

    private String actorName;
    private final Vector2 pos = new Vector2();
    private Action[] actions;
    private AnimationEntity effectsEntity;

    @Override
    protected void begin() {
        effectsEntity = ActorFactory.createEffectsActor(actorName,((AnimationEntity)actor).getWorld(),null).setCenterPosition(pos.x,pos.y).addToStage().addAllAction(actions);
    }

    @Override
    protected boolean update(float delta) {
        return true;//effectsEntity.LS(LIFE_STATUS_DEAD|LIFE_STATUS_DESTROY);
    }

    @Override
    public void reset() {
        super.reset();
        actorName = null;
        pos.set(0,0);
        actions = null;
        effectsEntity = null;
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
