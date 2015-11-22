package com.grizzlypenguins.dungeondart;

import com.grizzlypenguins.dungeondart.characters.MainCharacter;

/**
 * Created by Darko on 17.11.2015.
 */
public class Level implements Runnable{
    Thread thread;
    public boolean running=false;

    public Difficulty difficulty;
    public LevelMap levelMap;
    public CameraControl cameraControl;
    public MainCharacter mainCharacter;
    public PackedLevel packedLevel;

    //public EvilMonster evilMonster;


    public int screenWidth;
    public int screenHeight;


    public Level(Difficulty d,LevelMap lm,int screenWidth,int screenHeight)
    {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        difficulty = d;
        levelMap = lm;

    }

    @Override
    public void run() {

        System.out.println("Thread running");
        //Difficulty
        difficulty.calculateDifficulty();
        //Creation of LevelMap, with
        levelMap.createMap();

        //Create the character

        mainCharacter = new MainCharacter(levelMap.start,difficulty.playerSpeed);

        //Information for camera

        double cameraZoom;
        double tileSize;
        int numTiles;
        int screenSize;
        int movementSpeed;
        if(screenHeight > screenWidth)
            screenSize = screenWidth;
                else
            screenSize = screenHeight;

        numTiles = 9;

        tileSize = (double)screenSize/numTiles;

        cameraZoom =  (float)screenSize / (float)(tileSize * 9);

        movementSpeed = mainCharacter.speed ;

        cameraControl = new CameraControl(cameraZoom,cameraZoom,tileSize,levelMap.start,movementSpeed);
        cameraControl.levelMap = levelMap;
        cameraControl.tiles = levelMap.getShowingTiles(levelMap.start);

        // CreateMonster

        System.out.println("GENERATING FINISHED");
        // public PackedLevel(Difficulty difficulty, LevelMap levelMap, CameraControl cameraControl, MainCharacter mainCharacter)

        //ke treba da se dodade i evilMonster
        packedLevel = new PackedLevel(difficulty,levelMap,cameraControl,mainCharacter);


        running=false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // TODO Auto-generated method stub


    }

    public synchronized void start(){
        thread = new Thread(this);
        running = true;
       // thread.start();
       System.out.println("Thread started from within");
        thread.run();

    }
    synchronized void stop()
    {
        try {
            running=false;
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
