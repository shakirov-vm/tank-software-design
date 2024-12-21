package ru.mipt.bit.platformer.objects.LogicLevel.Generators;

import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.BulletLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.Command;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.DefaultDirectionMoveCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.LogicLevel;

import java.util.HashSet;
import java.util.Set;

public class BulletCommandsGenerator implements CommandGenerator {

    LogicLevel level;

    public BulletCommandsGenerator(LogicLevel level_) {
        level = level_;
    }

    public Set<Command> generate() {
        Set<Command> commands = new HashSet<>();
        Set<BulletLogModel> bullets = Set.copyOf(level.getBullets());

        for (BulletLogModel bullet: bullets) {
            if (level.tryMoveBullet(bullet)) {
                commands.add(new DefaultDirectionMoveCommand(bullet));
            }
        }

        return commands;
    }

}
