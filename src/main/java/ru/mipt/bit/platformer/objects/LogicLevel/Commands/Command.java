package ru.mipt.bit.platformer.objects.LogicLevel.Commands;

import ru.mipt.bit.platformer.objects.LogicLevel.TankLogModel;

public interface Command {
    boolean run(TankLogModel tank);
}
