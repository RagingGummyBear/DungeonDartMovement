package com.grizzlypenguins.dungeondart;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.grizzlypenguins.dungeondart.characters.MonsetNextStep;
import com.grizzlypenguins.dungeondart.effects.PowerUpBonusPoints;
import com.grizzlypenguins.dungeondart.effects.PowerUpMovementSpeed;
import com.grizzlypenguins.dungeondart.effects.PowerUpTorchHealth;
import com.grizzlypenguins.dungeondart.effects.TrapLowerTorch;
import com.grizzlypenguins.dungeondart.effects.TrapSlow;
import com.grizzlypenguins.dungeondart.effects.TrapStun;

import java.util.Random;

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
    public Bitmap EvilMonster;
    public MonsetNextStep monsetNextStep;

    public Bitmap PowerUpR; //speed
    public Bitmap PowerUpB; //torchHealth
    public Bitmap PowerUpG; //higherTorchIntensity
    public Bitmap PowerUpP; //stunMonster
    public Bitmap PowerUpY; //bonus points

    public Bitmap TrapR; //Stun
    public Bitmap TrapY; //Slow
    public Bitmap TrapB; //lowerTorch

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
               if(i>10 && i <tileNumber-10 && y>10 && y<tileNumber-10) temp[i][y] = new Tile(Rand.getInstance().random.nextInt(4),Rand.getInstance().random.nextInt(6),Rand.getInstance().random.nextInt(6));
                else
               {
                   temp[i][y] = new Tile(0,0,0);
               }
            }
        }
        return temp;
    }

    //defines the tile with : 0 wall,1 movable,2 start,3 finish, 4 choosenStart,5 working exit, 6 not working exit, 7 monsterDen
    public Tile newStartTile()
    {
        return new Tile(3,Rand.getInstance().random.nextInt(10),Rand.getInstance().random.nextInt(10));
    }

    public Tile newFinishTile()
    {
        return new Tile(4,Rand.getInstance().random.nextInt(10),Rand.getInstance().random.nextInt(10));
    }
    public Tile newMovableTile()
    {
        return new Tile(0,Rand.getInstance().random.nextInt(20),Rand.getInstance().random.nextInt(20));
    }

    public Tile newMonsterDenTile()
    {
        return new Tile(7,0,0);
    }

    public Tile newWallTile()
    {
        return new Tile(0,0,0);
    }

    public LevelMap test_map_2(int i)
    {
        int tileNum = i;
        //(Tile Tiles [][],int tileNumber, int TileSize,String mapName)
        return new LevelMap(test_Tiles_2(120),"TestMap2");
    }

    public Tile[][] test_Tiles_2(int tileNumber)
    {
        Tile temp [] [] = new Tile[tileNumber][tileNumber];

        for(int i=0;i<tileNumber;i++)
        {
            for(int y=0; y < tileNumber ; y++)
            {
                if(i>10 && i < tileNumber-10 && y>10 && y<tileNumber-10)
                {
                    temp[i][y] = newMovableTile();
                }
                else  temp[i][y] = newWallTile();
                //public Tile(int move, int pu, int t)
            }
        }

        int x,y;
        tileNumber -= 20;
        while(true) {
            x = Rand.random.nextInt((tileNumber- 20) + 1) + 20;
            y = Rand.random.nextInt((tileNumber - 20) + 1) + 20;
            if (temp[x][y].define < 3) {
                temp[x][y] = newStartTile();
                break;
            }
        }  while(true) {
        x = Rand.random.nextInt((tileNumber- 20) + 1) + 20;
        y = Rand.random.nextInt((tileNumber - 20) + 1) + 20;
        if (temp[x][y].define < 3) {
            temp[x][y] = newFinishTile();
            break;
        }
    }  while(true) {
        x = Rand.random.nextInt((tileNumber- 20) + 1) + 20;
        y = Rand.random.nextInt((tileNumber - 20) + 1) + 20;
        if (temp[x][y].define < 3) {
            temp[x][y] = newMonsterDenTile();
            break;
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

        if(PowerUpR!=null)
        {
            PowerUpR = getResizedBitmap(PowerUpR,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(PowerUpB!=null)
        {
            PowerUpB = getResizedBitmap(PowerUpB,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(PowerUpG!=null)
        {
            PowerUpG = getResizedBitmap(PowerUpG,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(PowerUpY!=null)
        {
            PowerUpY = getResizedBitmap(PowerUpY,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(PowerUpP!=null)
        {
            PowerUpP = getResizedBitmap(PowerUpP,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(TrapR!=null)
        {
            TrapR = getResizedBitmap(TrapR,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(TrapY!=null)
        {
            TrapY = getResizedBitmap(TrapY,myFactory.TILESIZE,myFactory.TILESIZE);
        }

        if(TrapB!=null)
        {
            TrapB = getResizedBitmap(TrapB,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(EvilMonster != null)
        {
            EvilMonster = getResizedBitmap(EvilMonster,myFactory.TILESIZE,myFactory.TILESIZE);
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


    public TrapStun newTrapStun()
    {
        return new TrapStun(100,"PlayerStun",false);
    }
    public TrapSlow newTrapSlow()
    {
        return new TrapSlow(200,"PlayerSlow",false);
    }
    public TrapLowerTorch newTrapLowerTorch()
    {
        return new TrapLowerTorch(1,"TrapLowerTorch",false);
    }


    public PowerUpMovementSpeed newPowerUpMovementSpeed()
    {
        return new PowerUpMovementSpeed(500,"PlayerMovementSpeed",false);
    }
    public PowerUpBonusPoints newPowerUpBonusPoints() {
        return new PowerUpBonusPoints(1,"PowerUpBonusPoints",false);
    }
    public PowerUpTorchHealth newPowerUpTorchHealth() {
        return new PowerUpTorchHealth(1,"PowerUpTorchHealth",false);
    }


}
