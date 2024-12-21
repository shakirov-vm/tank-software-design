package ru.mipt.bit.platformer.objects.LogicLevel.Generators;

import ru.mipt.bit.platformer.objects.LogicLevel.Commands.Command;
import ru.mipt.bit.platformer.objects.LogicLevel.LogicLevel;

import java.util.HashSet;
import java.util.Set;


public class CommandsHandler {

    private LogicLevel level;

    private Set<CommandGenerator> generators = new HashSet<>();

    public CommandsHandler(Set<CommandGenerator> generators_) {
        generators = generators_;
    }
    public Set<Command> generateCommands() {
        Set<Command> commands = new HashSet<>();

        for (CommandGenerator generator : generators) {
            commands.addAll(generator.generate());
        }
        return commands;
    }
    public void executeCommands(Set<Command> commands) {
        for (Command cmd : commands) {
            cmd.execute();
        }
    }
}
