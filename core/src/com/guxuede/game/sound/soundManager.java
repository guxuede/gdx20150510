package com.guxuede.game.sound;

import com.badlogic.gdx.utils.Array;
import com.guxuede.game.resource.SoundHolder;

/**
 * Created by guxuede on 2016/9/11 .
 */
public class SoundManager{

    Array<SoundHolder> soundHolders = new Array<SoundHolder>();

    public void registerSound(SoundHolder soundHolder){
        if(!soundHolders.contains(soundHolder,true)){
            soundHolders.add(soundHolder);
        }
    }

    public void unRegisterSound(SoundHolder soundHolder){
        if(soundHolders.contains(soundHolder,true)){
            soundHolders.removeValue(soundHolder,true);
        }
    }


    public void pause() {
        for(SoundHolder soundHolder:soundHolders){
                soundHolder.pause();
        }
    }


    public void resume() {
        for(SoundHolder soundHolder:soundHolders){
                soundHolder.resume();
        }
    }
}
