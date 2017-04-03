package com.guxuede.game.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * 扩展自带的ParallelAction，
 * 使其支持：
 *  1.半路终止（stopToThisAction）
 *  2.判断是否所有的action都执行完毕了（isAllActionEnd）
 * Created by guxuede on 2017/4/3 .
 */
public class GdxParallelAction extends Action {
    Array<Action> actions = new Array(4);
    Array<Action> currentActions = new Array(false,4);
    private boolean complete;
    protected boolean stopToThisAction = false;

    public GdxParallelAction() {
    }

    public GdxParallelAction(Action action1) {
        addAction(action1);
    }

    public GdxParallelAction(Action action1, Action action2) {
        addAction(action1);
        addAction(action2);
    }

    public GdxParallelAction(Action action1, Action action2, Action action3) {
        addAction(action1);
        addAction(action2);
        addAction(action3);
    }

    public GdxParallelAction(Action action1, Action action2, Action action3, Action action4) {
        addAction(action1);
        addAction(action2);
        addAction(action3);
        addAction(action4);
    }

    public GdxParallelAction(Action action1, Action action2, Action action3, Action action4, Action action5) {
        addAction(action1);
        addAction(action2);
        addAction(action3);
        addAction(action4);
        addAction(action5);
    }

    public boolean act (float delta) {
        if (complete) return true;
        complete = true;
        currentActions.clear();
        Pool pool = getPool();
        setPool(null); // Ensure this action can't be returned to the pool while executing.
        try {
            Array<Action> actions = this.actions;
            for (int i = 0, n = actions.size; i < n && actor != null; i++) {
                Action currentAction = actions.get(i);
                if(currentAction.getActor()!=null){
                    if(!currentAction.act(delta)){
                        complete = false;
                    }else{
                        currentActions.add(currentAction);
                        if(stopToThisAction){
                            complete = true;
                        }
                    }
                }
                if (actor == null) return true; // This action was removed.
            }
            return complete;
        } finally {
            setPool(pool);
        }
    }

    public void restart () {
        complete = false;
        stopToThisAction = false;
        Array<Action> actions = this.actions;
        for (int i = 0, n = actions.size; i < n; i++)
            actions.get(i).restart();
    }

    public void reset () {
        super.reset();
        actions.clear();
        currentActions.clear();
        stopToThisAction = false;
    }

    public void addAction (Action action) {
        actions.add(action);
        if (actor != null) action.setActor(actor);
    }

    public void setActor (Actor actor) {
        Array<Action> actions = this.actions;
        for (int i = 0, n = actions.size; i < n; i++)
            actions.get(i).setActor(actor);
        super.setActor(actor);
    }

    public Array<Action> getActions () {
        return actions;
    }

    public String toString () {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append(super.toString());
        buffer.append('(');
        Array<Action> actions = this.actions;
        for (int i = 0, n = actions.size; i < n; i++) {
            if (i > 0) buffer.append(", ");
            buffer.append(actions.get(i));
        }
        buffer.append(')');
        return buffer.toString();
    }

    /**
     * 设置：执行完当前的action后，就立即结束，不要在继续执行剩下的Action了
     */
    public void stopToThisAction() {
        this.stopToThisAction = true;
    }

    /**
     * 是否所有的Action都结束了
     * @return
     */
    public boolean isAllActionEnd(){
        return complete;
    }

    /**
     * 得到当前正在运行的Action
     * @return
     */
    public Array<Action> getCurrentAction(){
        return currentActions;
    }


}
