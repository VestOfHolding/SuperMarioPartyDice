package org.voh.smp.simulation;

import org.apache.commons.collections4.ListUtils;
import org.voh.smp.partydice.Dice;
import org.voh.smp.utils.RandomUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Wrapper around the list of four players that comes with some handy utility methods.
 */
public record PlayerGroup(List<Player> allPlayers) {
    public PlayerGroup(List<Player> allPlayers) {
        this.allPlayers = ListUtils.emptyIfNull(allPlayers);
    }

    public Player getRandomPlayer() {
        return allPlayers.get(RandomUtils.getRandomInt(allPlayers.size() - 1));
    }

    /**
     * Return a random player from the group that isn't the given player.
     */
    public Player getRandomPlayerBesidesCurrent(Player currentPlayer) {
        Player result;

        do {
            result = getRandomPlayer();
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
                .filter(Player::isInLastPlace)
                .findFirst()
                .orElse(null);
    }

    public boolean isDieInGroup(Dice characterDie) {
        return allPlayers.stream().anyMatch(p -> p.getCharacterDice() == characterDie);
    }
}
