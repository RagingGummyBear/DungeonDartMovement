package com.grizzlypenguins.dungeondart.characters;

import com.grizzlypenguins.dungeondart.MyPoint;
import com.grizzlypenguins.dungeondart.myFactory;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Darko on 01.12.2015.
 */
public class MonsetNextStep implements Runnable {


    Algoritmi algoritmi = new Algoritmi();
    int [][] maze;
    MyPoint playerLocation;
    MyPoint monsterLocation;
    public MyPoint nextStep= new MyPoint(0,0);

    public boolean finished = false ;
    Thread thread;
    public boolean running = false;

    public MonsetNextStep(int[][] maze, MyPoint playerLocation, MyPoint monsterLocation) {
        this.maze = maze;
        this.playerLocation = playerLocation;
        this.monsterLocation = monsterLocation;
    }

    @Override
    public void run() {

        ArrayList <Jazol> temp = new ArrayList<>();
        temp = algoritmi.returnPath(maze,monsterLocation,playerLocation);
        nextStep = monsterLocation;
        nextStep.x = temp.get(0).index / myFactory.TILENUMBER;
        nextStep.y = temp.get(0).index % myFactory.TILENUMBER;
        nextStep.x -= monsterLocation.x;
        nextStep.y -= monsterLocation.y;

        finished = true;
        running=false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    synchronized public void start()
    {
        this.start();
        thread=new Thread(this);
        running=true;
        finished = false;
    }

    synchronized public void stop()
    {
        try {
            finished = false;
            running=false;
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
