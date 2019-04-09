package boards.spaces.events.WDR;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.events.EventSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import simulation.Player;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class WhompSwitch extends EventSpace {

    private int firstWhompID;
    private int secondWhompID;

    public WhompSwitch(int spaceID, int firstWhompID, int secondWhompID) {
        this(spaceID, firstWhompID, secondWhompID, -1, -1);
    }

    public WhompSwitch(int spaceID, int firstWhompID, int secondWhompID, int x, int y) {
        super(spaceID);
        this.firstWhompID = firstWhompID;
        this.secondWhompID = secondWhompID;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                Player currentPlayer, List<Player> allPlayers) {

        WhompsOnTheRun firstWhomp = (WhompsOnTheRun)gameBoard.getVertexById(firstWhompID);
        WhompsOnTheRun secondWhomp = (WhompsOnTheRun)gameBoard.getVertexById(secondWhompID);

        //The two states of activity must be opposite of each other.
        if (firstWhomp.isActive() ^ secondWhomp.isActive()) {
            firstWhomp.whompSwitch(gameBoard);
            return true;
        }

        System.out.println("Illegal state reached while trying to swap Whomps! These spaces aren't synced properly: " + firstWhomp + " and " + secondWhomp);
        return false;
    }
}
