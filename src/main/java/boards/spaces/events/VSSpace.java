package boards.spaces.events;

import boards.layout.MPBoard;
import boards.spaces.BlueSpace;
import boards.spaces.SpaceColor;
import lombok.ToString;
import simulation.Player;
import simulation.PlayerGroup;
import utils.RandomUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@ToString(callSuper = true)
public class VSSpace extends BlueSpace {

    @ToString.Exclude
    final List<Integer> POSSIBLE_WAGERS = Arrays.asList(5, 7, 10, 15, 20, 25, 30);

    final List<Integer> NORMAL_SPLITS = Arrays.asList(6, 3, 1, 0);

    private boolean used;

    public VSSpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public boolean processEvent(MPBoard gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        //After the VS space has been landed on, it becomes a blue space.
        if (used) {
            return super.processEvent(gameBoard, currentPlayer, playerGroup);
        }

        int wager = POSSIBLE_WAGERS.get(RandomUtils.getRandomInt(POSSIBLE_WAGERS.size() - 1));

        //Sometimes not everyone has enough coins for the wager.
        int totalPot = playerGroup.allPlayers().stream()
                .mapToInt(p -> p.takeCoins(wager))
                .sum();

        //60%, 30%, 10%, 0%, rounded down.
        List<Integer> normalSplitCopy = new ArrayList<>(NORMAL_SPLITS);

        for (Player player : new HashSet<>(playerGroup.allPlayers())) {
            Integer split = normalSplitCopy.get(RandomUtils.getRandomInt(normalSplitCopy.size() - 1));
            int gainFromPot = totalPot * split / 10;

            player.addCoins(gainFromPot);
            normalSplitCopy.remove(split);
            totalPot -= gainFromPot;
        }

        //If two players tie for first place, they both get 45%, giving third the normal 10%.
        //If three players tie for second place, 1st gets the normal 60%,
        //    while the remaining three evenly split the remaining 40%.

        //The leftover coins from rounding all the results get
        // randomly distributed destination the 4 players.
        for(; 0 < totalPot; --totalPot) {
            playerGroup.getRandomPlayer().addCoins(1);
        }

        //Lastly, this space turns blue after it has been landed on.
        used = true;

        return true;
    }

    @Override
    public void reset() {
        used = false;
    }

    @Override
    public SpaceColor getSpaceColor() {
        return SpaceColor.GREEN;
    }
}
