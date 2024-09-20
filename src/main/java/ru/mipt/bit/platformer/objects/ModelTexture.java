package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ModelTexture {

    public final com.badlogic.gdx.graphics.Texture Texture_;
    public final TextureRegion TextureRegion_;

    public ModelTexture(String pathTreePng) {

        Texture_ = new com.badlogic.gdx.graphics.Texture(pathTreePng);
        TextureRegion_ = new TextureRegion(Texture_);
    }
}
