package com.example.tongxiwen.ladyrinth.innerbox;

/**
 * Created by tong.xiwen on 2017/10/11.
 * 单个迷宫格子
 * *0*
 * 1*3
 * *2*
 */
public class MazeBox {
    private int[] pos;
    private boolean[] accessFlags = new boolean[]{false,false,false,false};
    private int moveTo = -1;

    public MazeBox(int x, int y){
        pos = new int[]{x,y};
    }

    /**
     * 设置某方向通畅性
     */
    public void openFlag(int direction){
        accessFlags[direction] = true;
    }

    /**
     * 获取格子坐标
     * @return  返回数组，0为x，1为y
     */
    public int[] getPos() {
        return pos;
    }

    /**
     * 获取墙壁状态
     * @return 返回boolean的数组，0为上，1为左，2为下，3为右
     */
    public boolean[] getFlags() {
        return accessFlags;
    }

    /**
     * 设置该格子的动向
     */
    public int getMoveTo() {
        return moveTo;
    }

    /**
     * 获取该格子的动向
     */
    public void setMoveTo(int moveTo) {
        this.moveTo = moveTo;
    }
}
