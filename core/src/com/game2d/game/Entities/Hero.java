package com.game2d.game.Entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.game2d.game.Displays.PlayerScreen;
import com.game2d.game.Game2D;

public class Hero extends Sprite {

    public enum State {FALL,JUMP,STAND,RUN,DEAD};
    public State currentState;
    public State previousState;
    public World world;
    private TextureRegion heroidle;
    private TextureRegion characterDead;
    public Body body;
    private Animation<TextureRegion> characterRun;
    private Animation<TextureRegion> characterJump;
    private float stateTimer;
    private boolean runRight;
    private boolean characterisdead;

    private Music music;
    public Hero (PlayerScreen screen){

        super(screen.getAtlas().findRegion("mainhero"));
        this.world= screen.getWorld();
        currentState = State.STAND;
        previousState = State.STAND;
        stateTimer = 0;
        runRight = true;


        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 5; i<11; i++){
            frames.add(new TextureRegion(getTexture(),i*48, 0 , 48, 48));
        }
        characterRun = new Animation(0.1f, frames);
        frames.clear();
        for(int i = 11; i<16; i++){
            frames.add(new TextureRegion(getTexture(),i*48,0,48,48));
        }
        characterJump = new Animation(0.1f,frames);
        characterDead = new TextureRegion(screen.getAtlas().findRegion("deadhero"), 1, 1, 48, 48);


        defineHero();
        heroidle= new TextureRegion(getTexture(), 1, 1, 48, 48);
        setBounds(1, 1, 100/Game2D.PPM, 100/Game2D.PPM);
        setRegion(heroidle);


    }

    public void update(float deltatime){
        setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y-getHeight()/2);
        setRegion(getFrame(deltatime));
        if (body.getPosition().y<0 && !characterisdead){
            body.setLinearVelocity(0,0);
            body.getPosition().y=0;
            hit();
        }
    }

    public boolean dead(){
        return characterisdead;
    }
    public float getStateTimer(){
        return stateTimer;
    }

    public TextureRegion getFrame(float deltatime){
        currentState = getState();
        TextureRegion region;
        switch(currentState){
            case DEAD:
                region = characterDead;
                break;
            case JUMP:
                music = Game2D.assetManager.get("Sounds/Jump.ogg", Music.class);
                music.setVolume(0.3f);
                music.setLooping(false);
                music.play();

                region = characterJump.getKeyFrame(stateTimer);
                break;
            case RUN:
                region =  characterRun.getKeyFrame(stateTimer,true);
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
        if(characterisdead){
            return State.DEAD;
        }
        else if(body.getLinearVelocity().y>0 ||(body.getLinearVelocity().y<0 && previousState == State.JUMP)){
            return State.JUMP;
        } else if(body.getLinearVelocity().y<0){
            return State.FALL;
        } else if(body.getLinearVelocity().x!=0){
            return State.RUN;
        }else {
            return State.STAND;
        }
    }

    public void hit(){
        Game2D.assetManager.get("Sounds/Soundtrack.ogg", Music.class).stop();
        characterisdead = true;
        Filter filter = new Filter();
        filter.maskBits = Game2D.NOTHINGBIT;
        for(Fixture fixture : body.getFixtureList()){
            fixture.setFilterData(filter);
        } body.applyLinearImpulse(new Vector2(0,4f), body.getWorldCenter(), true);
    }
    private void defineHero() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(100/Game2D.PPM,550/Game2D.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(50/Game2D.PPM);
        fixtureDef.shape=shape;
        fixtureDef.filter.maskBits= Game2D.FLOORBIT|
                Game2D.MONSTERBIT|
                Game2D.TREEBIT|
                Game2D.MONSTERHEADBIT;                ;

        fixtureDef.filter.categoryBits= Game2D.HEROBIT;
        body.createFixture(fixtureDef).setUserData(this);

    }
}

