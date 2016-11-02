package com.test.l1;

/**
 * Created by greggu on 2016/10/31 .
 */
public class MaskTest
{

    public static final int         //实体状态
            LIFE_STATUS_CREATE=0x01, //实体处于创建创建状态，还没有进入世界，系统将不久后初始化它，并进入LIFE_STATUS_BORN
            LIFE_STATUS_BORN=0x02,//实体处于诞生状态，进入世界，诞生状态的实体，无敌不可攻击，并有诞生动画，诞生完成后进入LIFE_STATUS_LIVE
            LIFE_STATUS_LIVE=0x04,//实体处于正常活动状态，参与世界的任何事件，可以被控制，可以被攻击
            LIFE_STATUS_DEAD=0x08,//实体处于死亡状态，无敌不可攻击不可控制，并有死亡动画，死亡完成后进入LIFE_STATUS_DESTORY
            LIFE_STATUS_DESTORY=0x10;//实体处于摧毁状态，退出世界之外，系统将不久销毁它，内存空间将清理


    public static  int flag = LIFE_STATUS_BORN | LIFE_STATUS_LIVE|LIFE_STATUS_DEAD;

    /**
     *  删除一项或多项权限
     */
    public static void disable(int permission) {
        flag &= ~permission;
    }

    /**
     *  是否拥某些权限permission 是否在flag中
     */
    public static boolean inIt(int permission) {
        return (flag & permission) == permission;
    }

    /**
     *  是否拥某些权限,flag 是否在permission中
     */
    public static boolean isIn(int permission){
        return (flag & permission) == flag;
    }

    /**
     *  是否禁用了某些权限,permission是否不在flag中
     */
    public static boolean isNotInIt(int permission) {
        return (flag & permission) == 0;
    }

    /**
     *  是否仅仅拥有某些权限
     */
    public static boolean isOnlyAllow(int permission) {
        return flag == permission;
    }

    public static void main(String[] args)
    {
        System.out.println("args = [" + isNotInIt(LIFE_STATUS_BORN) + "]");
    }
}