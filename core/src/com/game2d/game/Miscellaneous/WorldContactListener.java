package com.game2d.game.Miscellaneous;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.game2d.game.Entities.Dragon;
import com.game2d.game.Entities.InteractiveObject;
import com.game2d.game.Entities.Monster;
import com.game2d.game.Game2D;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        int collisionDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        if(fixA.getUserData() == "head" || fixB.getUserData() == "head"){
            Fixture head = fixA.getUserData() == "head" ? fixA: fixB;
            Fixture object = head == fixA ? fixB: fixA;

            if(object.getUserData() != null && InteractiveObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveObject)object.getUserData()).headHit();

            }
        }
        switch (collisionDef){
            case Game2D.MONSTERHEADBIT | Game2D.HEROBIT:
                if (fixA.getFilterData().categoryBits == Game2D.MONSTERHEADBIT){
                    ((Monster)fixA.getUserData()).hitHead();
                }
                else if (fixB.getFilterData().categoryBits== Game2D.MONSTERHEADBIT){
                    ((Monster)fixB.getUserData()).hitHead();
                }
        }


    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
