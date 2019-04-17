package boards.spaces;

import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString(callSuper = true)
public class StartSpace extends BaseSpace {

    public StartSpace(int spaceID) {
        super(spaceID);
    }
}
