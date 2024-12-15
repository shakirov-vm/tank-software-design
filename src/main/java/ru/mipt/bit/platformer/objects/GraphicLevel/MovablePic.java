package ru.mipt.bit.platformer.objects.GraphicLevel;

import ru.mipt.bit.platformer.util.TileMovement;

public interface MovablePic {
    void movePic(TileMovement tileMovement);
    void changeMovementProgess(float deltaTime, float MOVEMENT_SPEED);
}
