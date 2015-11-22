package com.grizzlypenguins.dungeondart;

import java.io.Serializable;

/**
 * Created by Darko on 16.11.2015.
 * It requires more balancing
 */
public class Difficulty implements Serializable {

    public int starNumber;
    public int monsterSpeed;
    public int playerSpeed;
    public double multiplier;    // the multiplier is for the score
    public double torchDecrease;

    public Difficulty(int numb)
    {
        starNumber=numb;
        this.calculateDifficulty();
    }
    public  Difficulty ()
    {
        starNumber = 1;
        this.calculateDifficulty();
    }

    void calculateDifficulty()
    {

        switch (starNumber) {
            case 0:
            {

                monsterSpeed = 4;
                playerSpeed = 3;
                multiplier = 1;  // no bonus points
                torchDecrease = 0.8;
                break;

            }
            case 1: {

                monsterSpeed = 3;
                playerSpeed = 3;
                multiplier = 1.2;  //bonus 0.2% points
                torchDecrease = 1;
                break;

            }
            case 2: {

                monsterSpeed = 2;
                playerSpeed = 2;
                multiplier = 1.5; //bonus 0.5% points
                torchDecrease = 1.5;
                break;

            }
            default: {

                monsterSpeed = 3;
                playerSpeed = 3;
                multiplier = 1.2;
                torchDecrease = 1;
                break;

            }
        }
    }



}
