package com.example.tongxiwen.ladyrinth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LadyrinthView maze = (LadyrinthView) findViewById(R.id.maze_view);
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
}
