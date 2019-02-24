package boards.spaces;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import utils.SpaceUIClass;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class NonMovementSpace extends BaseSpace {

    public NonMovementSpace(int spaceID) {
        super(spaceID);
    }

    public NonMovementSpace(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public boolean affectsMovement() {
        return false;
    }

    @Override
    public SpaceUIClass getNodeClass() {
        return SpaceUIClass.NONMOVEMENT;
    }
}
