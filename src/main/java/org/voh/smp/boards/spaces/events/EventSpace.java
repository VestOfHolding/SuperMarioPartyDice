package org.voh.smp.boards.spaces.events;

import org.voh.smp.boards.spaces.BaseSpace;
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
