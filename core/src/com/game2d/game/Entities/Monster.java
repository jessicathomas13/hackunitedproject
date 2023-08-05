package com.game2d.game.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.game2d.game.Displays.PlayerScreen;

public abstract class Monster extends Sprite {
    protected PlayerScreen screen;
    public Body body;
    protected World world;
    public Monster(PlayerScreen screen, float x, float y){
        this.world= screen.getWorld();
        this.screen= screen;
        setPosition(x,y);
        defineMonster();
    }

    protected abstract void defineMonster();
}
