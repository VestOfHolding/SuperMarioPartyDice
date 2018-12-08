package boards.spaces;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class AllySpace extends BaseSpace {

    public AllySpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public boolean addAlly() {
        return true;
    }
}
