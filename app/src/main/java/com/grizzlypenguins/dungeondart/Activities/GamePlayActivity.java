package com.grizzlypenguins.dungeondart.Activities;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.grizzlypenguins.dungeondart.PackedLevel;
import com.grizzlypenguins.dungeondart.R;

public class GamePlayActivity extends Activity {

    Button move_up;
    Button move_down;
    Button move_left;
    Button move_right;

    PackedLevel level ;
    GamePanel gamePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // turn off title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game_play);

        initialize();
        set_listeners();



        //System.out.println(level.cameraControl.player_position.x + " "+ level.difficulty.starNumber + " " +level.mainCharacter.speed + "  "+ level.levelMap.tileNumber );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    void initialize()
    {
        level = (PackedLevel) getIntent().getSerializableExtra("PackedLevel");
        gamePanel = (GamePanel)findViewById(R.id.GamePanel);
        move_up = (Button)findViewById(R.id.moveUp);
        move_down = (Button)findViewById(R.id.moveDown);
        move_left = (Button)findViewById(R.id.moveLeft);
        move_right = (Button)findViewById(R.id.moveRight);
        gamePanel.level = level;
    }
    void set_listeners()
    {

        move_up.setOnTouchListener(new Button.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        gamePanel.pressedButton("up");
                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        gamePanel.pressedButton("none");
                        // RELEASED
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });

        move_down.setOnTouchListener(new Button.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        gamePanel.pressedButton("down");
                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        gamePanel.pressedButton("none");
                        // RELEASED
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });

        move_left.setOnTouchListener(new Button.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        gamePanel.pressedButton("left");
                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        gamePanel.pressedButton("none");
                        // RELEASED
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });
        move_right.setOnTouchListener(new Button.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        gamePanel.pressedButton("right");
                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        gamePanel.pressedButton("none");
                        // RELEASED
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });
    }
}
