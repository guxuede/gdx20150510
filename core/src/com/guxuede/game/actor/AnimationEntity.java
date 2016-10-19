package com.guxuede.game.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.guxuede.game.StageWorld;
import com.guxuede.game.action.move.ActorMoveToPathAction;
import com.guxuede.game.actor.ability.skill.HackSkill;
import com.guxuede.game.actor.ability.skill.JumpSkill;
import com.guxuede.game.actor.ability.skill.MagicSkill;
import com.guxuede.game.actor.ability.skill.Skill;
import com.guxuede.game.actor.state.ActorState;
import com.guxuede.game.actor.state.StandState;
import com.guxuede.game.action.ActionsFactory;
import com.guxuede.game.action.ActorAlwayMoveAction;
import com.guxuede.game.action.move.ActorMoveToAction;
import com.guxuede.game.action.move.ActorMoveToActorAction;
import com.guxuede.game.libgdx.GdxSprite;
import com.guxuede.game.resource.ResourceManager;
import com.guxuede.game.physics.PhysicsPlayer;
import com.guxuede.game.resource.ActorAnimationPlayer;

import java.util.ArrayList;
import java.util.List;

public abstract class AnimationEntity extends LevelDrawActor implements Poolable{

	public static final int STOP=0, DOWN=1,LEFT=2,RIGHT=3,UP=4;
	public static final int         //实体状态
            LIFE_STATUS_CREATE=0, //实体处于创建创建状态，还没有进入世界，系统将不久后初始化它，并进入LIFE_STATUS_BORN
            LIFE_STATUS_BORN=1,//实体处于诞生状态，进入世界，诞生状态的实体，无敌不可攻击，并有诞生动画，诞生完成后进入LIFE_STATUS_LIVE
            LIFE_STATUS_LIVE=2,//实体处于正常活动状态，参与世界的任何事件，可以被控制，可以被攻击
            LIFE_STATUS_DEAD=3,//实体处于死亡状态，无敌不可攻击不可控制，并有死亡动画，死亡完成后进入LIFE_STATUS_DESTORY
            LIFE_STATUS_DESTORY=-1;//实体处于摧毁状态，退出世界之外，系统将不久销毁它，内存空间将清理

	public static long ID = 1000000000001L;
    public AnimationEntity sourceActor;//源单位，此单位可能是宠物，寄主，生产者
    public long id;
    public int direction=DOWN;
    public float degrees;
    public float speed=1200000;
    public boolean isMoving;
    public float visualRadius=100;
    public Color  primaryColor;
    public int lifeStatus = LIFE_STATUS_CREATE;
    public boolean isSensor = false;
    public boolean isHover = false;
    public float collisionSize = 0;
    public float hitPoint = 100;
    public float currentHitPoint = 100;
    public ActorAnimationPlayer animationPlayer;
    /******************bellow attribute not share with other stage *****************/
    public StageWorld stageWorld;
    private PhysicsPlayer physicsPlayer;
    /******************bellow attribute not share with other stage end *****************/

    public List<Skill> skills = new ArrayList<Skill>();

