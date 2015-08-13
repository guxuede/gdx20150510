package com.guxuede.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.guxuede.game.animation.ActorAnimationGen;
import com.guxuede.game.libgdx.ResourceManager;
import com.guxuede.game.tools.ActionsUtils;
import com.test.game.TitleMapStage;

public class AnimationActor extends Actor implements Poolable{

	public static final int STOP=0, DOWN=1,LEFT=2,RIGHT=3,UP=4;
	public int direction=DOWN;
	public float degrees;
	public float speed=100;
	
	public Body body;
	public ActorAnimationGen animationGen;
	public AnimationActor sourceActor;

	public AnimationActor(String name,World world,InputListener l) {
		this(new TextureRegion(new Texture(Gdx.files.internal(name))),world);
		if(l!=null)addListener(l);
	}
	
	public AnimationActor(TextureAtlas atlas,String name,World world,InputListener l) {
		this(atlas,name,world);
		if(l!=null)addListener(l);
	}
	
	public AnimationActor(TextureAtlas atlas,String name,World world) {
		this(atlas.findRegion(name),world);
	}
	
	public AnimationActor(TextureRegion ar,World world) {
		animationGen = new ActorAnimationGen(this, ar);
		int actorWidth=animationGen.width;
		int actorHeight=animationGen.height;
        this.setSize(actorWidth, actorHeight);
        /**********************************box2d************************************************/
        BodyDef  bd = new  BodyDef ();
        bd.type=BodyType.DynamicBody; 
        bd.position.set(getX(),getY()); 
        
		CircleShape c=new CircleShape(); 
		c.setRadius(actorWidth/3);
		FixtureDef ballShapeDef = new FixtureDef();
		ballShapeDef.density = 1.0f;//密度
		ballShapeDef.friction = 1f;////摩擦粗糙程度
		ballShapeDef.restitution = 0.0f;//碰撞后，恢复原状后的力量,力度返回程度（弹性）
		ballShapeDef.shape = c;//形状
		//ballShapeDef.isSensor=
		body = world.createBody(bd);
		body.createFixture(ballShapeDef);
		//body.setFixedRotation(true);//纰版挒鏃�鏄惁鏃嬭浆
		body.setLinearDamping(100);//阻尼
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
	
	 
	public void draw(Batch batch, float parentAlpha) {
		Vector2 v=body.getPosition();
		this.setPosition(v.x-getWidth()/2, v.y-getHeight()/2+16);
		
		Sprite tr=(Sprite) animationGen.getKeyFrame();
		tr.setColor(getColor());
		tr.setX(this.getX());tr.setY(this.getY());
		tr.setSize(getWidth(), getHeight());
		tr.setOrigin(getWidth()/2, getHeight()/2);
		tr.setRotation(getRotation());
		tr.setScale(getScaleX(), getScaleY());
		tr.setColor(getColor());
		
		tr.draw(batch);
		
	}
	
	public void moveLeft(){
		addAction(ActionsUtils.actorMoveDirective(LEFT));
		animationGen.onMoveLeft();
	}
	public void moveRight(){
		addAction(ActionsUtils.actorMoveDirective(RIGHT));
		animationGen.onMoveRight();
	}
	public void moveDown(){
		addAction(ActionsUtils.actorMoveDirective(DOWN));
		animationGen.onMoveDown();
	}
	public void moveUp(){
		addAction(ActionsUtils.actorMoveDirective(UP));
		animationGen.onMoveUp();
	}
	
	
	public void move(int direction){
	}
	
	public void stop(){
		addAction(ActionsUtils.actorMoveDirective(STOP));
		animationGen.onStop();
	}
	
	public void turnDirection(float degrees){
		this.degrees = degrees;
		if(degrees > 45 && degrees < 135){
			direction = AnimationActor.UP;
		}else if(degrees > 135 && degrees < 225){
			direction = AnimationActor.LEFT;
		}else if(degrees > 225 && degrees < 325){
			direction = AnimationActor.DOWN;
		}else if(degrees > 325 || degrees < 45){
			direction = AnimationActor.RIGHT;
		}
	}
	
	public void throwProjection(){
		throwProjection(this.degrees);
	}
	public void throwProjection(float degrees){
		float l = 400;
		double radians = (float) (2*Math.PI * degrees / 360);
		float dx=(float) (this.getX()+l*Math.cos(radians));
		float dy=(float) (this.getY()+l*Math.sin(radians));
		throwProjection(dx,dy);
	}
	public void throwProjection(float x,float y){
		AnimationActorProjection projection = new AnimationActorProjection(ResourceManager.bult,body.getWorld());
		projection.sourceActor = this;
		projection.body.setTransform(this.body.getPosition(),0);
		projection.addAction(ActionsUtils.jumpAction(x, y));
		this.getStage().addActor(projection);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	public void dispose(){
		((TitleMapStage)this.getStage()).deleteActor(this);
	}
}