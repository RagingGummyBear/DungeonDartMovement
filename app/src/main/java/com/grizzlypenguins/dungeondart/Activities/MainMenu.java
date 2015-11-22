package com.grizzlypenguins.dungeondart.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.grizzlypenguins.dungeondart.Difficulty;
import com.grizzlypenguins.dungeondart.Level;
import com.grizzlypenguins.dungeondart.R;
import com.grizzlypenguins.dungeondart.myFactory;

import java.util.jar.Attributes;

public class MainMenu extends Activity {
    RelativeLayout mainScreen;
    FrameLayout createGameScreen;
    Button newGame;
    Button exitGame;
    Button startGame;
    Button back;
    RatingBar ratingBar;
    Level startNewLevel;
    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mainScreen = (RelativeLayout)findViewById(R.id.firstScreen);
        createGameScreen = (FrameLayout)findViewById(R.id.createGameScreen);
        newGame = (Button)findViewById(R.id.newGameButton);
        exitGame = (Button)findViewById(R.id.startGameButton);
        startGame = (Button) findViewById(R.id.startGameButton);
        back = (Button) findViewById(R.id.backButton);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        set_listeners();
        window = this.getWindow();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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

    void toggle_layout(boolean b)
    {
        if(b)
        {
            mainScreen.setClickable(false);
            mainScreen.setVisibility(View.INVISIBLE);
            createGameScreen.setVisibility(View.VISIBLE);
            createGameScreen.setClickable(true);

        }
        else
        {
            mainScreen.setClickable(true);
            mainScreen.setVisibility(View.VISIBLE);
            createGameScreen.setVisibility(View.INVISIBLE);
            createGameScreen.setClickable(false);
        }
    }

    void set_listeners()
    {
        newGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            System.out.println("clicked newGame");

            toggle_layout(true);
            }
        });

        exitGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                System.out.println("clicked exitGame");


            }
        });
        startGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                System.out.println("clicked startGame");

                Intent myIntent = new Intent(MainMenu.this,
                        GamePlayActivity.class);
                Difficulty dif = new Difficulty(ratingBar.getNumStars());
                startNewLevel = new Level(dif, myFactory.getInstance().test_map_1(),window.getDecorView().getWidth(),window.getDecorView().getHeight());
                startNewLevel.start();
                while(startNewLevel.running){

                }
                myIntent.putExtra("PackedLevel",startNewLevel.packedLevel);
                startActivity(myIntent);



            }
        });
        back.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        System.out.println("clicked backButton");
                        toggle_layout(false);

                    }
        });

    }
}
