package com.guxuede.game.action.damage;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Array;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.libgdx.GdxAction;
import com.guxuede.game.tools.MathUtils;

import java.util.Iterator;

/**
 * Created by guxuede on 2017/4/5 .
 */
public class DamageAction1 extends TemporalAction {

    private final Vector2 point = new Vector2();
    private float radius;
    private float hurtPoint;



//    @Override
//    protected boolean update(float delta) {
//        Array<AnimationEntity> animationEntities = MathUtils.findClosestPointEntry(actor.getStage().getActors(),point,radius);
//        Iterator<AnimationEntity> iterator = animationEntities.iterator();
//        while (iterator.hasNext()){
//            AnimationEntity animationEntity = iterator.next();
//            animationEntity.currentHitPoint = animationEntity.currentHitPoint - hurtPoint;
//        }
//        return true;
//    }

    @Override
    protected void update(float percent) {

    }

    @Override
    public void reset() {
        super.reset();
        point.set(0,0);
        radius = 0;
        hurtPoint = 0;
    }

    public DamageAction1 setHurtPoint(float hurtPoint) {
        this.hurtPoint = hurtPoint;
        return this;
    }

    public DamageAction1 setPoint(Vector2 point) {
        this.point.set(point);
        return this;
    }

    public DamageAction1 setRadius(float radius) {
        this.radius = radius;
        return this;
    }
}
