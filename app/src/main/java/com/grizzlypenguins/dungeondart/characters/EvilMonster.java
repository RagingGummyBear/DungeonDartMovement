package com.grizzlypenguins.dungeondart.characters;

import android.graphics.Canvas;

import com.grizzlypenguins.dungeondart.effects.Effect;
import com.grizzlypenguins.dungeondart.MyPoint;
import com.grizzlypenguins.dungeondart.myFactory;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Darko on 21.11.2015.
 */
public class EvilMonster implements Serializable{

    public int [][]movementMap;
    public MyPoint location = new MyPoint(0,0);
    public MyPoint nextStep = new MyPoint(0,0);
    public MyPoint playerLocation = new MyPoint(0,0);


    private boolean runAlgo = false;


    public int speed;
    int move = 1;

    public boolean showing = false;
    int num_animation = 5;
    public int facingSide = 0;  //0 = right, 1 = down, 2 = right, 3 = up

    public ArrayList<Effect> effects = new ArrayList<Effect>();

    public EvilMonster(MyPoint location, int speed,int [][]map) {

        movementMap = map;

        this.location = location;
        this.speed = speed;

    }

    public EvilMonster(int[][] movementMap, MyPoint location, MyPoint playerLocation, int speed) {
        this.movementMap = movementMap;
        this.location = location;
        this.playerLocation = playerLocation;
        this.speed = speed;
        this.move = speed;
        myFactory.getInstance().monsetNextStep =new MonsetNextStep(movementMap,location,playerLocation);
    }

    public boolean tick()
    {
        if(move == speed) {
            runAlgo = !myFactory.getInstance().monsetNextStep.running;
            if (myFactory.getInstance().monsetNextStep.finished) {
                this.nextStep = myFactory.getInstance().monsetNextStep.nextStep;

            } else
                nextStep = new MyPoint(0, 0);

            if (runAlgo) {
                myFactory.getInstance().monsetNextStep.start();
            }
            move = 0;
            return step();
        }
        move++;
        return false;
    }

    public boolean step ()
    {
        location.y+=nextStep.y;
        location.x+=nextStep.x;
        if(location == playerLocation)
        {
            return true;
        }
        else return false;
    }

    public void render(Canvas c,float x,float y)
    {
        if(showing)
        {
            c.drawBitmap(myFactory.getInstance().EvilMonster,x,y,myFactory.getInstance().paint);
            switch (num_animation)
            {
                case 1:
                {

                    ++num_animation;
                    break;
                }
                case 2:
                {

                    ++num_animation;
                    break;
                }
                case 3:
                {

                    ++num_animation;
                    break;
                }
                case 4:
                {

                    ++num_animation;
                    break;
                }
                case 5:
                {

                    num_animation = 0;
                    break;
                }
                default:
                {
                    num_animation = 0;
                    break;
                }

            }

        }
        else
        {
            num_animation = 0;
        }


    }


}
