package com.guxuede.game.resource;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.guxuede.game.tools.SoundUtils;

/**
 * Created by guxuede on 2016/9/3 .
 */
public class SoundHolder {
    private Sound sound;
    private long soundId;
    private boolean isLoop;

    public Sound getSound() {
        return sound;
    }

    public boolean isLoop() {
        return isLoop;
    }


    public SoundHolder(Sound sound, boolean isLoop){
        this.sound =sound;
        this.isLoop = isLoop;
    }

    public void reset(Sound sound,boolean isLoop){
        stop();
        this.sound =sound;
        this.isLoop = isLoop;
    }


    public void play(){
        soundId = sound.play();
        sound.setLooping(soundId,isLoop);
    }

    public void pause(){
        if(soundId!=-1){
            sound.pause(soundId);
        }
    }

    public void resume(){
        if(soundId!=-1){
            sound.resume(soundId);
        }
    }

    public void stop(){
        if(soundId!=-1){
            if(isLoop){
                sound.setLooping(soundId,false);
            }else {
                sound.stop(soundId);
            }
        }
        soundId = -1;
    }

    public void act(float delta,Actor entity){
        if (sound != null && soundId != -1) {
            SoundUtils.set3dPan(sound, soundId, entity);
        }
    }
}
