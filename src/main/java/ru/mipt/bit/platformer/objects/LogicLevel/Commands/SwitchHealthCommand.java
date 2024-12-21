package ru.mipt.bit.platformer.objects.LogicLevel.Commands;

import ru.mipt.bit.platformer.objects.GraphicLevel.Drawer;

public class SwitchHealthCommand implements Command {
    private Drawer drawer;

    public SwitchHealthCommand(Drawer drawer_) {
        drawer = drawer_;
    }

    public void execute() {
        drawer.handleHealthDrawing();
    }

}
