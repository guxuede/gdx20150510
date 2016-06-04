package com.guxuede.game.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.guxuede.game.actor.AnimationEntity;
import com.guxuede.game.libgdx.GdxEffect;
import com.guxuede.game.libgdx.GdxSprite;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 使得角色有幻影效果
 * Created by guxuede on 2016/6/2 .
 */
public class DoubleImageEffect extends GdxEffect {


    private Queue<AnimationEntitySnapshot> last5Animation = new LinkedList<AnimationEntitySnapshot>();
    private float lastFrameTime = 0;

    @Override
    protected void update(float percent) {
        AnimationEntity animationEntity = getAnimationEntity();
        if(animationEntity.stateTime - lastFrameTime > 0.1 ){
            GdxSprite sprite = (GdxSprite) animationEntity.animationPlayer.getKeyFrame();
            lastFrameTime = animationEntity.stateTime;
            if(last5Animation.size() > 5){
                last5Animation.poll();
            }
            last5Animation.offer(new AnimationEntitySnapshot(sprite,animationEntity.getX(),animationEntity.getY()));
        }
    }

    @Override
    public void drawBodyEffect(Batch batch, float parentAlpha) {
        AnimationEntity animationEntity = getAnimationEntity();
        Iterator<AnimationEntitySnapshot> gdxSpriteIterator = last5Animation.iterator();
        float alpha = 0.1f;
        while(gdxSpriteIterator.hasNext()){
            AnimationEntitySnapshot snapshot = gdxSpriteIterator.next();
            GdxSprite sprite = snapshot.sprite;
            sprite.setPosition(snapshot.x + animationEntity.drawOffSetX, snapshot.y + animationEntity.drawOffSetY);
            sprite.setSize(animationEntity.getWidth(), animationEntity.getHeight());
            sprite.setRotation(animationEntity.getRotation());
            sprite.setScale(animationEntity.getScaleX(), animationEntity.getScaleY());
            sprite.setColor(animationEntity.getColor());
            sprite.setAlpha(alpha);
            alpha = alpha + 0.1f;
            sprite.draw(batch, parentAlpha);
        }
    }

    @Override
    public void reset() {
        super.reset();
        last5Animation.clear();
        lastFrameTime = 0;
    }

    private class AnimationEntitySnapshot{
        GdxSprite sprite;
        float x,y;
        public AnimationEntitySnapshot(GdxSprite sprite, float x, float y) {
            this.sprite = sprite;
            this.x = x;
            this.y = y;
        }
    }
}
