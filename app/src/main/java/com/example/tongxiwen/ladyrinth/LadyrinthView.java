package com.example.tongxiwen.ladyrinth;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import com.example.tongxiwen.ladyrinth.innerbox.Ladyrinth;
import com.example.tongxiwen.ladyrinth.innerbox.MazeBox;

import java.util.Map;

/**
 * Created by tong.xiwen on 2017/10/11.
 */
public class LadyrinthView extends View {

    private boolean reset = true;
    private Path mazePath;

    private MazeBox myPos;
    private Ladyrinth ladyrinth;
    private int mazeSize = 16;
    private float minUnit;
    private Paint mPaint;
    private Paint fillPaint;
    private Map<String, MazeBox> boxMap;
    private float width;

    private OnWinnerWatcher watcher = new OnWinnerWatcher() {
        @Override
        public void onWin() {

        }
    };

    public LadyrinthView(Context context) {
        this(context, null, 0);
    }
    public LadyrinthView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public LadyrinthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        ladyrinth = new Ladyrinth(mazeSize);
        mazePath = new Path();

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5);
        boxMap = ladyrinth.getMaze();

        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.RED);

        resetMyPos();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        minUnit = width/mazeSize;
        setMeasuredDimension((int) width, (int) width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (reset) {
            mazePath.reset();
            for (String key : boxMap.keySet()) {
                MazeBox box = boxMap.get(key);
                canvas.drawPath(getPath(box), mPaint);
            }
        } else {
            canvas.drawPath(mazePath, mPaint);
        }

        drawMyPos(canvas);
    }

    /**
     * 获取单个格子的墙壁
     * @param box
     * @return
     */
    private Path getPath(MazeBox box) {
        float x = box.getPos()[0] * minUnit;
        float y = box.getPos()[1] * minUnit;

        Path path = new Path();
        boolean[] flags = box.getFlags();

        if (box.getPos()[0] == mazeSize - 1 && box.getPos()[1] == mazeSize - 1){
            flags[3] = true;
        }

        if (!flags[0]){
            path.moveTo(x,y);
            path.lineTo(x + minUnit, y);
        }
        if (!flags[1]){
            path.moveTo(x,y);
            path.lineTo(x,y+minUnit);
        }
        if (!flags[2]){
            path.moveTo(x,y+minUnit);
            path.lineTo(x+minUnit, y+minUnit);
        }
        if (!flags[3]){
            path.moveTo(x+minUnit, y);
            path.lineTo(x+minUnit, y+minUnit);
        }
        mazePath.addPath(path);
        return path;
    }

    /**
     * 设置尺寸
     */
    public void setMazeSize(int mazeSize) {
        this.mazeSize = mazeSize;
        minUnit = width / mazeSize;
        ladyrinth.setSize(mazeSize);
        reset();
    }

    /**
     * 重置地图
     */
    public void reset(){
        ladyrinth.reset();
        boxMap = ladyrinth.getMaze();
        resetMyPos();
        reset = true;
        invalidate();
    }

    /**
     * 获取迷宫尺寸
     */
    public int getMazeSize() {
        return mazeSize;
    }

    /**********************************************操作相关**************************************************/

    /**
     * 重置角色位置
     */
    private void resetMyPos() {
        myPos = boxMap.get("0|0");
    }

    /**
     * 移动角色
     */
    public void moveMyPos(int direction){
        int x = myPos.getPos()[0];
        int y = myPos.getPos()[1];

        if (x == mazeSize - 1 && y == mazeSize - 1){
            return;
        }

        boolean[] flags = myPos.getFlags();
        boolean flag0 = flags[0];
        boolean flag1 = flags[1];
        boolean flag2 = flags[2];
        boolean flag3 = flags[3];

        String key = "0|0";
        switch (direction){
            case 0:
                if (flag0){
                    key = x + "|" + (y-1);
                    myPos = boxMap.get(key);
                }
                break;
            case 1:
                if (flag1){
                    key = (x-1) + "|" + y;
                    myPos = boxMap.get(key);
                }
                break;
            case 2:
                if (flag2){
                    key = x + "|" + (y+1);
                    myPos = boxMap.get(key);
                }
                break;
            case 3:
                if (flag3){
                    key = (x+1) + "|" + y;
                    myPos = boxMap.get(key);
                }
                break;
            default:
                break;
        }
        if (myPos.getPos()[0] == mazeSize - 1 && myPos.getPos()[1] == mazeSize - 1){
            watcher.onWin();
        }
        reset = false;
        invalidate();
    }

    /**
     * 绘制角色
     */
    private void drawMyPos(Canvas canvas) {
        float x = myPos.getPos()[0] * minUnit;
        float y = myPos.getPos()[1] * minUnit;

        RectF rectF = new RectF(x,y,x+minUnit, y+minUnit);
        canvas.drawRect(rectF, fillPaint);
    }


    public void addOnWinnerWatcher(OnWinnerWatcher watcher){
        this.watcher = watcher;
    }

    public interface OnWinnerWatcher {
        void onWin();
    }
}
