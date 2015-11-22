package com.grizzlypenguins.dungeondart.effects;

/**
 * Created by Darko on 21.11.2015.
 */
public abstract class Effect {

    public String name;
    int length;
    public boolean active ;

    abstract void render();

    public Effect(int length, String name, boolean active) {
        this.length = length;
        this.name = name;
        this.active = active;
    }

    public void tick()
    {
        --length;
        if(length<=0) active = false;
    }

    public void destroy()
    {
        this.destroy();
    }

}
