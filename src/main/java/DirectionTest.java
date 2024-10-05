import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.objects.Direction;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void toRotationLeft() {
        Direction left_ = new Direction(Direction.To.LEFT);
        assertEquals(Direction.LEFT_DIRECTION, left_.toRotation());
    }
    @Test
    void toRotationUp() {
        Direction up_ = new Direction(Direction.To.UP);
        assertEquals(Direction.UP_DIRECTION, up_.toRotation());
    }
    @Test
    void toRotationRight() {
        Direction right_ = new Direction(Direction.To.RIGHT);
        assertEquals(Direction.RIGHT_DIRECTION, right_.toRotation());
    }
    @Test
    void toRotationDown() {
        Direction down_ = new Direction(Direction.To.DOWN);
        assertEquals(Direction.DOWN_DIRECTION, down_.toRotation());
    }
}