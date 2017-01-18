package com.guxuede.game.action;

import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.guxuede.game.action.effects.AnimationEffect;
import com.guxuede.game.actor.ActorFactory;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.actor.EffectsEntity;
import com.guxuede.game.libgdx.GdxAction;
import com.guxuede.game.tools.TempObjects;

/**
 * Created by guxuede on 2016/11/2 .
 */
public class ActorJumpAction1 extends TemporalAction {
    public static final float SCALE = 0.5F;
    public static final float HEIGHT = 100F;

    private float speed = 100;

    private Bezier<Vector2> bezier;
    Vector2 targetPoint = new Vector2();

    public ActorJumpAction1(Vector2 targetPoint) {
        this(targetPoint.x,targetPoint.y);
    }
    public ActorJumpAction1(float x, float y){
        targetPoint.set(x,y);
//        final AnimationEntity entity = (AnimationEntity) getTarget();
//        Vector2 startPoint = entity.getPhysicsPosition().cpy();
//        Vector2 endPoint = targetPoint;
//        float duration = 2;//startPoint.dst(endPoint)/speed;
//        this.setDuration(duration);
//        target.addAction(ActionsFactory.sequence(ActionsFactory.scaleTo(2,2,duration*0.2f, Interpolation.exp10)
//                ,ActionsFactory.delay(duration*0.6f)
//        ,ActionsFactory.scaleTo(-2,-2,duration*0.2f, Interpolation.exp10)));
    }

    @Override
    protected void begin() {


        final AnimationEntity entity = (AnimationEntity) getTarget();
        Vector2 startPoint = entity.getPhysicsPosition().cpy();
        Vector2 endPoint = targetPoint;
        Vector2 v = TempObjects.temp0Vector2.set(entity.getPhysicsPosition()).sub(targetPoint);
        bezier = new Bezier<Vector2>(
                startPoint,
                new Vector2(startPoint.x+(endPoint.x-startPoint.x)*0.00f, startPoint.y+(endPoint.y-startPoint.y)*0.00f),
                new Vector2(startPoint.x+(endPoint.x-startPoint.x)*1.00f, startPoint.y+(endPoint.y-startPoint.y)*1),
                endPoint);

        float duration = startPoint.dst(endPoint)/speed;
        this.setDuration(duration);
        target.addAction(ActionsFactory.sequence(
                ActionsFactory.scaleBy(SCALE,SCALE,duration*0.5f,Interpolation.circleOut)
                //,ActionsFactory.delay(duration*0.2f)
                ,ActionsFactory.scaleBy(-SCALE,-SCALE,duration*0.5f,Interpolation.circleIn)));
        target.addAction(ActionsFactory.sequence(
                new ActorZAction(HEIGHT,duration*0.2f,Interpolation.circleOut)
                //,ActionsFactory.delay(duration*0.2f)
                ,new ActorZAction(-HEIGHT,duration*0.8f,Interpolation.linear)));
        target.addAction(ActionsFactory.sequence(
                ActionsFactory.delay(duration)
                , new Action() {
                    @Override
                    public boolean act(float delta) {
                        EffectsEntity effectsEntity = ActorFactory.createEffectsActor("crack",entity.getWorld(),2f);
                        effectsEntity.drawZIndex  = -1;
                        effectsEntity.setRotation(MathUtils.random(360f));
                        effectsEntity.setCenterPosition(targetPoint.x,targetPoint.y - effectsEntity.getHeight()/2);
                        effectsEntity.addAction(ActionsFactory.sequence(ActionsFactory.fadeIn(1),ActionsFactory.sequence(ActionsFactory.fadeOut(1))));
                        entity.addAction(new AnimationEffect("heal7"));
                        entity.getWorld().getStage().addActor(effectsEntity);
                        return true;
                    }
                }));
    }

    @Override
    protected void update(float percent) {
        final AnimationEntity entity = (AnimationEntity) getTarget();
        bezier.valueAt(TempObjects.temp0Vector2, percent);
        entity.setPhysicsPosition(TempObjects.temp0Vector2.x,TempObjects.temp0Vector2.y);
        float an = bezier.derivativeAt(TempObjects.temp0Vector2,percent*0.999f).angle();//*0.999是为了避免，100%时，an = 0 的情况
        entity.turnDirection(an);
        entity.doMoveAnimation();
    }

    @Override
    protected void end() {
        final AnimationEntity entity = (AnimationEntity) getTarget();
        entity.stop();
        entity.collisionSize = 99;
    }
}
