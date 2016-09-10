package com.guxuede.game.actor;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.guxuede.game.GameWorld;
import com.guxuede.game.libgdx.ResourceManager;
import com.guxuede.game.resource.*;

/**
 * Created by guxuede on 2016/5/26 .
 */
public class ActorFactory {

    public static AnimationActor createActor(String name, GameWorld world) {
        AnimationHolder animationHolder = ResourceManager.getAnimationHolder(name);
        ActorAnimationPlayer actorAnimationPlayer = new ActorAnimationPlayer(animationHolder);
        AnimationActor animationActor = new AnimationActor(actorAnimationPlayer,world);
        return animationActor;
    }
    public static AnimationActor createActor(String name,GameWorld world,InputListener l) {
        AnimationHolder animationHolder = ResourceManager.getAnimationHolder(name);
        ActorAnimationPlayer actorAnimationPlayer = new ActorAnimationPlayer(animationHolder);
        AnimationActor animationActor = new AnimationActor(actorAnimationPlayer,world,l);
        return animationActor;
    }
    public static EffectsEntity createEffectsActor(String name,GameWorld world,InputListener l) {
        AnimationHolder animationHolder = ResourceManager.getAnimationHolder(name);
        ActorAnimationPlayer actorAnimationPlayer = new ActorAnimationPlayer(animationHolder);
        EffectsEntity animationActor = new EffectsEntity(actorAnimationPlayer,world,l);
        return animationActor;
    }
    public static AnimationProjection createProjectionActor(String name,GameWorld world,InputListener l) {
        AnimationHolder animationHolder = ResourceManager.getAnimationHolder(name);
        ActorAnimationPlayer actorAnimationPlayer = new ActorAnimationPlayer(animationHolder);
        AnimationProjection animationActor = new AnimationProjection(actorAnimationPlayer,world,l);
        return animationActor;
    }
    public static LightningEntity createLightningEntity(String name,GameWorld world,InputListener l) {
        AnimationHolder animationHolder = ResourceManager.getAnimationHolder(name);
        ActorAnimationPlayer actorAnimationPlayer = new ActorAnimationPlayer(animationHolder);
        LightningEntity animationActor = new LightningEntity(actorAnimationPlayer,world,l);
        return animationActor;
    }



    //==============================

//    public static DecorationActor createDecoration(String name,World world,InputListener l) {
//        return createDecoration(ResourceManager.getTextureRegion(name), world, l);
//    }
//
//    public static DecorationActor createDecoration(TextureAtlas atlas,String name,World world,InputListener l) {
//        return createDecoration(atlas.findRegion(name), world, l);
//    }
//
//    public static DecorationActor createDecoration(TextureAtlas atlas,String name,World world) {
//        return createDecoration(atlas.findRegion(name), world, null);
//    }
//    public static DecorationActor createDecoration(Texture texture,World world) {
//        return createDecoration(new TextureRegion(texture), world, null);
//    }
//
//    public static DecorationActor createDecoration(TextureRegion ar,World world,InputListener l) {
//        AnimationHolder animationHolder = new DecorationAnimationHolderParse().parse(ar);
//        ActorAnimationPlayer actorAnimationPlayer = new ActorAnimationPlayer(animationHolder);
//        DecorationActor animationActor = new DecorationActor(actorAnimationPlayer,world,l);
//        return animationActor;
//    }

}
