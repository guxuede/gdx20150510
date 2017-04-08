package com.guxuede.game.actor.ability.skill;

import com.guxuede.game.action.GdxSequenceAction;
import com.guxuede.game.resource.ResourceManager;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

/**
 * Created by guxuede on 2016/9/27 .
 */
public class ScriptSkill extends Skill {

    private String script;
    private GdxSequenceAction action;
    private String name;
    private SkillTargetTypeEnum targetType;
    private int hotKey;

    public int getHotKey() {
        return hotKey;
    }

    public SkillTargetTypeEnum getTargetType() {
        return targetType;
    }

    @Override
    public void enter() {
        ScriptEngine scriptEngine = ResourceManager.getScriptEngine();
        SimpleBindings simpleBindings = new SimpleBindings();
        simpleBindings.put("targetEntry",targetEntry);
        simpleBindings.put("targetPos",targetPos);
        simpleBindings.put("owner",owner);
        try {
            action = (GdxSequenceAction) scriptEngine.eval(script,simpleBindings);
        } catch (ScriptException e) {
            throw new RuntimeException("Skill error",e);
        }
        owner.addAction(action);
    }

    @Override
    public boolean update(float delta) {
        return action.isAllGDXActionEnd();
    }

    @Override
    public void exit() {
        super.exit();
        //如果SkillAction（技能前摇），还有的没结束，直接就结束。如果已经结束，就不要remove了，让其他的继续执行。前摇是可以打断的
        if (action != null && !action.isAllGDXActionEnd()) {
            action.stopToThisAction();
        }
    }

    public void setHotKey(int hotKey) {
        this.hotKey = hotKey;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public void setTargetType(SkillTargetTypeEnum targetType) {
        this.targetType = targetType;
    }
}
