package com.grizzlypenguins.dungeondart;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Created by Darko on 16.11.2015.
 */
public class myFactory {
    Rand rand = Rand.getInstance();
    public Bitmap TileMovable;
    public Bitmap TileNotMovable;
    public Bitmap TileStart;
    public Bitmap TileFinish;
    public Bitmap TileNFinish;
    public Bitmap TorchLight;
    public Bitmap Character;

    public Paint paint;

    public static final int TILESIZE = 32;
    public static final int TILENUMBER = 9;
    private static myFactory ourInstance = new myFactory();

    public static myFactory getInstance() {
        return ourInstance;
    }

    private myFactory() {
        paint = new Paint();
        paint.setAntiAlias(false);
        paint.setDither(true);
        paint.setFilterBitmap(false);


    }

    public LevelMap test_map_1()
    {
        int tileNum = 30;
        //(Tile Tiles [][],int tileNumber, int TileSize,String mapName)
        return new LevelMap(test_Tiles_1(tileNum),"TestMap1");
    }

    public Tile test_tile_1()
    {
        return new Tile(Rand.getInstance().random.nextInt(4),0,0);
    }

    public Tile[][] test_Tiles_1(int tileNumber)
    {
        Tile temp [] [] = new Tile[tileNumber][tileNumber];

        for(int i=0;i<tileNumber;i++)
        {
            for(int y=0; y < tileNumber ; y++)
            {
              //public Tile(int move, int pu, int t)
               if(i>10 && i <tileNumber-10 && y>10 && y<tileNumber-10) temp[i][y] = new Tile(Rand.getInstance().random.nextInt(4),0,0);
                else
               {
                   temp[i][y] = new Tile(0,0,0);
               }
            }
        }

        return temp;
    }

    // function used from stackoverflow for scaling bitmaps

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        //bm.recycle();

        return resizedBitmap;
    }

    public void resize()
    {
        if(TileMovable!=null)
        {
            TileMovable = getResizedBitmap(TileMovable,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(TileNotMovable!=null)
        {
            TileNotMovable = getResizedBitmap(TileNotMovable,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(Character!=null)
        {
            Character = getResizedBitmap(Character,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(TorchLight!=null)
        {
            TorchLight = getResizedBitmap(TorchLight,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(TileStart!=null)
        {
            TileStart = getResizedBitmap(TileStart,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(TileFinish!=null)
        {
            TileFinish = getResizedBitmap(TileFinish,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(TileNFinish!=null)
        {
            TileNFinish = getResizedBitmap(TileNFinish,myFactory.TILESIZE,myFactory.TILESIZE);
        }

    }

    public int [][] get_MovementMap(Tile [][]tiles)
    {
        int temp [][] = new int [tiles.length] [tiles[0].length];

        for(int i=0;i<tiles.length;i++)
        {
            for(int y=0;y<tiles[0].length;y++)
            {
                temp[i][y]= tiles[i][y].define;
            }
        }
        return temp;
    }



}
