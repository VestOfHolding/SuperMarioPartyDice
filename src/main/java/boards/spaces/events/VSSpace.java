package boards.spaces.events;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.BlueSpace;
import lombok.ToString;
import simulation.Player;
import simulation.PlayerGroup;
import utils.RandomUtils;
import utils.SpaceUIClass;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@ToString(callSuper = true)
public class VSSpace extends EventSpace {

    @ToString.Exclude
    List<Integer> POSSIBLE_WAGERS = Arrays.asList(5, 7, 10, 15, 20, 25, 30);

    public VSSpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        int wager = POSSIBLE_WAGERS.get(RandomUtils.getRandomInt(POSSIBLE_WAGERS.size() - 1));

        int totalPot = wager * 4;

        //60%, 30%, 10%, 0%, rounded down.
        IntStream possibleResults = IntStream.of(totalPot * 6 / 10, totalPot * 3 / 10, totalPot / 10, 0);

        //If two players tie for first place, they both get 45%, giving third the normal 10%.
        //If three players tie for second place, 1st gets the normal 60%,
        //    while the remaining three evenly split the remaining 40%.

        //Sometimes not everyone has enough coins for the wager too.
        //The leftover coins from rounding all the results down get
        // randomly distributed destination the 4 players.


        //Lastly, this space turns blue after it has been landed on.
        gameBoard.setOrReplaceVertex(spaceID, new BlueSpace(spaceID));

        return true;
    }

    @Override
    public SpaceUIClass getNodeClass() {
        return SpaceUIClass.VS;
    }
}
