package com.game2d.game.Entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.game2d.game.Displays.PlayerScreen;
import com.game2d.game.Game2D;

public class Hero extends Sprite {

    public enum State {FALL,JUMP,STAND,RUN};
    public State currentState;
    public State previousState;
    public World world;
    private TextureRegion heroidle;
    public Body body;
    private Animation characterRun;
    private Animation characterJump;
    private float stateTimer;
    private boolean runRight;
    public Hero (World world, PlayerScreen screen){

        super(screen.getAtlas().findRegion("mainhero"));
        this.world= world;
        currentState = State.STAND;
        previousState = State.STAND;
        stateTimer = 0;
        runRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i<5; i++){
            frames.add(new TextureRegion(getTexture(),i*105, 0 , 105, 100));
        }
        characterRun = new Animation(0.1f, frames);
        frames.clear();
        for(int i = 1; i<5; i++){
            frames.add(new TextureRegion(getTexture(),i*105,0,105,100));
        }
        characterJump = new Animation(0.1f,frames);

        defineHero();
        heroidle= new TextureRegion(getTexture(), 10, 1, 80, 100);
        setBounds(1, 1, 100/Game2D.PPM, 100/Game2D.PPM);
        setRegion(heroidle);


    }

    public void update(float deltatime){
        setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y-getHeight()/2);
        setRegion(getFrame(deltatime));
    }

    public TextureRegion getFrame(float deltatime){
        currentState = getState();
        TextureRegion region;
        switch(currentState){
            case JUMP:
                region = (TextureRegion) characterJump.getKeyFrame(stateTimer);
                break;
            case RUN:
                region = (TextureRegion) characterRun.getKeyFrame(stateTimer,true);
                break;
            case FALL:
            case STAND:
            default:
                region = heroidle;
                break;

        } if((body.getLinearVelocity().x<0 || !runRight) && !region.isFlipX()){
            region.flip(true,false);
            runRight = false;
        } else if((body.getLinearVelocity().x>0 || runRight) && region.isFlipX()){
            region.flip(true,false);
            runRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + deltatime : 0;
        previousState = currentState;
        return region;
    }

    public State getState(){
        if(body.getLinearVelocity().y>0 ||(body.getLinearVelocity().y<0 && previousState == State.JUMP)){
            return State.JUMP;
        } else if(body.getLinearVelocity().y<0){
            return State.FALL;
        } else if(body.getLinearVelocity().x!=0){
            return State.RUN;
        }else {
            return State.STAND;
        }
    }
    private void defineHero() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(90/Game2D.PPM,550/Game2D.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(50/Game2D.PPM);
        fixtureDef.shape=shape;
        body.createFixture(fixtureDef);

    }
}

