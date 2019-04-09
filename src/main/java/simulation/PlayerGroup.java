package simulation;

import org.apache.commons.collections4.ListUtils;
import stattracker.Place;
import utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Wrapper around the list of four players that comes with some handy utility methods.
 */
public class PlayerGroup {
    private List<Player> allPlayers;

    public PlayerGroup (List<Player> allPlayers) {
        allPlayers = ListUtils.emptyIfNull(allPlayers);
    }

    /**
     * Return a random player from the group that isn't the given player.
     */
    public Player getRandomPlayerBesidesCurrent(Player currentPlayer) {
        Player result;

        do {
            result = allPlayers.get(RandomUtils.getRandomInt(allPlayers.size() - 1));
        } while (result == currentPlayer);

        return result;
    }

    /**
     * Return a list of all the other players besides the current one.
     */
    public List<Player> getAllPlayersExceptCurrent(Player currentPlayer) {
        return allPlayers.stream()
                .filter(p -> p != currentPlayer)
                .collect(Collectors.toList());
    }

    public Player getLastPlacePlayer() {
        return allPlayers.stream()
                .filter(p -> p.getCurrentPlace() == Place.FOURTH)
                .findFirst()
                .orElse(null);
    }
}
