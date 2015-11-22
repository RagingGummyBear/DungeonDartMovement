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
    public Bitmap Character;

    public Paint paint;

    public static int TILESIZE = 32;
    public static int TILENUMBER = 9;
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
        int tileNum = 120;
        int tileSize = 32;
        //(Tile Tiles [][],int tileNumber, int TileSize,String mapName)
        return new LevelMap(test_Tiles_1(tileNum),tileNum,tileSize,"TestMap1");
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
                temp[i][y] = new Tile(Rand.getInstance().random.nextInt(4),0,0);

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
            TileMovable = getResizedBitmap(TileMovable,32,32);
        }
        if(TileNotMovable!=null)
        {
            TileNotMovable = getResizedBitmap(TileNotMovable,32,32);
        }
        if(Character!=null)
        {
            Character = getResizedBitmap(Character,32,32);
        }
    }



}
