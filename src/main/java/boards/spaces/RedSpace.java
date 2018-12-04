package boards.spaces;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class RedSpace extends BaseSpace{

    public RedSpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public int coinGain() {
        return -3;
    }
}
