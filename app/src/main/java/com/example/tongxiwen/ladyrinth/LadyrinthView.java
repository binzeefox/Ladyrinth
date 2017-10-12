package com.example.tongxiwen.ladyrinth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.example.tongxiwen.ladyrinth.innerbox.Ladyrinth;
import com.example.tongxiwen.ladyrinth.innerbox.MazeBox;

import java.util.Map;

/**
 * Created by tong.xiwen on 2017/10/11.
 */
public class LadyrinthView extends View {

    Ladyrinth ladyrinth;
    private int mazeSize = 16;
    private float minUnit;
    private Paint mPaint;
    private Map<String, MazeBox> boxMap;
    private float width;

    public LadyrinthView(Context context) {
        this(context, null, 0);
    }
    public LadyrinthView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public LadyrinthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        ladyrinth = new Ladyrinth(mazeSize);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5);
        boxMap = ladyrinth.getMaze();
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

        for (String key : boxMap.keySet()){
            MazeBox box = boxMap.get(key);
            canvas.drawPath(getPath(box), mPaint);
        }
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
        invalidate();
    }

    /**
     * 获取迷宫尺寸
     */
    public int getMazeSize() {
        return mazeSize;
    }
}
