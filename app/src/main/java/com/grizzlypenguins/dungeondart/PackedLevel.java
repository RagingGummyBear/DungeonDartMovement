package com.grizzlypenguins.dungeondart;

import com.grizzlypenguins.dungeondart.CameraControl;
import com.grizzlypenguins.dungeondart.Difficulty;
import com.grizzlypenguins.dungeondart.LevelMap;
import com.grizzlypenguins.dungeondart.characters.MainCharacter;

import java.io.Serializable;

/**
 * Created by Darko on 22.11.2015.
 */
public class PackedLevel implements Serializable {


    public Difficulty difficulty;
    public LevelMap levelMap;
    public CameraControl cameraControl;
    public MainCharacter mainCharacter;

    public PackedLevel(Difficulty difficulty, LevelMap levelMap, CameraControl cameraControl, MainCharacter mainCharacter) {
        this.difficulty = difficulty;
        this.levelMap = levelMap;
        this.cameraControl = cameraControl;
        this.mainCharacter = mainCharacter;
    }

   public void tick()
    {
        mainCharacter.tick();
        cameraControl.tick();
        if(cameraControl.moved)
        {
            cameraControl.tiles = levelMap.getShowingTiles(cameraControl.player_position);
        }
    }
}
