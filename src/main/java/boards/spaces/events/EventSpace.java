package boards.spaces.events;

import boards.spaces.BaseSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import utils.SpaceUIClass;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class EventSpace extends BaseSpace {

    public EventSpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public SpaceUIClass getNodeClass() {
        return SpaceUIClass.EVENT;
    }
}
