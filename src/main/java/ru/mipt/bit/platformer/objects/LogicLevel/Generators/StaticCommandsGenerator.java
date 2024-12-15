package ru.mipt.bit.platformer.objects.LogicLevel.Generators;

import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.BulletLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.Command;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.DefaultDirectionMoveCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.LogicLevel;

import java.util.HashSet;
import java.util.Set;

public class StaticCommandsGenerator {

    public static Set<Command> generateStaticCommands(Set<BulletLogModel> bullets, LogicLevel level) {
        Set<Command> commands = new HashSet<>();

        for (BulletLogModel bullet: bullets) {
            if (level.tryMoveBullet(bullet)) {
                commands.add(new DefaultDirectionMoveCommand(bullet));
            }
        }

        return commands;
    }

}
