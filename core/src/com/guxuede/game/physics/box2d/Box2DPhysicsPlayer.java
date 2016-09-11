package com.guxuede.game.physics.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.physics.PhysicsPlayer;

/**
 * Created by guxuede on 2016/9/10 .
 */
public class Box2DPhysicsPlayer implements PhysicsPlayer {
    private Body body;
    private Box2DPhysicsManager positionWorld;
    private Vector2 linearVelocity;

    public Box2DPhysicsPlayer(Box2DPhysicsManager positionWorld){
        this.positionWorld = positionWorld;
    }

    public void init(AnimationEntity animationEntity){
        /**********************************box2d************************************************/
        //http://www.firedragonpzy.com.cn/index.php/archives/2524
        BodyDef bd = new  BodyDef ();
        bd.type= BodyDef.BodyType.DynamicBody;
        bd.position.set(animationEntity.getCenterX(), animationEntity.getCenterY());

        CircleShape c=new CircleShape();

        c.setRadius(animationEntity.getWidth()/4);
        FixtureDef ballShapeDef = new FixtureDef();
        ballShapeDef.density = 0.0f;//密度
        ballShapeDef.friction = 1f;////摩擦粗糙程度
        ballShapeDef.restitution = 0.0f;//碰撞后，恢复原状后的力量,力度返回程度（弹性）
        ballShapeDef.shape = c;//形状
        ballShapeDef.isSensor= animationEntity.isSensor;//当isSensor为false时(这也是默认值)，在发生碰撞后，由Box2D模拟物理碰撞后的反弹或变向运动。当isSensor是true时，刚体只进行碰撞检测，而不模拟碰撞后的物理运动。此时，我们就可以自定义刚体处理方式了，如示例中的绕小圆运动。
        body = positionWorld.world.createBody(bd);
        body.createFixture(ballShapeDef);
        body.setFixedRotation(true);//固定旋转标记把转动惯量逐渐设置成零。
        body.setLinearDamping(100);//阻尼，阻尼用来降低世界中物体的速度。阻尼和摩擦不同，因为摩擦仅仅和接触同时发生。阻尼不是摩擦的一个替代者，并且这两个效果可以被同时使用。
        body.setAngularDamping(100);//瑙掗樆灏�鎽╂摝?
        body.setUserData(animationEntity);
        c.dispose();
    }

    @Override
    public void destroy(AnimationEntity entity) {
        positionWorld.world.destroyBody(body);
    }

    @Override
    public void setAwake(boolean b) {
        body.setAwake(true);
    }

    @Override
    public void setLinearVelocity(Vector2 v) {
        this.linearVelocity = v;
        body.setLinearVelocity(v);
    }

    @Override
    public float getX() {
        return body.getPosition().x;
    }

    @Override
    public float getY() {
        return body.getPosition().y;
    }

    @Override
    public Vector2 getXY() {
        return body.getPosition();
    }

    @Override
    public void setX(float x) {
        body.setTransform(x,body.getPosition().y ,99);
    }

    @Override
    public void setY(float y) {
        body.setTransform(body.getPosition().x ,y,99);
    }

    @Override
    public void setXY(float x, float y) {
        body.setTransform(x ,y,99);
    }

    @Override
    public void setXY(Vector2 vector2) {
        body.setTransform(vector2.x ,vector2.y,99);
    }

    @Override
    public void act(float delta,AnimationEntity entity) {
        //将模拟位置同步到actor上
        Vector2 v = this.getXY();
        //出于立体感效果，将碰撞设置到角色脚部
        entity.setPosition(v.x - entity.getWidth()/2,v.y - entity.getHeight()/4);
    }
}
