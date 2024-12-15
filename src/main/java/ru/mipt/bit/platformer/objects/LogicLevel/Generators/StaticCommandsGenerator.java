package ru.mipt.bit.platformer.objects.LogicLevel.Generators;

import ru.mipt.bit.platformer.objects.LogicLevel.BulletLogModel;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.Command;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.DefaultDirectionMoveCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.MoveCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.ShootCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Direction;
import ru.mipt.bit.platformer.objects.LogicLevel.LogicLevel;
import ru.mipt.bit.platformer.objects.LogicLevel.TankLogModel;

import java.util.HashSet;
import java.util.Set;

import static ru.mipt.bit.platformer.objects.LogicLevel.Direction.randomDirection;
import static ru.mipt.bit.platformer.objects.LogicLevel.UtilsRandom.getRandomNumberUsingNextInt;

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
