package mg;

import boards.spaces.SpaceColor;
import simulation.Player;
import simulation.PlayerGroup;
import utils.RandomUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * I am going to regret writing this class at midnight, lol.
 * I'm sure this must destroy the simulation performance.
 */
public class MinigameManager {
    public static final int WIN_AMOUNT = 8;
    public static final int LOSE_AMOUNT = 2;

    public static final List<Integer> FOUR_PLAYER_WIN_AMOUNTS = Arrays.asList(8, 4, 2, 0);

    //Creating a map of values to recreate these tables:
    //https://docs.google.com/spreadsheets/d/1ZUsV_4xSfQCyrY0uEret_aJXR7FH2Tyn-pwo608ZnNQ/

    //Green count to blue count to probability to transformed blue count
    private static final Map<Integer, Map<Integer, Map<Integer, Integer>>> thisIsACryForHelp =
            Map.of(4, Map.of(0, Map.of(1, 2, 2, 3, 10, 4)),
                    3, Map.of(1, Map.of(1, 0, 2, 1, 10, 3),
                            0, Map.of(1, 3, 2, 2, 10, 0)),
                    2, Map.of(2, Map.of(1, 0, 2, 1, 10, 2),
                            1, Map.of(2, 1, 10, 2),
                            0, Map.of(1, 2, 2, 1, 10, 0)),
                    1, Map.of(3, Map.of(2, 0, 10, 1),
                            2, Map.of(2, 0, 10, 1),
                            1, Map.of(2, 1, 10, 0),
                            0, Map.of(2, 1, 10, 0)));

    public void runMinigame(PlayerGroup playerGroup) {
        List<List<Player>> minigameTeams = constructPlayerTeams(playerGroup);

        int winningTeamIndex = RandomUtils.getRandomInt(minigameTeams.size() - 1);

        //In 2v2 or 1v3 games, the winning team gets 8 coins, while the losing team gets 2 coins.
        //Something to consider adding at a later date:
        //  If the winning team consists of more than one player, they can high five and gain 2 more coins.
        //  This is super easy and common for human players to do basically every time if they wanted.
        if (minigameTeams.size() == 2) {
            for (Player player : minigameTeams.get(winningTeamIndex)) {
                player.addCoins(WIN_AMOUNT);
            }

            //Using the mod is the easiest way I can think of to do the other team.
            for (Player player : minigameTeams.get((winningTeamIndex + 1) % 2)) {
                player.addCoins(LOSE_AMOUNT);
            }
        }
        //Four player games have a decreasing list of coin rewards
        else if (minigameTeams.size() == 4) {
            //Randomizing who wins in what order.
            Set<Player> playerSet = new HashSet<>(playerGroup.getAllPlayers());

            int i = 0;
            for (Player player : playerSet) {
                player.addCoins(FOUR_PLAYER_WIN_AMOUNTS.get(i++));
            }
        }
    }

    public List<List<Player>> constructPlayerTeams(PlayerGroup playerGroup) {
        List<Player> allPlayers = playerGroup.getAllPlayers();

        int greenCount = 0;
        int blueCount = 0;

        for (Player player : allPlayers) {
            if (player.getLandedSpaceColor() == SpaceColor.GREEN) {
                greenCount++;
            }
            else if (player.getLandedSpaceColor() == SpaceColor.BLUE) {
                blueCount++;
            }
        }

        if (greenCount == 4) {
            return Arrays.asList(Collections.singletonList(allPlayers.get(0)),
                    Collections.singletonList(allPlayers.get(1)),
                    Collections.singletonList(allPlayers.get(2)),
                    Collections.singletonList(allPlayers.get(3)));
        }
        //We only care about transforming the green spaces, so it could be that there's nothing more to do.
        else if (greenCount == 0) {
            return new ArrayList<>(allPlayers.stream().collect(Collectors.groupingBy(Player::getLandedSpaceColor)).values());
        }

        Map<Integer, Integer> finalLayer = thisIsACryForHelp.get(greenCount).get(blueCount);

        int randomChance = RandomUtils.getRandomInt(1, 10);
        if (!finalLayer.containsKey(randomChance)) {
            randomChance = 10;
        }

        int newBlueCount = finalLayer.get(randomChance);

        for (Player player : allPlayers) {
            if (player.getLandedSpaceColor() == SpaceColor.GREEN) {
                if (newBlueCount > 0) {
                    player.setLandedSpaceColor(SpaceColor.BLUE);
                    newBlueCount--;
                }
                else {
                    player.setLandedSpaceColor(SpaceColor.RED);
                }
            }
        }
        return new ArrayList<>(allPlayers.stream().collect(Collectors.groupingBy(Player::getLandedSpaceColor)).values());
    }
}
