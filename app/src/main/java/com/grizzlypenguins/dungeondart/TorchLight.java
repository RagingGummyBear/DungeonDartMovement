package com.grizzlypenguins.dungeondart;

import android.graphics.Canvas;

import java.io.Serializable;

/**
 * Created by Darko on 23.11.2015.
 */
public class TorchLight implements Serializable {

    float intensity;
    public float decrease;
    int timeToDecrease = 100;
    int counter = 2;
    public float life=100;
    float damage = 1;


    public TorchLight(float decrease,float damage)
    {
        intensity = 3;
        this.decrease = decrease;
        this.damage = damage;
    }

    public void tick()
    {
       if(counter == 0)
       {
           intensity-=decrease;
           counter = timeToDecrease;
           if(intensity<=0)intensity = 0;
       }
        else
           --counter;

    }

    public boolean increaseLight(float f)
    {
        intensity+=f;

        if(intensity>=(int)(myFactory.TILENUMBER/2)-2)
        {
            life-=damage;
           if(intensity>=(myFactory.TILENUMBER/2)) intensity = myFactory.TILENUMBER;
            return true;
        }
        else
            return false;
    }

    public void render(Canvas c)
    {
        int start = (int) Math.floor(myFactory.TILENUMBER / 2);
        start ++;
        int end = start;
        //--end;
        start -= intensity;
        end += intensity;

        for(int i=start;i<end;i++)
        {
            for(int y=start;y<end;y++)
            {
                if(i == start)
                {
                    c.drawBitmap(myFactory.getInstance().TorchLight,y*myFactory.TILESIZE,i*myFactory.TILESIZE,myFactory.getInstance().paint);
                }
                else
                {
                    if(i==end-1)
                    {

                        c.drawBitmap(myFactory.getInstance().TorchLight,y*myFactory.TILESIZE,i*myFactory.TILESIZE,myFactory.getInstance().paint);
                    }
                    else
                    {

                        c.drawBitmap(myFactory.getInstance().TorchLight,start*myFactory.TILESIZE,i*myFactory.TILESIZE,myFactory.getInstance().paint);
                        c.drawBitmap(myFactory.getInstance().TorchLight,(end-1)*myFactory.TILESIZE,i*myFactory.TILESIZE,myFactory.getInstance().paint);
                        y = end;

                    }
                }
            }
        }


    }
}
