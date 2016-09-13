package com.guxuede.game.actor;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.guxuede.game.StageWorld;
import com.guxuede.game.libgdx.ResourceManager;
import com.guxuede.game.resource.*;

/**
 * Created by guxuede on 2016/5/26 .
 */
public class ActorFactory {

    public static AnimationActor createActor(String name, StageWorld world) {
        AnimationHolder animationHolder = ResourceManager.getAnimationHolder(name);
        ActorAnimationPlayer actorAnimationPlayer = new ActorAnimationPlayer(world,animationHolder);
        AnimationActor animationActor = new AnimationActor(actorAnimationPlayer,world);
        return animationActor;
    }
    public static EffectsEntity createEffectsActor(String name, StageWorld world) {
        AnimationHolder animationHolder = ResourceManager.getAnimationHolder(name);
        ActorAnimationPlayer actorAnimationPlayer = new ActorAnimationPlayer(world,animationHolder);
        EffectsEntity animationActor = new EffectsEntity(actorAnimationPlayer,world);
        return animationActor;
    }
    public static AnimationProjection createProjectionActor(String name, StageWorld world) {
        AnimationHolder animationHolder = ResourceManager.getAnimationHolder(name);
        ActorAnimationPlayer actorAnimationPlayer = new ActorAnimationPlayer(world,animationHolder);
        AnimationProjection animationActor = new AnimationProjection(actorAnimationPlayer,world);
        return animationActor;
    }
    public static LightningEntity createLightningEntity(String name, StageWorld world) {
        AnimationHolder animationHolder = ResourceManager.getAnimationHolder(name);
        ActorAnimationPlayer actorAnimationPlayer = new ActorAnimationPlayer(world,animationHolder);
        LightningEntity animationActor = new LightningEntity(actorAnimationPlayer,world);
        return animationActor;
    }

    public static AnimationDoor creatDoor(String name, StageWorld world) {
        AnimationHolder animationHolder = ResourceManager.getAnimationHolder(name);
        ActorAnimationPlayer actorAnimationPlayer = new ActorAnimationPlayer(world,animationHolder);
        AnimationDoor animationActor = new AnimationDoor(actorAnimationPlayer,world);
        return animationActor;
    }

    public static  void createRandomDoor(StageWorld world, Stage stage){
        for(int i = 0;i<1;i++){
            AnimationDoor door = ActorFactory.creatDoor("wind2",world);
            door.setPosition(MathUtils.random(-100,1000),MathUtils.random(-100,1000));
            stage.addActor(door);
        }
    }

    public static  void createRandomActor(StageWorld world, Stage stage){
        for(int i = 0;i<10;i++){
            AnimationActor actor = ActorFactory.createActor(ResourceManager.ANIMATION_HOLDER_LIST.get(MathUtils.random(0,ResourceManager.ANIMATION_HOLDER_LIST.size()-1)).name,world);
            actor.setPosition(MathUtils.random(0,200),MathUtils.random(-0,200));
            stage.addActor(actor);
        }
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
