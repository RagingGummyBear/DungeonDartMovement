package com.grizzlypenguins.dungeondart.characters;

import com.grizzlypenguins.dungeondart.effects.Effect;
import com.grizzlypenguins.dungeondart.MyPoint;

import java.util.ArrayList;

/**
 * Created by Darko on 21.11.2015.
 */
public class EvilMonster {

    public boolean [][]movementMap;
    public MyPoint location = new MyPoint(0,0);
    public MyPoint nextStep = new MyPoint(0,0);


    public int speed;
    int move = 1;

    public boolean showing = false;
    int num_animation = 5;
    public int facingSide = 0;  //0 = right, 1 = down, 2 = right, 3 = up

    public ArrayList<Effect> effects = new ArrayList<Effect>();

    public EvilMonster(MyPoint location, int speed,boolean [][]map) {

        movementMap = map;
        this.location = location;
        this.speed = speed;

    }

    public void tick()
    {

        for(int i=0;i<effects.size();i++)
        {
            effects.get(i).tick();
            if(!effects.get(i).active)
            {
                effects.remove(i);
            }
        }
        if(nextStep.x == nextStep.y && nextStep.y == 0)
        {
            find_next_step();
            if(nextStep.x >0 )
            {
               facingSide = 0;

            }
            else
            {
                if( nextStep.x < 0 )
                {
                    facingSide = 2;
                }
                else
                {
                    if (nextStep.y<0)
                    {
                        facingSide = 3;
                    }
                    else
                        facingSide = 1;
                }
            }
        }
    }

    void find_next_step()
    {

    }

    void step ()
    {
        location.y+=nextStep.y;
        location.x+=nextStep.x;
    }

    public void render()
    {
        if(showing)
        {
            switch (num_animation)
            {
                case 1:
                {

                    ++num_animation;
                }
                case 2:
                {

                    ++num_animation;
                }
                case 3:
                {

                    ++num_animation;
                }
                case 4:
                {

                    ++num_animation;
                }
                case 5:
                {

                    num_animation = 0;
                }
                default:
                {
                    num_animation = 0;
                }

            }

        }
        else
        {
            num_animation = 0;
        }


    }


}
