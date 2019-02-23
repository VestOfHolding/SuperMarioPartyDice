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
public class ItemSpace extends BaseSpace {
    public ItemSpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public SpaceUIClass getNodeClass() {
        return SpaceUIClass.ITEM;
    }
}
