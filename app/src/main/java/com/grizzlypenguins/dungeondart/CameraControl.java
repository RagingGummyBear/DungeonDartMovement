package com.grizzlypenguins.dungeondart;

import android.graphics.Canvas;
import android.graphics.Point;

import java.io.Serializable;

/**
 * Created by Darko on 17.11.2015.
 *
 */
public class CameraControl implements Serializable {


    //scaleX and scaleY are the player location in the bitmap
    double scaleX;
    double scaleY;
    public double tileSize;
    LevelMap levelMap;

    //player position
    public MyPoint player_position;
    public int playerMovement=1;
    int speed=1;

    //Displaying Tiles
    Tile [][] tiles;

    //moving information
    public boolean move_left= false;
    public boolean move_right = false;
    public boolean move_up = false;
    public boolean move_down = false;
    public boolean moved = false;


    public CameraControl(double scaleX, double scaleY,double Ts,MyPoint poz,int playerMovement) {
        tileSize = myFactory.TILESIZE;

        this.playerMovement = playerMovement;
        speed = playerMovement;
        player_position = new MyPoint(poz.x,poz.y);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public boolean show_monster(MyPoint p)
    {
        int x = p.x - player_position.x;
        int y = p.y - player_position.y;
        if(x<0)
        {
            x*=-1;
        }
        if(y<0)
        {
            y*=-1;
        }
        if(x<=4 || y<=4)
        {
            return true;
        }
        return false;
    }

    boolean movableTile(Tile t)
    {
        if(t.define>0)
            return true;
        else
            return false;
    }

    private Tile move()
    {
        int num = myFactory.TILENUMBER/2;
        if(move_left)
        {
            if(movableTile(tiles[num -1][num]))

            {
                player_position.x--;
                moved = true;
                return tiles[num -1][num];
            }
        }
        else
        if(move_right)
        {
            if(movableTile(tiles[num+1][num]))

            {
                player_position.x++;
                moved = true;
                return tiles[num+1][num];
            }
        }
        else
        if(move_down)
        {
            if(movableTile(tiles[num][num+1]))

            {
                player_position.y++;
                moved = true;
                return tiles[num][num+1];
            }

        }
        else
        if(move_up)
        {
            if(movableTile(tiles[num][num-1]))

            {
                player_position.y--;
                moved = true;
                return tiles[num][num-1];
            }

        }
        return null;
    }

    public  void reset_movement()
    {
        move_left= false;
        move_right = false;
        move_up = false;
        move_down = false;
        moved = false;
    }

    public Tile tick()
    {
        if(playerMovement == 0){
            playerMovement = speed;
            return this.move();
        }
        else
            playerMovement--;

        return null;

    }

    public void render (Canvas c)
    {
       if(moved) {

           moved = false;
       }
        for(int i=0;i<myFactory.TILENUMBER;i++)
        {
            for(int y=0;y<myFactory.TILENUMBER;y++) {
                tiles[i][y].render(c, (float) (i * tileSize), (float) (y * tileSize));
            }
        }
    }

}
