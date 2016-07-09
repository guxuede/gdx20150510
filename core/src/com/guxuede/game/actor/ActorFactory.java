package com.guxuede.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.guxuede.game.libgdx.ResourceManager;
import com.guxuede.game.resource.*;

/**
 * Created by guxuede on 2016/5/26 .
 */
public class ActorFactory {

    public static AnimationActor createActor(String name,World world) {
        AnimationHolder animationHolder = ResourceManager.getAnimationHolder(name);
        ActorAnimationPlayer actorAnimationPlayer = new ActorAnimationPlayer(animationHolder);
        AnimationActor animationActor = new AnimationActor(actorAnimationPlayer,world);
        return animationActor;
    }
    public static AnimationActor createActor(String name,World world,InputListener l) {
        AnimationHolder animationHolder = ResourceManager.getAnimationHolder(name);
        ActorAnimationPlayer actorAnimationPlayer = new ActorAnimationPlayer(animationHolder);
        AnimationActor animationActor = new AnimationActor(actorAnimationPlayer,world,l);
        return animationActor;
    }
    public static EffectsEntity createEffectsActor(String name,World world,InputListener l) {
        AnimationHolder animationHolder = ResourceManager.getAnimationHolder(name);
        ActorAnimationPlayer actorAnimationPlayer = new ActorAnimationPlayer(animationHolder);
        EffectsEntity animationActor = new EffectsEntity(actorAnimationPlayer,world,l);
        return animationActor;
    }
    public static AnimationProjection createProjectionActor(String name,World world,InputListener l) {
        AnimationHolder animationHolder = ResourceManager.getAnimationHolder(name);
        ActorAnimationPlayer actorAnimationPlayer = new ActorAnimationPlayer(animationHolder);
        AnimationProjection animationActor = new AnimationProjection(actorAnimationPlayer,world,l);
        return animationActor;
    }


    public static AnimationActor createAnimationActor(String name,World world,InputListener l) {
        return createAnimationActor(ResourceManager.getTextureRegion(name), world,l);
    }

    public static AnimationActor createAnimationActor(TextureAtlas atlas,String name,World world,InputListener l) {
        return createAnimationActor(atlas.findRegion(name), world,l);
    }

    public static AnimationActor createAnimationActor(Texture texture,World world) {
        return createAnimationActor(new TextureRegion(texture), world,null);
    }

    public static AnimationActor createAnimationActor(TextureAtlas atlas,String name,World world) {
        return createAnimationActor(atlas.findRegion(name), world,null);
    }

    public static AnimationActor createAnimationActor(TextureRegion ar,World world,InputListener l) {
        AnimationHolder animationHolder = new BX8ActorAnimationHolderParse().parse(ar);
        ActorAnimationPlayer actorAnimationPlayer = new ActorAnimationPlayer(animationHolder);
        AnimationActor animationActor = new AnimationActor(actorAnimationPlayer,world,l);
        return animationActor;
    }

//==============================

    public static AnimationProjection createProjection(String name, World world, InputListener l) {
        return createProjection(ResourceManager.getTextureRegion(name), world, l);
    }

    public static AnimationProjection createProjection(TextureAtlas atlas, String name, World world, InputListener l) {
        return createProjection(atlas.findRegion(name), world, l);
    }
    public static AnimationProjection createProjection(Texture texture, World world) {
        return createProjection(new TextureRegion(texture), world, null);
    }

    public static AnimationProjection createProjection(TextureAtlas atlas, String name, World world) {
        return createProjection(atlas.findRegion(name), world, null);
    }


    public static AnimationProjection createProjection(TextureRegion ar, World world, InputListener l) {
        AnimationHolder animationHolder = new BulletAnimationHolderParse().parse(ar);
        ActorAnimationPlayer actorAnimationPlayer = new ActorAnimationPlayer(animationHolder);
        AnimationProjection animationActor = new AnimationProjection(actorAnimationPlayer,world,l);
        return animationActor;
    }


    //==============================

    public static DecorationActor createDecoration(String name,World world,InputListener l) {
        return createDecoration(ResourceManager.getTextureRegion(name), world, l);
    }

    public static DecorationActor createDecoration(TextureAtlas atlas,String name,World world,InputListener l) {
        return createDecoration(atlas.findRegion(name), world, l);
    }

    public static DecorationActor createDecoration(TextureAtlas atlas,String name,World world) {
        return createDecoration(atlas.findRegion(name), world, null);
    }
    public static DecorationActor createDecoration(Texture texture,World world) {
        return createDecoration(new TextureRegion(texture), world, null);
    }

    public static DecorationActor createDecoration(TextureRegion ar,World world,InputListener l) {
        AnimationHolder animationHolder = new DecorationAnimationHolderParse().parse(ar);
        ActorAnimationPlayer actorAnimationPlayer = new ActorAnimationPlayer(animationHolder);
        DecorationActor animationActor = new DecorationActor(actorAnimationPlayer,world,l);
        return animationActor;
    }

}
