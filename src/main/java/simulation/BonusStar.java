package simulation;

import lombok.AllArgsConstructor;
import partydice.Dice;
import utils.RandomUtils;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.Function;

@AllArgsConstructor
public enum BonusStar {
    SIGHTSEER((Player p) -> p.getGameStatTracker().getDistanceTotal()),
    RICH(Player::getCoinTotal),
    MINIGAME((Player p) -> p.getGameStatTracker().getMiniGameWins()),
    EVENTFUL((Player p) -> p.getGameStatTracker().getEventActivations()),
    ALLY((Player p) -> p.getGameStatTracker().getAllyTotal()),
    SLOWPOKE((Player p) -> p.getGameStatTracker().getCoinTotal(), Mode.MIN),
    BUDDY(null),
//    ITEM((Player p) -> p.getGameStatTracker().getItemsUsed()),
    UNLUCKY((Player p) -> p.getGameStatTracker().getBadLuckCount());

    private final Function<Player, Integer> starFunction;

    private final Mode mode;

    private enum Mode { MIN, MAX }

    BonusStar(Function<Player, Integer> starFunction) {
        this(starFunction, Mode.MAX);
    }

    /**
     * SMP gives 2 bonus stars in 10 and 15 turn games, but 3 in 20 or 30 turn games.
     * This method assumes max turn games and returns 3.
     */
    public static Set<BonusStar> randomlyGetBonusStars() {
        Set<BonusStar> bonusStars = EnumSet.noneOf(BonusStar.class);
        while (3 > bonusStars.size()) {
            bonusStars.add(values()[RandomUtils.getRandomInt(values().length - 1)]);
        }
        return bonusStars;
    }

    public Player findWinningPlayer(PlayerGroup group) {
        if (BUDDY == this) {
            //The Buddy Star is given to a player that has a randomly selected ally.
            Dice bonusBuddy = Dice.getRandomCharacterDieNotInGroup(group);
            return group.allPlayers().stream()
                    .filter(player -> player.getGameStatTracker().getAllies().contains(bonusBuddy.getName()))
                    .findFirst()
                    .orElse(null);
        }
        if (Mode.MAX == mode) {
            return group.allPlayers().stream().max(Comparator.comparing(starFunction)).orElse(null);
        }
        else {
            return group.allPlayers().stream().min(Comparator.comparing(starFunction)).orElse(null);
        }
    }
}
