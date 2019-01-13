package boards.spaces.events.WDR;

import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.events.EventSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jgrapht.graph.DefaultEdge;
import stattracker.GameStatTracker;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class WhompSwitch extends EventSpace {

    private int firstWhompID;
    private int secondWhompID;

    public WhompSwitch(int spaceID, int firstWhompID, int secondWhompID) {
        super(spaceID);
        this.firstWhompID = firstWhompID;
        this.secondWhompID = secondWhompID;
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, DefaultEdge> gameBoard,
                                GameStatTracker gameStatTracker, BaseSpace space) {

        WhompsOnTheRun firstWhomp = (WhompsOnTheRun)gameBoard.getVertexById(firstWhompID);
        WhompsOnTheRun secondWhomp = (WhompsOnTheRun)gameBoard.getVertexById(secondWhompID);

        if (firstWhomp.isActive() && !secondWhomp.isActive()) {
            firstWhomp.setActive(false);
            secondWhomp.setActive(true);
            return true;
        }
        else if (!firstWhomp.isActive() && secondWhomp.isActive()) {
            firstWhomp.setActive(true);
            secondWhomp.setActive(false);
            return true;
        }

        System.out.println("Illegal state reached while trying to swap Whomps! These spaces aren't synced properly: " + firstWhomp + " and " + secondWhomp);
        return false;
    }
}
