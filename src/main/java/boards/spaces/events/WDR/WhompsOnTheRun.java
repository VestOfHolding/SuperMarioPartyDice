package boards.spaces.events.WDR;

import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.events.EventSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jgrapht.graph.DefaultEdge;
import stattracker.GameStatTracker;
import utils.RandomUtils;

import java.util.ArrayList;
import java.util.Arrays;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class WhompsOnTheRun extends EventSpace {
    private static int COST = 3;

    private int partnerID;

    @Getter
    @Setter
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private boolean active;

    public WhompsOnTheRun(int spaceID, int partnerID, boolean active) {
        super(spaceID);
        this.partnerID = partnerID;
        this.active = active;
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, DefaultEdge> gameBoard,
                                GameStatTracker gameStatTracker, BaseSpace space) {
        if (!isActive()) {
            return false;
        }

        gameStatTracker.addCoins(-COST);

        active = false;
        ((WhompsOnTheRun)gameBoard.getVertexById(partnerID)).setActive(true);

        return true;
    }

    @Override
    public boolean affectsMovement() {
        return false;
    }

    @Override
    public boolean isPassingEvent() {
        return true;
    }
}
