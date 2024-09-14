package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class Map {

    private final TiledMap level;
    private final MapRenderer levelRenderer;
    private TiledMapTileLayer groundLayer;

    public Map(Batch batch, String pathLevelPng) {

        // load level tiles
        level = new TmxMapLoader().load(pathLevelPng);
        levelRenderer = createSingleLayerMapRenderer(level, batch);
    }

    public TileMovement createTileMovement() {
        groundLayer = getSingleLayer(level);
        return new TileMovement(groundLayer, Interpolation.smooth);
    }

    public void render() {
        // render each tile of the level
        levelRenderer.render();
    }
    public TiledMapTileLayer getGroundLayer() {
        return groundLayer;
    }

    public void dispose() {
        level.dispose();
    }
}
