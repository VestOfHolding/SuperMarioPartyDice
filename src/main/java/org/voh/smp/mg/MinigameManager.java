package org.voh.smp.mg;

import org.voh.smp.boards.spaces.SpaceColor;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;
import org.voh.smp.utils.RandomUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * I am going to regret writing this class at midnight, lol.
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

        //In 2v2 or 1v3 games, the winning team gets 8 coins, while the losing team gets 2 coins.
        //Something to consider adding at a later date:
        //  If the winning team consists of more than one player, they can high five and gain 2 more coins.
        //  This is super easy and common for human players to do basically every time if they wanted.
        if (2 == minigameTeams.size()) {
            int winningTeamIndex = RandomUtils.getRandomInt(minigameTeams.size() - 1);

            for (Player player : minigameTeams.get(winningTeamIndex)) {
                awardMinigameCoins(player, WIN_AMOUNT);
            }

            //Using the mod is the easiest way I can think of to do the other team.
            for (Player player : minigameTeams.get((winningTeamIndex + 1) % 2)) {
                awardMinigameCoins(player, LOSE_AMOUNT);
            }
        }
        //Four player games have a decreasing list of coin rewards.
        else if (4 == minigameTeams.size()) {
            //Randomizing who wins in what order.
            List<Player> shuffledPlayers = new ArrayList<>(playerGroup.allPlayers());

            for (int i = shuffledPlayers.size() - 1; i > 0; i--) {
                Collections.swap(shuffledPlayers, i, RandomUtils.nextIntExclusive(i + 1));
            }
            for (int i = 0; i < shuffledPlayers.size(); ++i) {
                awardMinigameCoins(shuffledPlayers.get(i), FOUR_PLAYER_WIN_AMOUNTS.get(i));
            }
        }
    }

    private void awardMinigameCoins(Player player, int amount) {
        player.getGameStatTracker().addMinigameCoins(amount);
    }

    public List<List<Player>> constructPlayerTeams(PlayerGroup playerGroup) {
        List<Player> allPlayers = playerGroup.allPlayers();

        int greenCount = 0;
        int blueCount = 0;

        for (Player player : allPlayers) {
            if (SpaceColor.GREEN == player.getLandedSpaceColor()) {
                greenCount++;
            }
            else if (SpaceColor.BLUE == player.getLandedSpaceColor()) {
                blueCount++;
            }
        }

        if (4 == greenCount) {
            return buildFreeForAllTeams(allPlayers);
        }
        //We only care about transforming the green spaces, so it could be that there's nothing more to do.
        else if (0 == greenCount) {
            return groupTeamsByColor(allPlayers);
        }

        Map<Integer, Integer> finalLayer = thisIsACryForHelp.get(greenCount).get(blueCount);

        int randomChance = RandomUtils.getRandomInt(1, 10);
        if (!finalLayer.containsKey(randomChance)) {
            randomChance = 10;
        }

        int newBlueCount = finalLayer.get(randomChance);

        for (Player player : allPlayers) {
            if (SpaceColor.GREEN == player.getLandedSpaceColor()) {
                if (0 < newBlueCount) {
                    player.setLandedSpaceColor(SpaceColor.BLUE);
                    newBlueCount--;
                }
                else {
                    player.setLandedSpaceColor(SpaceColor.RED);
                }
            }
        }
        return new ArrayList<>(allPlayers.stream()
                .collect(Collectors.groupingBy(Player::getLandedSpaceColor,
                        () -> new EnumMap<>(SpaceColor.class), Collectors.toList()))
                .values());
    }

    /**
     * Group the players into teams by the color of space they landed on.
     *
     * If all four players ended up on the same color, the real game runs a
     * free-for-all minigame, so return four one-player teams instead of
     * one four-player team (which would previously pay out nothing).
     */
    private List<List<Player>> groupTeamsByColor(List<Player> allPlayers) {
        List<List<Player>> teams = new ArrayList<>(allPlayers.stream()
                .collect(Collectors.groupingBy(Player::getLandedSpaceColor,
                        () -> new EnumMap<>(SpaceColor.class), Collectors.toList()))
                .values());

        if (1 == teams.size()) {
            return buildFreeForAllTeams(allPlayers);
        }
        return teams;
    }

    private List<List<Player>> buildFreeForAllTeams(List<Player> allPlayers) {
        return Arrays.asList(Collections.singletonList(allPlayers.get(0)),
                Collections.singletonList(allPlayers.get(1)),
                Collections.singletonList(allPlayers.get(2)),
                Collections.singletonList(allPlayers.get(3)));
    }
}
