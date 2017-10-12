package com.example.tongxiwen.ladyrinth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private LadyrinthView maze;

    private int[] ids = new int[]{
            R.id.button_top, R.id.button_left, R.id.button_bottom, R.id.button_right
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int id : ids){
            View view = findViewById(id);
            view.setOnClickListener(this);
        }

        maze = (LadyrinthView) findViewById(R.id.maze_view);
        maze.addOnWinnerWatcher(new LadyrinthView.OnWinnerWatcher() {
            @Override
            public void onWin() {
                Toast.makeText(MainActivity.this, "您获胜了！！！请重置游戏", Toast.LENGTH_SHORT).show();
            }
        });

        final EditText text = (EditText) findViewById(R.id.size_field);
        Button button = (Button) findViewById(R.id.size_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text.getText() != null){
                    int size = maze.getMazeSize();
                    try {
                        size = Integer.valueOf(text.getText().toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    maze.setMazeSize(size);
                    text.setText(null);
                    text.setHint("当前尺寸为" + maze.getMazeSize());
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_top:
                maze.moveMyPos(0);
                break;
            case R.id.button_left:
                maze.moveMyPos(1);
                break;
            case R.id.button_bottom:
                maze.moveMyPos(2);
                break;
            case R.id.button_right:
                maze.moveMyPos(3);
                break;
            default:
                break;
        }
    }
}
