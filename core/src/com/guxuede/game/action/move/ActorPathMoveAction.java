
package com.guxuede.game.action.move;

import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.libgdx.GdxAction;
import com.guxuede.game.tools.MathUtils;
import com.guxuede.game.tools.TempObjects;

public class ActorPathMoveAction extends TemporalAction {
    private Bezier<Vector2> bezier;
    private Vector2 targetPoint = new Vector2();
    private static final float duration = 2;

    public ActorPathMoveAction(float x, float y){
        targetPoint.set(x,y);
        this.setDuration(duration);
    }

    @Override
    protected void begin() {
        final AnimationEntity entity = (AnimationEntity) getTarget();
        Vector2 startPoint = entity.getPhysicsPosition().cpy();
        Vector2 endPoint = targetPoint;
        Vector2 v = TempObjects.temp0Vector2.set(entity.getPhysicsPosition()).sub(targetPoint);
        bezier = new Bezier<Vector2>(
                startPoint,
                new Vector2(startPoint.x+(endPoint.x-startPoint.x)*0.95f, startPoint.y+(endPoint.y-startPoint.y)*-0.31f),
                new Vector2(startPoint.x+(endPoint.x-startPoint.x)*0.00f, startPoint.y+(endPoint.y-startPoint.y)*1.14f),
                endPoint);
    }


    @Override
    protected void update(float percent) {
        final AnimationEntity entity = (AnimationEntity) getTarget();
        bezier.valueAt(TempObjects.temp0Vector2, percent);
        entity.setPhysicsPosition(TempObjects.temp0Vector2.x,TempObjects.temp0Vector2.y);
        float an = bezier.derivativeAt(TempObjects.temp0Vector2,percent).angle();
        entity.turnDirection(an);
        entity.doMoveAnimation();
    }

    @Override
    protected void end() {
        final AnimationEntity entity = (AnimationEntity) getTarget();
        entity.stop();
        entity.collisionSize = 99;
    }

    @Override
	public void reset () {
		super.reset();
	}

	public void setPosition (float x, float y) {
        targetPoint.set(x,y);
	}


}
