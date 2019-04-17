package boards.spaces;

import lombok.ToString;
import utils.SpaceUIClass;

@ToString(callSuper = true)
public class ItemSpace extends BaseSpace {
    public ItemSpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public SpaceUIClass getNodeClass() {
        return SpaceUIClass.ITEM;
    }
}
