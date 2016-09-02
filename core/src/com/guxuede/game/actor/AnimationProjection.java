package com.guxuede.game.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.guxuede.game.animation.ActionsFactory;
import com.guxuede.game.effects.AnimationEffect;
import com.guxuede.game.libgdx.ResourceManager;
import com.guxuede.game.resource.ActorAnimationPlayer;
import com.guxuede.game.tools.SoundUtils;

public class AnimationProjection extends AnimationEntity {

    protected boolean trunDirectionWhenMove = true;

    long soundId=-1;

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
		speed = 50000000;
        setZIndex(2);
        soundId = ResourceManager.sound_fire_flying.play();
        ResourceManager.sound_fire_flying.setLooping(soundId,true);
        //ResourceManager.sound_fire_flying.setPan(soundId, -0.5f, 1f);
	}
	
	@Override
	public void createBody(World world) {
        if(lifeStatus == LIFE_STATUS_CREATE) {
            lifeStatus = LIFE_STATUS_BORN;
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
        if(lifeStatus == LIFE_STATUS_BORN){
            lifeStatus = LIFE_STATUS_LIVE;
        }
	}
	
	@Override
	public void turnDirection(float degrees) {
		super.turnDirection(degrees);
        if(trunDirectionWhenMove){
            this.setRotation(degrees+90);
        }
	}

    @Override
    public void act(float delta) {
        super.act(delta);
        SoundUtils.set3dPan(ResourceManager.sound_fire_flying,soundId,getX(),getY(),getStage().getCamera());
    }

    /**
     * 撞击
     * @param entity 撞击对象
     * @param position 撞击位置
     */
    public void hit(AnimationEntity entity,Vector2 position){
        doShowDamageEffect(entity,position);
    }


    public void doShowDamageEffect(AnimationEntity entity,Vector2 position) {
        BarrageTip tip = new BarrageTip("-1", position.x, position.y);
        tip.setZIndex(Integer.MAX_VALUE);
        getStage().addActor(tip);

        if (entity != null) {
            SoundUtils.play(ResourceManager.sound_hited,this);
            if(entity.equals(this)){
                entity.dead();
            }else{
                entity.addAction(
                        ActionsFactory.sequence(
                                ActionsFactory.color(Color.RED, 0.1f),
                                ActionsFactory.color(Color.PINK, 0.1f),
                                ActionsFactory.color(new Color(1, 1, 1, 1), 0.1f)
                        )
                );
                AnimationEffect effect = new AnimationEffect();
                effect.setEffectAnimation(ResourceManager.getAnimationHolder("attack1").getStopDownAnimation());
                entity.addAction(effect);
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        ResourceManager.sound_fire_flying.setLooping(soundId,false);
    }
}