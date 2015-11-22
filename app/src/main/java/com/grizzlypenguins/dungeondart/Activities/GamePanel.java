package com.grizzlypenguins.dungeondart.Activities;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.grizzlypenguins.dungeondart.GameLoop.MyGameLoop;
import com.grizzlypenguins.dungeondart.PackedLevel;
import com.grizzlypenguins.dungeondart.R;
import com.grizzlypenguins.dungeondart.Tile;
import com.grizzlypenguins.dungeondart.myFactory;

import javax.security.auth.callback.Callback;

/**
 * Created by Darko on 22.11.2015.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public PackedLevel level;
    MyGameLoop myGameLoop;
    boolean runnable= false;
    boolean flipflop= true;
    float canvasZoom = 0;


    Tile test;

    public GamePanel(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initialize();

    }
    public GamePanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();

    }

    public GamePanel(Context context) {
        super(context);
        initialize();

    }

    void initialize()
    {
        myFactory.getInstance().TileNotMovable = BitmapFactory.decodeResource(getResources(), R.drawable.notmovabletile);
        myFactory.getInstance().TileMovable = BitmapFactory.decodeResource(getResources(), R.drawable.movabletile);
        myFactory.getInstance().Character = BitmapFactory.decodeResource(getResources(),R.drawable.character);
        myFactory.getInstance().resize();

        myGameLoop = new MyGameLoop(this);
        test = myFactory.getInstance().test_tile_1();
       // this.setFocusable(true);
        runnable = true;

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(runnable)
        {


            double surfaceSize = 0;
            if(this.getHeight()<this.getWidth())
            {
                surfaceSize = getHeight();
            }
            else
            {
                surfaceSize = getWidth();
            }

            double tileSize = 32;
            double scale =  surfaceSize/(tileSize * 9);
            System.out.print("THE scale is wrong , or the srufaceview"+scale + " THE VIEWSIZE : " + surfaceSize);
            canvasZoom = (float) scale;


            this.start();
            runnable = false;

        }

        return super.onTouchEvent(event);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }
    void start()
    {
        myGameLoop.setRunning(true);
        myGameLoop.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void tick(){
        level.tick();
    }

    public void pressedButton(String s)
    {
        switch (s)
        {
            case "right" :
            {
                level.cameraControl.move_right  = true;
                break;
            }
            case "left" :
            {
                level.cameraControl.move_left  = true;
                break;
            }
            case "up" :
            {
                level.cameraControl.move_up  = true;
                break;
            }
            case "down" :
            {
                level.cameraControl.move_down  = true;
                break;
            }
            default:
            {
                level.cameraControl.reset_movement();
                break;
            }

        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.scale(canvasZoom, canvasZoom, 1, 1);
        canvas.save();

        test.render(canvas, 0, 0);

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(3);
        canvas.drawRect(30, 30, 80, 80, paint);
        paint.setStrokeWidth(0);
        paint.setColor(Color.CYAN);
        canvas.drawRect(33, 60, 77, 77, paint);


        if(flipflop)
        {
            paint.setColor(Color.YELLOW);
            canvas.drawRect(33, 33, 77, 60, paint);
            flipflop = false;
        }
        else{
            paint.setColor(Color.BLUE);
            canvas.drawRect(33, 33, 77, 60, paint);
            flipflop = true;
        }

        if(level != null)
        {
            level.cameraControl.render(canvas);
            level.mainCharacter.render(canvas);

        }
        canvas.restore();
    }
}
