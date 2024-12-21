package ru.mipt.bit.platformer.objects.LogicLevel.LogModels;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public interface LogModel {

    public void movementProgess(float deltaTime, float movement_speed);
}
