package boards.spaces.events;

import boards.spaces.BaseSpace;
import lombok.ToString;
import utils.SpaceUIClass;

@ToString(callSuper = true)
public class EventSpace extends BaseSpace {

    public EventSpace(int spaceID) {
        super(spaceID);
    }

    public EventSpace(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public SpaceUIClass getNodeClass() {
        return SpaceUIClass.EVENT;
    }
}
