package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mipt.bit.platformer.GameDesktopLauncher;
import ru.mipt.bit.platformer.objects.GraphicLevel.Drawer;
import ru.mipt.bit.platformer.objects.LogicLevel.Generators.*;
import ru.mipt.bit.platformer.objects.LogicLevel.InitMap.MapInitObjects;
import ru.mipt.bit.platformer.objects.LogicLevel.InitMap.MapInitPath;
import ru.mipt.bit.platformer.objects.LogicLevel.InitMap.MapInitRandom;
import ru.mipt.bit.platformer.objects.LogicLevel.LogicLevel;
import ru.mipt.bit.platformer.objects.LogicLevel.TapHandlers.HealthBarTapHandler;
import ru.mipt.bit.platformer.objects.LogicLevel.TapHandlers.MoveTapHandler;
import ru.mipt.bit.platformer.objects.LogicLevel.TapHandlers.ShootTapHandler;
import ru.mipt.bit.platformer.objects.LogicLevel.TapHandlers.TapHandler;

import java.util.HashSet;
import java.util.Set;

import static ru.mipt.bit.platformer.GameDesktopLauncher.TILES_HEIGHT;
import static ru.mipt.bit.platformer.GameDesktopLauncher.TILES_WIDTH;

@Configuration
public class Config {

    @Bean
    public MapInitObjects mapInitObjects() {
        return (MapInitObjects) new MapInitRandom(TILES_WIDTH, TILES_HEIGHT);
    }

    @Bean
    public LogicLevel logicLevel() {
        return new LogicLevel();
    }
    @Bean
    public GameDesktopLauncher.obstaclesCreateMode obstaclesMode() {
        return GameDesktopLauncher.obstaclesCreateMode.RANDOM_OBSTACLES;
    }
}