    public AnimationEntity(ActorAnimationPlayer animationPlayer,StageWorld world) {
        id = ID++;
        this.animationPlayer = animationPlayer;
        int actorWidth= animationPlayer.width;
        int actorHeight= animationPlayer.height;
        this.collisionSize = 14;//actorWidth/4;
        this.setSize(actorWidth, actorHeight);
        this.setOrigin(Align.center);
        this.setVisible(true);
        this.lifeStatus = LIFE_STATUS_CREATE;
        this.addAction(new ActorAlwayMoveAction());
        this.visualRadius = actorWidth * 4;
        this.primaryColor = new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1);
        this.physicsPlayer = world.getPhysicsManager().createPositionPlayer();
        this.stageWorld = world;
        skills.add(new HackSkill());
        skills.add(new MagicSkill());
        skills.add(new JumpSkill());
    }

    //=============================================Position Control========================================================================

    /**
     * 一下三个方法不要再改单位初始化时调用
     * @param actorLinearVelocity
     */
    public void setLinearVelocity(Vector2 actorLinearVelocity) {
        this.physicsPlayer.setLinearVelocity(actorLinearVelocity);
    }

    public void setPhysicsPosition(float x, float y){
        this.physicsPlayer.setXY(x,y);
    }
    /*得到物理位置*/
    public Vector2 getPhysicsPosition(){
        return physicsPlayer.getXY();
    }
	@Override
	public void moveBy(float x, float y) {
		if (x != 0 || y != 0) {
			physicsPlayer.setXY(physicsPlayer.getX() + x, physicsPlayer.getY() + y);
			positionChanged();
		}
	}

    /**
     * 禁止！在外面调用下面三个方法。这些方法被内部位置系统positionPlayer无数次的调用用来同步actor位置，外部调用无用。
     * 仅初始状态可调用来调整位置.
     * 如需实时改变位置请使用 setPhysicsPosition。
     *  @see AnimationEntity setCenterPosition
     *  @see PhysicsPlayer act()
     * @param x
     * @param y
     */
    @Deprecated
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
	}
    @Deprecated
    @Override
    public void setPosition(float x, float y, int alignment) {
        super.setPosition(x, y, alignment);
    }

    /**仅建议在初始化对象时调用一次*/
    public void setCenterPosition(float x, float y) {
        this.setPosition(x, y, Align.center);
    }

//====================================================================================================================

    @Override
	public void act(float delta) {
        super.act(delta);
        if(canAct()){
            physicsPlayer.act(delta,this);
            animationPlayer.act(delta,this);
            if(actorState!=null){
                ActorState newState =  actorState.update(this, delta);
                goingToNewState(newState,null);
            }
		}
	}
	
	protected boolean canAct() {
		return lifeStatus == LIFE_STATUS_LIVE || lifeStatus == LIFE_STATUS_DEAD;
	}

    /**************************************draw control**************************************************/

    public float drawOffSetX,drawOffSetY;

    public void drawFoot(Batch batch, float parentAlpha){
        batch.draw(ResourceManager.humanShadow,this.getCenterX() - this.getWidth()/2,this.getCenterY()-50,this.getWidth(),ResourceManager.humanShadow.getRegionHeight());
    }

    public void drawBody(Batch batch, float parentAlpha) {
        if(lifeStatus != LIFE_STATUS_DESTORY){
            GdxSprite sprite = (GdxSprite) animationPlayer.getKeyFrame();
            if (sprite != null) {
                sprite.setPosition(this.getCenterX() + drawOffSetX, this.getCenterY() + drawOffSetY);
                if(isHover){
                    int oldSrcFunc = batch.getBlendSrcFunc();
                    int oldDescFunc = batch.getBlendDstFunc();
                    batch.setBlendFunction(GL20.GL_BLEND_SRC_RGB, GL20.GL_ONE);
                    sprite.draw(batch, 1, getRotation(), getScaleX()+0.05f, getScaleY()+0.05f, Color.RED);
                    batch.setBlendFunction(oldSrcFunc,oldDescFunc);
                }
                sprite.draw(batch, parentAlpha, getRotation(), getScaleX(), getScaleY(), getColor());
            }
        }
    }

