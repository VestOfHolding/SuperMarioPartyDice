package boards.spaces;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class NonMovementSpace extends BaseSpace {
    @Override
    public boolean affectsMovement() {
        return false;
    }
}
