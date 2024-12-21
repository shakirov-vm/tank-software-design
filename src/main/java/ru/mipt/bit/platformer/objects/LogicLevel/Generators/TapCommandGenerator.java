package ru.mipt.bit.platformer.objects.LogicLevel.Generators;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.Command;
import ru.mipt.bit.platformer.objects.LogicLevel.Commands.ShootCommand;
import ru.mipt.bit.platformer.objects.LogicLevel.LogicLevel;
import ru.mipt.bit.platformer.objects.LogicLevel.TapHandlers.TapHandler;

import java.util.HashSet;
import java.util.Set;

import static com.badlogic.gdx.Input.Keys.*;

public class TapCommandGenerator implements CommandGenerator {

    Set<TapHandler> handlers = new HashSet<>();

    public TapCommandGenerator(Set<TapHandler> handlers_) {
        handlers = handlers_;
    }

    public Set<Command> generate() {

        Set<Command> commands = new HashSet<>();

        for (TapHandler handler : handlers) {
            Command next = handler.handle();
            if (next != null) {
                commands.add(next);
            }
        }

        return commands;
    }
}
