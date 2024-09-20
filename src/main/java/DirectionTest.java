import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.objects.Direction;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void toRotationLeft() {
        Direction left_ = new Direction(Direction.To.LEFT);
        assertEquals(-180f, left_.toRotation());
    }
    @Test
    void toRotationUp() {
        Direction up_ = new Direction(Direction.To.UP);
        assertEquals(90f, up_.toRotation());
    }
    @Test
    void toRotationRight() {
        Direction down_ = new Direction(Direction.To.DOWN);
        assertEquals(-90f, down_.toRotation());
    }
    @Test
    void toRotationDown() {
        Direction down_ = new Direction(Direction.To.DOWN);
        assertEquals(-90f, down_.toRotation());
    }
}