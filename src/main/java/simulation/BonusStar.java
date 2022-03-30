package simulation;

import lombok.AllArgsConstructor;
import utils.RandomUtils;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

@AllArgsConstructor
public enum BonusStar {
    SIGHTSEER((Player p) -> p.getGameStatTracker().getDistanceTotal()),
    RICH(Player::getCoinTotal),
    MINIGAME((Player p) -> p.getGameStatTracker().getMiniGameWins()),
    EVENTFUL((Player p) -> p.getGameStatTracker().getEventActivations()),
    ALLY((Player p) -> p.getGameStatTracker().getAllyTotal()),
    SLOWPOKE((Player p) -> p.getGameStatTracker().getCoinTotal(), Mode.MIN),
//    BUDDY((Player p) -> p.getGameStatTracker() have random specific ally),
//    ITEM((Player p) -> p.getGameStatTracker().getItemsUsed()),
    UNLUCKY((Player p) -> p.getGameStatTracker().getBadLuckCount());

    private Function<Player, Integer> starFunction;

    private Mode mode;

    private enum Mode { MIN, MAX }

    BonusStar(Function<Player, Integer> starFunction) {
        this(starFunction, Mode.MAX);
    }

    /**
     * SMP gives 2 bonus stars in 10 and 15 turn games, but 3 in 20 or 30 turn games.
     * This method assumes max turn games and returns 3.
     */
    public static Set<BonusStar> randomlyGetBonusStars() {
        Set<BonusStar> bonusStars = new HashSet<>();
        while (bonusStars.size() < 3) {
            bonusStars.add(BonusStar.values()[RandomUtils.getRandomInt(BonusStar.values().length - 1)]);
        }
        return bonusStars;
    }

    public Player findWinningPlayer(PlayerGroup group) {
        Stream<Player> playerStream = group.getAllPlayers().stream();
        if (mode == Mode.MAX) {
            return playerStream.max(Comparator.comparing(p -> starFunction.apply(p))).orElse(null);
        }
        else {
            return playerStream.min(Comparator.comparing(p -> starFunction.apply(p))).orElse(null);
        }
    }
}
