package ru.mipt.bit.platformer.objects.LogicLevel.Generators;

import ru.mipt.bit.platformer.objects.LogicLevel.Commands.Command;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.MoveCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.ShootCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.Utils.Direction;
import ru.mipt.bit.platformer.objects.LogicLevel.LogicLevel;
import ru.mipt.bit.platformer.objects.LogicLevel.LogModels.TankLogModel;

import java.util.HashSet;
import java.util.Set;

import static ru.mipt.bit.platformer.objects.LogicLevel.Utils.Direction.randomDirection;
import static ru.mipt.bit.platformer.objects.LogicLevel.Utils.UtilsRandom.getRandomNumberUsingNextInt;

public class AICommandsGenerator implements CommandGenerator {

    LogicLevel level;
    final int FREQUENCY = 5;

    public AICommandsGenerator(LogicLevel level_) {
        level = level_;
    }

    public Set<Command> generate() {
        Set<Command> commands = new HashSet<>();
        Set<TankLogModel> enemies = Set.copyOf(level.getEnemies());

        for (TankLogModel tank: enemies) {
            Direction direction = randomDirection();
            if (level.tryMoveTank(tank, direction)) {
                commands.add(new MoveCommand(tank, direction));
            }
            if (getRandomNumberUsingNextInt(0, FREQUENCY) % FREQUENCY == 0) {
                commands.add(new ShootCommand(level, tank));
            }
        }

        return commands;
    }
}
