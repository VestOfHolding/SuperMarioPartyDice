package boards.spaces.events;

import boards.spaces.BaseSpace;
import boards.spaces.SpaceColor;
import lombok.ToString;

@ToString(callSuper = true)
public class EventSpace extends BaseSpace {

    public EventSpace(int spaceID) {
        super(spaceID);
    }

    public EventSpace(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }
}
