package com.guxuede.game.actor;

import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.guxuede.game.animation.ActorAlwayMoveAction;
import com.guxuede.game.libgdx.GdxSprite;
import com.guxuede.game.resource.ActorAnimationPlayer;
import com.guxuede.game.libgdx.ResourceManager;
import com.guxuede.game.animation.ActionsFactory;

public abstract class AnimationEntity extends LevelDrawActor implements Poolable{

	public static final int STOP=0, DOWN=1,LEFT=2,RIGHT=3,UP=4;
	public static final int LIFE_STATUS_CREATE=0, LIFE_STATUS_BORN=1,LIFE_STATUS_LIVE=2,LIFE_STATUS_DEAD=3,LIFE_STATUS_DESTORY=-1;
	public static long ID = 1000000000001L;
	public long id;
	public int direction=DOWN;
	public float degrees;
	public float speed=200;
	public boolean isMoving;

	public int lifeStatus = LIFE_STATUS_CREATE;
	
	public Body body;
	public ActorAnimationPlayer animationPlayer;
	public AnimationEntity sourceActor;

    public AnimationEntity(ActorAnimationPlayer animationPlayer,World world,InputListener l) {
		this(animationPlayer, world);
		if(l!=null)addListener(l);
	}
	
	public AnimationEntity(ActorAnimationPlayer animationPlayer,World world) {
		id = ID++;
		this.animationPlayer = animationPlayer;
		int actorWidth= animationPlayer.width;
		int actorHeight= animationPlayer.height;
        this.setSize(actorWidth, actorHeight);
        this.setOrigin(Align.center);
        this.setVisible(false);
        this.lifeStatus = LIFE_STATUS_CREATE;
        this.addAction(new ActorAlwayMoveAction());
	}


    /**************************************box2d control**************************************************/
	//call by stage
	public void createBody(World world){
		if(lifeStatus == LIFE_STATUS_CREATE){
			lifeStatus = LIFE_STATUS_LIVE;
			int actorWidth= animationPlayer.width;
			this.setVisible(true);
			/**********************************box2d************************************************/
            //http://www.firedragonpzy.com.cn/index.php/archives/2524
			BodyDef  bd = new  BodyDef ();
			bd.type=BodyType.DynamicBody;
			bd.position.set(getEntityX(),getEntityY());

			CircleShape c=new CircleShape();
			c.setRadius(actorWidth/3);
			FixtureDef ballShapeDef = new FixtureDef();
			ballShapeDef.density = 1.0f;//密度
			ballShapeDef.friction = 1f;////摩擦粗糙程度
			ballShapeDef.restitution = 0.0f;//碰撞后，恢复原状后的力量,力度返回程度（弹性）
			ballShapeDef.shape = c;//形状
            ballShapeDef.isSensor= false;//当isSensor为false时(这也是默认值)，在发生碰撞后，由Box2D模拟物理碰撞后的反弹或变向运动。当isSensor是true时，刚体只进行碰撞检测，而不模拟碰撞后的物理运动。此时，我们就可以自定义刚体处理方式了，如示例中的绕小圆运动。
			body = world.createBody(bd);
			body.createFixture(ballShapeDef);
			body.setFixedRotation(true);//固定旋转标记把转动惯量逐渐设置成零。
			body.setLinearDamping(100);//阻尼，阻尼用来降低世界中物体的速度。阻尼和摩擦不同，因为摩擦仅仅和接触同时发生。阻尼不是摩擦的一个替代者，并且这两个效果可以被同时使用。
			body.setAngularDamping(100);//瑙掗樆灏�鎽╂摝?
			body.setUserData(this);
			c.dispose();
			/**
			 * 1.力，循序渐进——ApplyForce 顾名思义，ApplyForce方法会在刚体上施加一个力。学过物理力学的同学都知道，F=ma，有了力F就有了加速度a，有了加速度，物体就会有速度，就会慢慢动起来。(但是不会立马动起来，因为力不会直接影响速度)。 举个简单的例子，小明推一个静止的箱子，箱子不会立马飞出去，而是慢慢的、越来越快的动起来(减速也一样)。
			 */
			//body.applyForce(force, point, wake);
			/**
			 * 与ApplyForce不同，ApplyImpulse不会产生力，而是直接影响刚体的速度。通过ApplyImpulse方法添加的速度会与刚体原有的速度叠加，产生新的速度。
			 */
			//body.applyLinearImpulse(impulse, point, wake);
			//body.applyAngularImpulse(impulse, wake);
			/**
			 * setLinearVelocity与ApplyImpulse一样，直接影响刚体的速度。不一样的是，setLinearVelocity添加的速度会覆盖刚体原有的速度。不过，在SetLinearVelocity方法不会自动唤醒sleeping的刚体，所以在调用该方法之前，记得将刚体body.wakeUp()一下。
			 */
			//body.setAngularVelocity(0);//鏃嬭浆鏃剁殑瑙掗�搴�
			//body.setLinearVelocity(1,1);//绾挎�閫熷害
			/**********************************box2d************************************************/

		}
	}

