package ru.mipt.bit.platformer.objects.LogicLevel.Utils;

import ru.mipt.bit.platformer.objects.LogicLevel.Commands.Command;
import ru.mipt.bit.platformer.objects.LogicLevel.Generators.AICommandsGenerator;
import ru.mipt.bit.platformer.objects.LogicLevel.Generators.TapHandler;
import ru.mipt.bit.platformer.objects.LogicLevel.LogicLevel;

import java.util.Set;

import static ru.mipt.bit.platformer.objects.LogicLevel.Generators.StaticCommandsGenerator.generateStaticCommands;

public class CommandsHandler {

    private LogicLevel level;

    private AICommandsGenerator AIGenerator;
    private TapHandler keys;

    public CommandsHandler(LogicLevel level_) {
        keys = new TapHandler(level_.getPlayer(), level_);
        AIGenerator = new AICommandsGenerator(level_);
        level = level_;
    }
    public Set<Command> generateCommands() {
        Set<Command> commands = AIGenerator.generateEnemiesCommands(Set.copyOf(level.getEnemies()), level);
        commands.addAll(keys.generateKeysCommands());
        commands.addAll(generateStaticCommands(Set.copyOf(level.getBullets()), level));

        return commands;
    }
    public void executeCommands(Set<Command> commands) {
        for (Command cmd : commands) {
            cmd.execute();
        }
    }
}
