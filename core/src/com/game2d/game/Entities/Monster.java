package com.game2d.game.Entities;

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
        velocity = new Vector2(2, 0);
        defineMonster();
    }
    public abstract void hitHead();

    public abstract void update(float deltaTime);
    public void revVelocity(boolean x, boolean y){
        if (x){
            velocity.x=-velocity.x;
        }
        if (y){
            velocity.y=-velocity.y;
        }
    }

    protected abstract void defineMonster();
}
