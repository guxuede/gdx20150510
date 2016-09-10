package com.guxuede.game.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.guxuede.game.GameWorld;
import com.guxuede.game.actor.state.ActorState;
import com.guxuede.game.actor.state.StandState;
import com.guxuede.game.animation.ActionsFactory;
import com.guxuede.game.animation.ActorAlwayMoveAction;
import com.guxuede.game.animation.move.ActorMoveToAction;
import com.guxuede.game.animation.move.ActorMoveToActorAction;
import com.guxuede.game.animation.move.ActorMoveToPointAction;
import com.guxuede.game.libgdx.GdxSprite;
import com.guxuede.game.position.PositionPlayer;
import com.guxuede.game.resource.ActorAnimationPlayer;

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
	public long id;
	public int direction=DOWN;
	public float degrees;
	public float speed=1200000;
	public boolean isMoving;
    public float visualRadius=100;
    public Color  primaryColor;
	public int lifeStatus = LIFE_STATUS_CREATE;

    public GameWorld gameWorld;
	public PositionPlayer positionPlayer;
	public ActorAnimationPlayer animationPlayer;
	public AnimationEntity sourceActor;

    public AnimationEntity(ActorAnimationPlayer animationPlayer,GameWorld world,InputListener l) {
        this(animationPlayer, world);
        if(l!=null)addListener(l);
    }

    public AnimationEntity(ActorAnimationPlayer animationPlayer,GameWorld world) {
        id = ID++;
        this.animationPlayer = animationPlayer;
        int actorWidth= animationPlayer.width;
        int actorHeight= animationPlayer.height;
        this.setSize(actorWidth, actorHeight);
        this.setOrigin(Align.center);
        this.setVisible(false);
        this.lifeStatus = LIFE_STATUS_CREATE;
        this.addAction(new ActorAlwayMoveAction());
        this.visualRadius = actorWidth * 4;
        this.primaryColor = new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1);
        this.positionPlayer = world.getPositionWorld().createPositionPlayer();
        this.gameWorld = world;
    }

    /**************************************box2d control**************************************************/
	//call by stage
	public void createBody(GameWorld world){
		if(lifeStatus == LIFE_STATUS_CREATE){
			lifeStatus = LIFE_STATUS_BORN;
            this.positionPlayer.init(this);
			this.setVisible(true);
		}
        if(lifeStatus == LIFE_STATUS_BORN){
            lifeStatus = LIFE_STATUS_LIVE;
        }
    }

	//call by stage
	public void destroyBody(GameWorld world){
		if(lifeStatus == LIFE_STATUS_DESTORY){
            this.positionPlayer.destroy(this);
			this.remove();//TODO 也许不应该在这里remove掉
		}
	}

    //=============================================Position Control========================================================================
    public void setLinearVelocity(Vector2 actorLinearVelocity) {
        this.positionPlayer.setLinearVelocity(actorLinearVelocity);
    }

    public void setEntityPosition(float x,float y){
        this.positionPlayer.setXY(x,y);
    }

	@Override
	public void moveBy(float x, float y) {
		if (x != 0 || y != 0) {
			positionPlayer.setXY(positionPlayer.getX() + x, positionPlayer.getY() + y);
			positionChanged();
		}
	}

    //这个方法被body调用注意死循环
    //System.err.println(x+","+y);
    //positionPlayer.setTransform(x, y, 0);

    /**
     * 不要在外面调用下面三个方法。这些方法被内部位置系统positionPlayer无数次的调用用来同步actor位置，外部调用无用。
     * 仅初始状态可调用来调整位置.
     * 如需实时改变位置请使用 setEntityPosition。
     *  @see AnimationEntity.setCenterPosition
     *  @see PositionPlayer.act()
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
    public void setCenterPosition(float x, float y) {
        this.setPosition(x, y, Align.center);
    }

//====================================================================================================================

    @Override
	public void act(float delta) {
        super.act(delta);
        if(canAct()){
            positionPlayer.act(delta,this);
            animationPlayer.act(delta,this);
            if(actorState!=null){
                ActorState newState =  actorState.update(this, delta);
                processNewState(newState,null);
            }
		}
	}
	
	protected boolean canAct() {
		return lifeStatus == LIFE_STATUS_LIVE || lifeStatus == LIFE_STATUS_DEAD;
	}

    /**************************************draw control**************************************************/

    public float drawOffSetX,drawOffSetY;

//    public void drawFoot(Batch batch, float parentAlpha){
//        //batch.draw(ResourceManager.humanShadow,this.getCenterX() - ResourceManager.humanShadow.getRegionWidth()/2,this.getCenterY()-ResourceManager.humanShadow.getRegionHeight()/2);
//    }
    public void drawBody(Batch batch, float parentAlpha) {
        if(lifeStatus != LIFE_STATUS_DESTORY){
            GdxSprite sprite = (GdxSprite) animationPlayer.getKeyFrame();
            if (sprite != null) {
                sprite.setPosition(this.getCenterX() + drawOffSetX, this.getCenterY() + drawOffSetY);
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
        actorMoveToAction = new ActorMoveToPointAction(x,y);
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
        final int oldDirection = this.direction;
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
        //if Direction not change,no need to re do Animation
        if(!isMoving && oldDirection == this.direction){
            return;
        }
        doMoveAnimation();
	}

    public void doMoveAnimation(){
        if(direction == AnimationEntity.LEFT){
            animationPlayer.doMoveLeftAnimation();
        }else if(direction == AnimationEntity.RIGHT){
            animationPlayer.doMoveRightAnimation();
        }else if(direction == AnimationEntity.DOWN){
            animationPlayer.doMoveDownAnimation();
        }else if(direction == AnimationEntity.UP){
            animationPlayer.doMoveUpAnimation();
        }
    }

    public void setDirection(int direction) {
        this.direction = direction;
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
    }
    //让对象去死
    public void dead(){
        clearActions();
        lifeStatus = LIFE_STATUS_DEAD;
        addAction(ActionsFactory.sequence(ActionsFactory.actorDeathAnimation()));
    }


    /**************************************state control**************************************************/
    ActorState actorState = new StandState(0);
    public void handleInput(InputEvent event){
        if(actorState!=null){
            ActorState newState = actorState.handleInput(this,event);
            processNewState(newState,event);
        }
    }

    private void processNewState(ActorState newState,InputEvent event){
        if(newState!=null){
            actorState.exit(this);
            actorState = newState;
            actorState.enter(this,event);
        }
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

    public GameWorld getWorld() {
        return gameWorld;
    }

}