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

    public boolean finished = true ;
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
        System.out.println("MonsterHunt running");
        temp = algoritmi.returnPath(maze,monsterLocation,playerLocation);
        nextStep = monsterLocation;
        nextStep.x = (int)Math.floor(temp.get(0).index / maze.length);
        nextStep.y =  (int)Math.floor(temp.get(0).index % maze[0].length);
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
        try {
            this.run();
            thread = new Thread(this);
            running = true;
            finished = false;
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }
    }

    synchronized public void stop()
    {
        try {
            finished = true;
            nextStep = new MyPoint(0,0);
            running=false;
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
