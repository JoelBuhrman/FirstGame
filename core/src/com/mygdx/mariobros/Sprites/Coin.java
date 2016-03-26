package com.mygdx.mariobros.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by JoelBuhrman on 16-03-23.
 */
public class Coin extends InterActiveTileObject {


    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin", "Collision");
    }

    public Coin(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
    }
}
