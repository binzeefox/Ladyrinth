package com.example.tongxiwen.ladyrinth.innerbox;

import android.graphics.Point;
import android.util.Log;

import java.util.*;

/**
 * Created by tong.xiwen on 2017/10/11.
 * 迷宫类
 */
public class Ladyrinth {
    private int size;
    private int maxBoxes;
    private Map<String, MazeBox> boxMap;
    private Random random = new Random();
    private Stack<MazeBox> historyList;

    public Ladyrinth(int size) {
        this.size = size;
        maxBoxes = size * size;
        historyList = new Stack<>();
        boxMap = new HashMap<>();
    }

    /**
     * 获取格子
     */
    public Map<String, MazeBox> getMaze() {
        MazeBox box = new MazeBox(0, 0);
        box.openFlag(1);

        while (boxMap.size() < maxBoxes) {
            boolean isDeadEnd = true;
            while (isDeadEnd) {
                if (checkDeadEnd(box)) {
                    if (historyList.size() > 0) {
                        String key = box.getPos()[0] + "|" + box.getPos()[1];
                        boxMap.put(key, box);
                        box = historyList.pop();
                    } else break;
                } else {
                    isDeadEnd = false;
                }
            }
            int move = moveBox(box);
            box.setMoveTo(move);
            historyList.push(box);
            String key = box.getPos()[0] + "|" + box.getPos()[1];
            if (boxMap.get(key) != null) {
                boxMap.remove(key);
            }
            boxMap.put(key, box);
            box = nextBox(box);
            if (boxMap.size() == maxBoxes - 1){
                key = box.getPos()[0] + "|" + box.getPos()[1];
                boxMap.put(key, box);
            }
            Log.d("boxMap 的size", String.valueOf(boxMap.size()));
        }

        return boxMap;
    }

    /**
     * 获取下一个格子
     */
    private MazeBox nextBox(MazeBox foreBox) {
        MazeBox box;
        int lastMove = foreBox.getMoveTo();
        int foreX = foreBox.getPos()[0];
        int foreY = foreBox.getPos()[1];
        switch (lastMove) {
            case 0:
                box = new MazeBox(foreX, foreY - 1);
                box.openFlag(2);
                return box;
            case 1:
                box = new MazeBox(foreX - 1, foreY);
                box.openFlag(3);
                return box;
            case 2:
                box = new MazeBox(foreX, foreY + 1);
                box.openFlag(0);
                return box;
            case 3:
                box = new MazeBox(foreX + 1, foreY);
                box.openFlag(1);
                return box;
            default:
                Log.e("获取格子错误", "获取方向数字不在0-3之间");
                return null;
        }
    }

    /**
     * 判断是否为死路
     */
    private boolean checkDeadEnd(MazeBox box) {
        int x = box.getPos()[0];
        int y = box.getPos()[1];
        boolean isBlock = true;
        String key1 = (box.getPos()[0]-1) + "|" + box.getPos()[1];
        String key3 = (box.getPos()[0]+1) + "|" + box.getPos()[1];
        String key0 = box.getPos()[0] + "|" + (box.getPos()[1]-1);
        String key2 = box.getPos()[0] + "|" + (box.getPos()[1]+1);

        if (boxMap.get(key1) == null && x != 0) {
            isBlock =  false;
        } else if (boxMap.get(key3) == null && x != size - 1) {
            isBlock =  false;
        } else if (boxMap.get(key0) == null && y != 0) {
            isBlock =  false;
        } else if (boxMap.get(key2) == null && y != size - 1) {
            isBlock =  false;
        }
        return isBlock;
    }

    /**
     * 移动盒子并返回移动方向，同时标记开口
     */
    private int moveBox(MazeBox box) {
        int move = -1;
        int[] boxPos = box.getPos().clone();
        String key1 = (box.getPos()[0]-1) + "|" + box.getPos()[1];
        String key3 = (box.getPos()[0]+1) + "|" + box.getPos()[1];
        String key0 = box.getPos()[0] + "|" + (box.getPos()[1]-1);
        String key2 = box.getPos()[0] + "|" + (box.getPos()[1]+1);

        boolean legal = false;
        while (!legal){
            move = random.nextInt(4);
            switch (move) {
                case 0:
                    if (boxPos[1] != 0 && boxMap.get(key0) == null) {
                        box.openFlag(0);
                        legal = true;
                    }
                    break;
                case 1:
                    if (boxPos[0] != 0 && boxMap.get(key1) == null) {
                        box.openFlag(1);
                        legal = true;
                    }
                    break;
                case 2:
                    if (boxPos[1] != size - 1 && boxMap.get(key2) == null) {
                        box.openFlag(2);
                        legal = true;
                    }
                    break;
                case 3:
                    if (boxPos[0] != size - 1 && boxMap.get(key3) == null) {
                        box.openFlag(3);
                        legal = true;
                    }
                    break;
                default:
                    Log.e("获取随机数错误", "获得的数字不在0-3之间");
                    break;
            }
        }
        return move;
    }

    /**
     * 设置尺寸
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
        maxBoxes = size * size;
    }

    /**
     * 重置迷宫
     */
    public void reset(){
        boxMap = new HashMap<>();
    }
}
