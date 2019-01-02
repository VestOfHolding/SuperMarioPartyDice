package boards.spaces.events;

import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.Range;
import org.jgrapht.graph.DefaultEdge;
import stattracker.GameStatTracker;
import utils.RandomUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class BadLuckSpace extends EventSpace {
    //List of known possible events on Bad Luck Spaces:
    // * Give 3 coins to all other players (5)
    // * Give 5 coins to all other players (9)
    // * Lose 5 coins (11)
    // * Lose 10 coins (16)
    // * The Star moves (13)
    // * Give 5 coins to the last-place player. (5)
    // * Give 10 coins to the last-place player. (9)
    // * Give 5 coins to a random player. (5)
    // * Raise the coin cost for a Star (2)
    // * Lose one item (0)

    public BadLuckSpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, DefaultEdge> gameBoard,
                                GameStatTracker gameStatTracker, BaseSpace space) {
        List<Option> options = new ArrayList<>();

        for (int i = 0; i < 5; ++i) {
            options.add(getNextOption(options));
        }

        Option chosenOption = options.get(RandomUtils.getRandomInt(4));
        gameStatTracker.addCoins(chosenOption.getCoinGain());

        return false;
    }

    /**
     * Generate the next Option we can put in our list of five.
     * With the stipulation that no option can appear in the final list
     * more than twice.
     */
    public Option getNextOption(List<Option> listSoFar) {
        boolean foundNextOption = false;
        Option returnedOption;
        do {
            Option nextOption = Option.getRandomOption();

            if (nextOption == Option.ANYTHING_ELSE ||
                    listSoFar.stream().filter(o -> o.equals(nextOption)).count() < 2) {
                foundNextOption = true;
            }
            returnedOption = nextOption;
        } while (!foundNextOption);
        return returnedOption;
    }

    @Getter
    private enum Option {
        LOSE_5_COINS(-5, Range.between(0, 10)),
        LOSE_10_COINS(5, Range.between(11, 25)),
        ANYTHING_ELSE(0, Range.between(26, 69));

        private final int coinGain;
        private final Range<Integer> chance;

        Option(int coinGain, Range<Integer>  chance) {
            this.coinGain = coinGain;
            this.chance = chance;
        }

        public static Option getRandomOption() {
            int random = RandomUtils.getRandomInt(0, 69);

            return Arrays.stream(Option.values())
                    .filter(o -> o.getChance().contains(random))
                    .findFirst()
                    .orElse(ANYTHING_ELSE);
        }
    }
}
