package com.grizzlypenguins.dungeondart;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Darko on 16.11.2015.
 */
public class LevelMap implements Serializable {


    public String mapName;
    public Tile tiles [][];  //tile [height][width]

   //public int tileNumber;
    boolean generatedMap = false;

    ArrayList<MyPoint> starts = new ArrayList<MyPoint>();    // start tiles
    ArrayList<MyPoint> finishs = new ArrayList<MyPoint>();   //finish tiles
    MyPoint start = new MyPoint(1,1);
    MyPoint end = new MyPoint(1,1);

    public  LevelMap (Tile Tiles [][],int tileNumber, int TileSize,String mapName)
    {

        this.mapName = mapName;
        tiles = Tiles;
        //width = Width;
        //height = Height;
//        this.tileNumber = tileNumber;


    }

    public LevelMap (LevelMap lm)
    {

        this.mapName = lm.mapName;
        this.tiles = lm.tiles;
        //this.width = lm.width;
        //this.tileNumber = lm.tileNumber;
        //this.height = lm.height;
        //this.tileSize = lm.tileSize;

        this.mapName = lm.mapName;
    }

    public  void createMap()
    {
        for(int i=0;i<tiles.length;i++)
        {
            for(int y=0;y<tiles[0].length;y++)
            {

                if(tiles[i][y].define == 2)
                {
                    // Implement the drawing of start and finish tiles
                    starts.add(new MyPoint(i, y));

                }
                if(tiles[i][y].define == 3)
                {
                    finishs.add(new MyPoint(i, y));
                }

            }

        }

       this.end = choose_End();
       this.start = choose_Start();
        this.generatedMap = true;

    }

    MyPoint choose_Start()
    {
        Collections.shuffle(starts);
        return starts.get(0);
    }

    MyPoint choose_End()
    {
        Collections.shuffle(starts);
        return finishs.get(0);
    }


    public Tile[][] getShowingTiles(MyPoint location)
    {

        int x = location.x;
        int y = location.y;
        Tile[][] temp = new Tile[myFactory.TILESIZE][myFactory.TILESIZE];

        for(int i=0;i<myFactory.TILESIZE;i++)
        {
            for(int z=0;z<myFactory.TILESIZE;z++)
            {
                temp[i][z] = this.tiles[x+i][y+z];
            }
        }

        return temp;

    }



}
