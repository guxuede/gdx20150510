package com.guxuede.game.actor;

import java.util.Arrays;
import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class AnimationActor1 extends Actor {
	
	 Animation  walkDownAnimation;
	 Animation  walkLeftAnimation;
	 Animation  walkRightAnimation;
	 Animation  walkUpAnimation;
	 
	 Animation  stopDownAnimation;
	 Animation  stopLeftAnimation;
	 Animation  stopRightAnimation;
	 Animation  stopUpAnimation;
	 
	 Animation  currentAnimation;
	 
	 float stateTime;
	 
	 Body body;
	 
	 
	 private static final int DOWN=1,LEFT=2,RIGHT=3,UP=4;
	 private static final int WALK=0,IDLE=1;
	 
	 private int direction=WALK;
	 private int behavior=IDLE;
	 

	public void draw(Batch batch, float parentAlpha) {
		Vector2 v=body.getPosition();
		this.setPosition(v.x-getWidth()/2, v.y-getHeight()/2+16);
		
		stateTime += Gdx.graphics.getDeltaTime();
		Sprite tr=(Sprite) currentAnimation.getKeyFrame(stateTime, true);
		tr.setColor(getColor());
		tr.setX(this.getX());tr.setY(this.getY());
		tr.setSize(getWidth(), getHeight());
		tr.setOrigin(getWidth()/2, getHeight()/2);
		tr.setRotation(getRotation());
		tr.setScale(getScaleX(), getScaleY());
		tr.setColor(getColor());
		
		tr.draw(batch);
		//this.setZIndex(this.getY()<0?0:(int)this.getY());
        //batch.draw(tr, this.getX(), this.getY());
        //System.out.println(this.getY());
        //body.setAngularVelocity(0);
        //body.setAngularVelocity(x);
       // body.applyAngularImpulse(x++);
      // body.setLinearDamping(x);
       // body.setGravityScale(x);
        //body.setAngularVelocity(0);
        	//x=x+0.001f;
        //System.out.println(x);
       
       // body.setLinearVelocity(0, speed);
        if(direction==DOWN){
        	if(behavior==WALK){
        		body.setLinearVelocity(0, -speed);
        		currentAnimation=walkDownAnimation;
        	}else if(behavior==IDLE){
        		body.setLinearVelocity(0, 0);
        		currentAnimation=stopDownAnimation;
        	}
        }else if(direction==UP){
        	if(behavior==WALK){
        		body.setLinearVelocity(0, speed);
        		currentAnimation=walkUpAnimation;
        	}else if(behavior==IDLE){
        		body.setLinearVelocity(0, 0);
        		currentAnimation=stopUpAnimation;
        	}
        }else if(direction==LEFT){
        	if(behavior==WALK){
        		body.setLinearVelocity(-speed, 0);
        		currentAnimation=walkLeftAnimation;
        	}else if(behavior==IDLE){
        		body.setLinearVelocity(0, 0);
        		currentAnimation=stopLeftAnimation;
        	}
        }else if(direction==RIGHT){
        	if(behavior==WALK){
        		body.setLinearVelocity(speed, 0);
        		currentAnimation=walkRightAnimation;
        	}else if(behavior==IDLE){
        		body.setLinearVelocity(0, 0);
        		currentAnimation=stopRightAnimation;
        	}
        }
//        if(currentAnimation==walkDownAnimation){
//        	body.setTransform(v , 1.585f);
//        }else if(currentAnimation==walkLeftAnimation){
//        	body.setTransform(v , 0);
//        }else if(currentAnimation==walkRightAnimation){
//        	body.setTransform(v , 0);
//        }else if(currentAnimation==walkUpAnimation){
//        	body.setTransform(v , 1.585f);
//        }
	}

	public AnimationActor1(String name,World world,InputListener l) {
		this( new TextureRegion(new Texture(Gdx.files.internal(name))),world);
		addListener(l);
	}
	
	public AnimationActor1(TextureAtlas atlas,String name,World world,InputListener l) {
		this(atlas,name,world);
		addListener(l);
	}
	
	public AnimationActor1(TextureAtlas atlas,String name,World world) {
		this(atlas.findRegion(name),world);
	}
	
	public AnimationActor1(TextureRegion ar,World world) {
		int actorWidth=ar.getRegionWidth()/4;
		int actorHeight=ar.getRegionHeight()/4;
		this.setWidth(actorWidth);
		this.setHeight(actorHeight);
		
		TextureRegion[][] tmp=ar.split(actorWidth,actorHeight);
		for(int i=0;i<tmp.length;i++){
			for(int j=0;j<tmp[0].length;j++){
				tmp[i][j] = new Sprite(tmp[i][j]);
			}
		}
        walkDownAnimation = new Animation(stepDuration, tmp[0][0], tmp[0][1] ,tmp[0][2], tmp[0][3]);
        walkLeftAnimation = new Animation(stepDuration, tmp[1][0], tmp[1][1] ,tmp[1][2], tmp[1][3]); 
        walkRightAnimation= new Animation(stepDuration, tmp[2][0], tmp[2][1] ,tmp[2][2], tmp[2][3]); 
        walkUpAnimation   = new Animation(stepDuration, tmp[3][0], tmp[3][1] ,tmp[3][2], tmp[3][3]); 
        
        stopDownAnimation = new Animation(0.2f, tmp[0][2]); 
        stopLeftAnimation = new Animation(0.2f, tmp[1][2]); 
        stopRightAnimation= new Animation(0.2f, tmp[2][2]); 
        stopUpAnimation   = new Animation(0.2f, tmp[3][2]); 
        
        currentAnimation  = stopDownAnimation;
        
        stateTime = 0f;     
        
        this.setSize(actorWidth, actorWidth);
        BodyDef bd = new BodyDef();bd.type=BodyType.DynamicBody; bd.position.set(getX(),getY()); 
//		PolygonShape c=new PolygonShape();c.setAsBox(8, 8);//c.setAsBox(TILEWIDTH/3, TILEHIGHT/5);
		CircleShape c=new CircleShape(); c.setRadius(actorWidth/3);
		body = world.createBody(bd);
		body.createFixture(c, 1f);
		body.setFixedRotation(true);//纰版挒鏃�鏄惁鏃嬭浆
		body.setAngularVelocity(0);//鏃嬭浆鏃剁殑瑙掗�搴�
		body.setLinearDamping(10);//绾挎�闃诲凹 鎽╂摝?
		body.setAngularDamping(10);//瑙掗樆灏�鎽╂摝?
		body.setLinearVelocity(1,1);//绾挎�閫熷害
	}
	
	@Override
	public void moveBy(float x, float y) {
		if (x != 0 || y != 0) {
			body.setTransform(body.getPosition().x += x, body.getPosition().y += y, 99);
			positionChanged();
		}
	}
	
	private float stepDuration=0.1f;
	private float speed=100;
	
	public void moveLeft(){
		direction=LEFT;
		behavior=WALK;
	}
	public void moveRight(){
		direction=RIGHT;
		behavior=WALK;
	}
	public void moveDown(){
		direction=DOWN;
		behavior=WALK;
	}
	public void moveUp(){
		direction=UP;
		behavior=WALK;
	}
	
	public void move(int direction){
		this.direction=direction;
		behavior=WALK;
	}
	
	public void stop(){
		behavior=IDLE;
	}
	
}