//    @Override
//    public void drawHead(Batch batch, float parentAlpha) {
//        super.drawHead(batch, parentAlpha);
//    }


    /**************************************move control**************************************************/
    private ActorMoveToAction actorMoveToAction;
    public void moveLeft(){
		addAction(ActionsFactory.actorMoveDirective(LEFT));
		animationPlayer.onMoveLeft();
	}
	public void moveRight(){
		addAction(ActionsFactory.actorMoveDirective(RIGHT));
		animationPlayer.onMoveRight();
	}
	public void moveDown(){
		addAction(ActionsFactory.actorMoveDirective(DOWN));
		animationPlayer.onMoveDown();
	}
	public void moveUp(){
		addAction(ActionsFactory.actorMoveDirective(UP));
		animationPlayer.onMoveUp();
	} 
	
	public void move(int direction){
        setDirection(direction);
		if(direction == LEFT){
			moveLeft();
		}else if(direction == RIGHT){
			moveRight();
		}else if(direction == DOWN){
			moveDown();
		}else if(direction == UP){
			moveUp();
		}else{
			stop();
		}
	}

	public void stop(){
        if(actorMoveToAction !=null){
            removeAction(actorMoveToAction);
        }
		addAction(ActionsFactory.actorMoveDirective(STOP));
		animationPlayer.onStop(this.direction);
	}

    public void moveToPoint(float x, float y){
        stop();
        IntArray path = getWorld().getPhysicsManager().getAstarPath(new Vector2(this.getPhysicsPosition()),new Vector2(x,y));
        actorMoveToAction = new ActorMoveToPathAction(path);
        addAction(actorMoveToAction);
    }
    public void moveToTarget(AnimationEntity target){
        stop();
        actorMoveToAction = new ActorMoveToActorAction(target);
        addAction(actorMoveToAction);
    }

    public AnimationEntity findClosestEntry(List<AnimationEntity> excludes){
        AnimationEntity found = null;
        float distance = Float.MAX_VALUE;
        for(Actor actor : getStage().getActors()){
            if(actor instanceof AnimationEntity
                && !(actor instanceof AnimationProjection)
                && actor != this
                && (excludes == null || !excludes.contains(actor))
             ){
                AnimationEntity entity = (AnimationEntity) actor;
                float d = Vector2.dst(entity.getCenterX(),entity.getCenterY(),this.getCenterX(),this.getCenterY());
                if(d < distance){
                    distance = d;
                    found = entity;
                }
            }
        }
        return found;
    }

    public void turnDirection(float degrees){
		this.degrees = degrees;
		if(degrees > 45 && degrees < 135){
			direction = AnimationEntity.UP;
		}else if(degrees > 135 && degrees < 225){
			direction = AnimationEntity.LEFT;
		}else if(degrees > 225 && degrees < 325){
			direction = AnimationEntity.DOWN;
		}else if(degrees > 325 || degrees < 45){
			direction = AnimationEntity.RIGHT;
		}
	}
    public void doMoveAnimation(){
        animationPlayer.doMoveAnimation(this.direction);
    }

    public void setDirection(int direction) {
        float degrees = 0;
        if(direction == LEFT){
            degrees = 180;
        }else if(direction == RIGHT){
            degrees = 0;
        }else if(direction == DOWN){
            degrees = 270;
        }else if(direction == UP){
            degrees = 90;
        }else{
            degrees = 0;
        }
        turnDirection(degrees);
    }
    //让对象去死
    public void dead(){
        clearActions();
        lifeStatus = LIFE_STATUS_DEAD;
        addAction(ActionsFactory.sequence(ActionsFactory.actorDeathAnimation()));
    }


    /**************************************state control**************************************************/
    ActorState actorState = new StandState(0);
    public boolean handleInput(final InputEvent event){
        if(actorState!=null){
            ActorState newState = actorState.handleInput(this,event);
            return goingToNewState(newState,event);
        }
        return false;
    }

    public boolean goingToNewState(ActorState newState, InputEvent event){
        if(newState!=null){
            actorState.exit(this);
            actorState = newState;
            actorState.enter(this,event);
            return true;
        }
        return false;
    }


    @Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	

	public void dispose(){
		lifeStatus = LIFE_STATUS_DESTORY;
        animationPlayer.onDispose();
	}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnimationEntity that = (AnimationEntity) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public StageWorld getWorld() {
        return stageWorld;
    }

    public void setStageWorld(StageWorld stageWorld) {
        this.stageWorld = stageWorld;
    }

    public void setPhysicsPlayer(PhysicsPlayer physicsPlayer) {
        this.physicsPlayer = physicsPlayer;
    }

    public PhysicsPlayer getPhysicsPlayer() {
        return physicsPlayer;
    }
}