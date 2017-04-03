package com.guxuede.game.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * 扩展自带的ParallelAction，
 * 使其支持：
 *  1.半路终止（stopToThisAction）
 *  2.判断是否所有的action都执行完毕了（isAllActionEnd）
 *  3.判断是否所有的GdxParallel都指向完毕了（isAllGDXActionEnd）
 * Created by guxuede on 2017/4/3 .
 */
public class GdxSequenceAction extends GdxParallelAction {
    private int index;


    public GdxSequenceAction () {
    }

    public GdxSequenceAction (Action action1) {
        addAction(action1);
    }

    public GdxSequenceAction (Action action1, Action action2) {
        addAction(action1);
        addAction(action2);
    }

    public GdxSequenceAction (Action action1, Action action2, Action action3) {
        addAction(action1);
        addAction(action2);
        addAction(action3);
    }

    public GdxSequenceAction (Action action1, Action action2, Action action3, Action action4) {
        addAction(action1);
        addAction(action2);
        addAction(action3);
        addAction(action4);
    }

    public GdxSequenceAction (Action action1, Action action2, Action action3, Action action4, Action action5) {
        addAction(action1);
        addAction(action2);
        addAction(action3);
        addAction(action4);
        addAction(action5);
    }

    public boolean act (float delta) {
        currentActions.clear();
        if (index >= actions.size) return true;
        Pool pool = getPool();
        setPool(null); // Ensure this action can't be returned to the pool while executings.
        try {
            currentActions.add(actions.get(index));
            if (actions.get(index).act(delta)) {
                if (actor == null) return true; // This action was removed.
                index++;
                if (index >= actions.size || stopToThisAction) return true;
            }
            return false;
        } finally {
            setPool(pool);
        }
    }

    public void restart () {
        super.restart();
        index = 0;
    }

    @Override
    public boolean isAllActionEnd() {
        return index >= actions.size;
    }

    public boolean isAllGDXActionEnd(){
        for(Action action:getActions()){
            if(action instanceof GdxParallelAction){
                GdxParallelAction skillSequenceAction = (GdxParallelAction) action;
                if(!skillSequenceAction.isAllActionEnd()){
                    return false;
                }
            }
        }
        return true;
    }

}
