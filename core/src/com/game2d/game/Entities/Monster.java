package com.game2d.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.game2d.game.Displays.PlayerScreen;

public abstract class Monster extends Sprite {
    protected PlayerScreen screen;
    public Body body;
    public Vector2 velocity;
    protected World world;
    public Monster(PlayerScreen screen, float x, float y){
        this.world= screen.getWorld();
        this.screen= screen;
        setPosition(x,y);
        velocity = new Vector2(1, 0);
        defineMonster();
    }
    public abstract void hitHead();
    public void revVelocity(boolean x, boolean y){
        if (x){
                velocity.x*=-1;

        }
        if (y){
            velocity.y=-1;
        }
    }

    protected abstract void defineMonster();
}
