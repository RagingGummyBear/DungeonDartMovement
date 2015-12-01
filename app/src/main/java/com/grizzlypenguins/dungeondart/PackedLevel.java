package com.grizzlypenguins.dungeondart;

import android.graphics.Canvas;

import com.grizzlypenguins.dungeondart.CameraControl;
import com.grizzlypenguins.dungeondart.Difficulty;
import com.grizzlypenguins.dungeondart.LevelMap;
import com.grizzlypenguins.dungeondart.characters.EvilMonster;
import com.grizzlypenguins.dungeondart.characters.MainCharacter;
import com.grizzlypenguins.dungeondart.effects.Effect;
import com.grizzlypenguins.dungeondart.effects.PowerUpsAndTrapsBank;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Darko on 22.11.2015.
 */
public class PackedLevel implements Serializable {

    public PlayerScoring playerScoring;
    public Difficulty difficulty;
    public LevelMap levelMap;
    public CameraControl cameraControl;
    public MainCharacter mainCharacter;
    public TorchLight torchLight;
    public EvilMonster evilMonster;


    public boolean gameFinished = false;

    public PackedLevel(Difficulty difficulty, LevelMap levelMap, CameraControl cameraControl, MainCharacter mainCharacter,TorchLight torchLight,EvilMonster evilMonster) {
        this.difficulty = difficulty;
        this.levelMap = levelMap;
        this.cameraControl = cameraControl;
        this.mainCharacter = mainCharacter;
        this.torchLight = torchLight;
        this.evilMonster = evilMonster;
        playerScoring = new PlayerScoring();
    }

   public void tick() throws Exception {

        torchLight.tick();
       if(evilMonster.tick())
       {
           gameFinished = true;
           mainCharacter.alive = false;
           int temp1 = Math.abs(cameraControl.player_position.x - evilMonster.location.x);
           int temp2 = Math.abs(cameraControl.player_position.y - evilMonster.location.y);
           if(temp1 <4)
           {
               evilMonster.showing = true;
           }
           else
               if(temp2 <4)
               {
                   evilMonster.showing = true;
               }
           else
               {
                   evilMonster.showing = false;
               }
       }
        mainCharacter.tick();

        Tile temp = cameraControl.tick();
       if(temp !=null )
       {
           Effect powerUpEffect = PowerUpsAndTrapsBank.getInstance().get_PowerUp(temp.use_powerUp());
           Effect trapEffect = PowerUpsAndTrapsBank.getInstance().get_Trap(temp.use_trap());
           if(powerUpEffect!=null)
           {
               powerUpEffect.active = true;
               mainCharacter.effects.add(powerUpEffect);

           }
           if(trapEffect != null)
           {
               trapEffect.active = true;
               mainCharacter.effects.add(trapEffect);
           }

       }
       this.useEffects(mainCharacter.effects);

        if(cameraControl.moved)
        {
            cameraControl.tiles = levelMap.getShowingTiles(cameraControl.player_position);
        }
       cameraControl.calculateShadow((int) Math.floor(torchLight.intensity));

      if(temp!=null) if(temp.define == 5){
           gameFinished = true;
       }

    }

    public void render (Canvas c)
    {
        try{
            cameraControl.render(c);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        if(evilMonster.showing)
        {
            int temp1 = (cameraControl.player_position.x - evilMonster.location.x);
            int temp2 = (cameraControl.player_position.y - evilMonster.location.y);
            int plocation = (int) Math.floor(myFactory.TILENUMBER / 2);
            plocation++;
            temp1+=plocation;
            temp2+=plocation;
            evilMonster.render(c,temp1,temp2);

        }
        mainCharacter.render(c);
        torchLight.render(c);
    }

    public void shake_shake()
    {
        torchLight.shake_shake();
    }

    private void useEffects(ArrayList<Effect> lista)
    {
        for(int i=0;i<lista.size();i++)
        {
            use_effect(lista.get(i).name);
            lista.get(i).tick();
            if(!lista.get(i).active)
            {
                deactivate_effect(lista.get(i).name);
                lista.remove(i);
            }
        }
    }

    private void deactivate_effect(String s)
    {
        switch (s)
        {
            case "PlayerMovementSpeed" :
            {
                cameraControl.boost = 0;
                break;
            }
            case "PlayerStun":
            {
                mainCharacter.stunned = false;
                cameraControl.playerMovement = cameraControl.speed;
                break;
            }
            case "PlayerSlow":
            {
                cameraControl.boost = 0;
                mainCharacter.slowed = false;
                break;
            }
            default: break;
        }

    }


    private void use_effect(String s)
    {
        switch (s)
        {
            case "PlayerMovementSpeed" :
            {
                cameraControl.boost = 2;
                break;
            }
            case "PowerUpBonusPoints":
            {
                playerScoring.addPowerUpBonus(20);
                break;
            }
            case "PlayerStun":
            {
                mainCharacter.stunned = true;
                cameraControl.playerMovement = 10000000;
                break;
            }
            case "PlayerSlow":
            {
                mainCharacter.slowed = true;
                cameraControl.boost = -4;
                break;
            }
            case "TrapLowerTorch":
            {
                torchLight.intensity--;
                break;
            }
            case "PowerUpTorchHealth":
            {
                torchLight.life+=20;
                break;
            }
            default: break;
        }
    }

    public void finishGame()
    {
        long temp = System.nanoTime();
        playerScoring.calculateScore(difficulty.multiplier,temp,cameraControl.player_position,new MyPoint(1,1),torchLight.life, mainCharacter.alive);

    }
}
