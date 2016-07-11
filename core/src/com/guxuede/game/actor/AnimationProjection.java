package com.guxuede.game.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.guxuede.game.animation.ActionsFactory;
import com.guxuede.game.effects.AnimationEffect;
import com.guxuede.game.libgdx.ResourceManager;
import com.guxuede.game.resource.ActorAnimationPlayer;

import java.util.HashMap;
import java.util.Map;

public class AnimationProjection extends AnimationEntity {


    public AnimationProjection(ActorAnimationPlayer animationPlayer, World world, InputListener l) {
        super(animationPlayer, world, l);
        init();
    }

    public AnimationProjection(ActorAnimationPlayer animationPlayer, World world) {
        super(animationPlayer, world);
        init();
    }



    public void init(){
		this.scaleBy(1);
		speed = 50000;
	}
	
	@Override
	public void createBody(World world) {
        if(lifeStatus == LIFE_STATUS_CREATE) {
            lifeStatus = LIFE_STATUS_LIVE;
            int actorWidth = animationPlayer.width;
            this.setVisible(true);
            /**********************************box2d************************************************/
            //http://www.firedragonpzy.com.cn/index.php/archives/2524
            BodyDef bd = new BodyDef();
            bd.type = BodyDef.BodyType.DynamicBody;
            bd.position.set(getEntityX(), getEntityY());

            CircleShape c = new CircleShape();
            c.setRadius(actorWidth / 3);
            FixtureDef ballShapeDef = new FixtureDef();
            ballShapeDef.density = 1.0f;//密度
            ballShapeDef.friction = 1f;////摩擦粗糙程度
            ballShapeDef.restitution = 0.0f;//碰撞后，恢复原状后的力量,力度返回程度（弹性）
            ballShapeDef.shape = c;//形状
            ballShapeDef.isSensor = true;//当isSensor为false时(这也是默认值)，在发生碰撞后，由Box2D模拟物理碰撞后的反弹或变向运动。当isSensor是true时，刚体只进行碰撞检测，而不模拟碰撞后的物理运动。此时，我们就可以自定义刚体处理方式了，如示例中的绕小圆运动。
            body = world.createBody(bd);
            body.createFixture(ballShapeDef);
            body.setFixedRotation(true);//固定旋转标记把转动惯量逐渐设置成零。
            body.setLinearDamping(100);//阻尼，阻尼用来降低世界中物体的速度。阻尼和摩擦不同，因为摩擦仅仅和接触同时发生。阻尼不是摩擦的一个替代者，并且这两个效果可以被同时使用。
            body.setAngularDamping(100);//瑙掗樆灏�鎽╂摝?
            body.setUserData(this);
            this.body.setBullet(true);
            c.dispose();
        }
	}
	
	@Override
	public void turnDirection(float degrees) {
		super.turnDirection(degrees);
		//this.setRotation(degrees+90);
	}

    public void hit(AnimationEntity entity,Vector2 vector2){
        doShowDamageEffect(vector2, entity);
        ResourceManager.sound.play();
    }


    public void doShowDamageEffect(Vector2 vector2, Actor actor) {
        BarrageTip tip = new BarrageTip("-1", vector2.x, vector2.y);
        tip.setZIndex(Integer.MAX_VALUE);
        getStage().addActor(tip);

        if (actor != null) {
            actor.addAction(
                    ActionsFactory.sequence(
                            ActionsFactory.color(Color.RED, 0.1f),
                            ActionsFactory.color(Color.PINK, 0.1f),
                            ActionsFactory.color(new Color(1, 1, 1, 1), 0.1f)
                    )
            );
            AnimationEffect effect = new AnimationEffect();
            effect.setEffectAnimation(ResourceManager.getAnimationHolder("special10").getStopDownAnimation());
            actor.addAction(effect);
        }
    }
}