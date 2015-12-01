package com.grizzlypenguins.dungeondart.GameLoop;

import com.grizzlypenguins.dungeondart.MyPoint;
import com.grizzlypenguins.dungeondart.characters.AStarAlgorithm.AStar;
import com.grizzlypenguins.dungeondart.characters.AStarAlgorithm.AreaMap;
import com.grizzlypenguins.dungeondart.characters.AStarAlgorithm.heuristics.AStarHeuristic;
import com.grizzlypenguins.dungeondart.characters.AStarAlgorithm.heuristics.ClosestHeuristic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Darko on 01.12.2015.
 */
public class FindNextStep implements Runnable {
    AreaMap map;
    AStar aStar;
    AStarHeuristic heuristic = new ClosestHeuristic();

    int [][] maze;
    public boolean finished = true;

    public  MyPoint playerLocation;
    public MyPoint monsterLocation;

    public MyPoint nextStep= new MyPoint(0,0);

    public FindNextStep(int[][] maze, MyPoint playerLocation, MyPoint monsterLocation)
    {
        this.maze = maze;
        this.playerLocation = playerLocation;
        this.monsterLocation = monsterLocation;
        map = new AreaMap(maze[0].length, maze.length, maze);
        aStar = new AStar(map, heuristic);
    }

    @Override
    public void run() {
        finished = false;

        ArrayList<MyPoint> shortestPath = aStar.calcShortestPath(monsterLocation.x, monsterLocation.y, playerLocation.x, playerLocation.y);
       if(shortestPath!=null)
        {
            System.out.println("x:  "+shortestPath.get(0).x+" y: "+shortestPath.get(0).y);
            nextStep.x = shortestPath.get(0).x-monsterLocation.x;
            nextStep.y = shortestPath.get(0).y-monsterLocation.y;
        }
        finished = true;

    }



}