	//call by stage
	public void destroyBody(World world){
		if(lifeStatus == LIFE_STATUS_DEAD){
			lifeStatus = LIFE_STATUS_DESTORY;
			world.destroyBody(body);
			this.remove();//TODO 也许不应该在这里remove掉
		}
	}
	
	@Override
	public void moveBy(float x, float y) {
		if (x != 0 || y != 0) {
			body.setTransform(body.getPosition().x += x, body.getPosition().y += y, 99);
			positionChanged();
		}
	}
	
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		//这个方法被body调用注意死循环
		//System.err.println(x+","+y);
		//body.setTransform(x, y, 0);
	}
	
	@Override
	public void act(float delta) {
        super.act(delta);
        if(canAct()){
            animationPlayer.act();
			Vector2 v=body.getPosition();
			this.setPosition(v.x - getWidth()/2, v.y - getHeight()/2);
		}
	}
	
	protected boolean canAct() {
		return lifeStatus == LIFE_STATUS_LIVE || lifeStatus == LIFE_STATUS_DEAD;
	}

    /**************************************draw control**************************************************/

    public float drawOffSetX,drawOffSetY;

    public void drawFoot(Batch batch, float parentAlpha){
        batch.draw(ResourceManager.humanShadow,this.getEntityX() - ResourceManager.humanShadow.getRegionWidth()/2,this.getEntityY()-ResourceManager.humanShadow.getRegionHeight()/2);
    }
    public void drawBody(Batch batch, float parentAlpha) {
        GdxSprite sprite = (GdxSprite) animationPlayer.getKeyFrame();
        if (sprite != null) {
            sprite.setPosition(this.getEntityX() + drawOffSetX, this.getEntityY() + drawOffSetY);
            sprite.draw(batch, parentAlpha, getRotation(), getScaleX(), getScaleY(), getColor());
        }
    }

    @Override
    public void drawHead(Batch batch, float parentAlpha) {
        super.drawHead(batch, parentAlpha);
    }


    /**************************************position control**************************************************/
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
		addAction(ActionsFactory.actorMoveDirective(STOP));
		animationPlayer.onStop(this.direction);
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

    /**************************************Projection control**************************************************/
    public void throwProjection(){
		for(float d=0f;d < 360f;d=d+30){
			throwProjection(d);
		}
		//throwProjection(this.degrees);
	}
	public void throwProjection(float degrees){
		float l = 400;
		double radians = (float) (2*Math.PI * degrees / 360);
		float dx=(float) (this.getEntityX()+l*Math.cos(radians));
		float dy=(float) (this.getEntityY()+l*Math.sin(radians));
		throwProjection(dx,dy);
	}
	public void throwProjection(float dx,float dy){
		throwProjection(getEntityX(), getEntityY(),dx,dy);
	}
	public void throwProjection(float fx,float fy,float dx,float dy){
		AnimationProjection projection = ActorFactory.createProjection(ResourceManager.bult, body.getWorld(), null);
		projection.sourceActor = this;
		//projection.body.setTransform(this.body.getPosition(),0);
		projection.setPosition(fx, fy);
		projection.turnDirection(degrees);
		projection.addAction(
				ActionsFactory.sequence(ActionsFactory.scaleBy(5, 5, 0.1f),
                        ActionsFactory.scaleBy(-5, -5, 0.1f),
                        ActionsFactory.jumpAction(dx, dy)
                        , ActionsFactory.actorDeathAnimation(0, 0.2f)
                ));
		getStage().addActor(projection);
	}
	

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	

	public void dispose(){
		lifeStatus = LIFE_STATUS_DEAD;
	}

}