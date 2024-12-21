package ru.mipt.bit.platformer.objects.LogicLevel.Generators;

import ru.mipt.bit.platformer.objects.LogicLevel.Commands.Command;

import java.util.Set;

public interface CommandGenerator {
    public Set<Command> generate();
}
