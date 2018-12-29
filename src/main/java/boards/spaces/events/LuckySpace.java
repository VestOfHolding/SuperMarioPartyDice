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
public class LuckySpace extends EventSpace {
    //List of known possible events on Lucky Spaces:
    // * Receive a Dash Mushroom (17)
    // * Get several Dash Mushrooms (10)
    // * Receive a Golden Dash Mushroom (7)
    // * Get several Golden Dash Mushrooms (4)
    // * Make a rival lose 5 coins (23)
    // * Make a rival lose 10 coins (3)
    // * Receive 3 coins (10)
    // * Receive 5 coins (23)
    // * Steal one ally from a rival (3)

    private boolean allyOptionTriggered;

    public LuckySpace(int spaceID) {
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

        //Set this here so that way when the general simulation logic checks
        // if an ally needs to be added it'll find out through the same
        // overridden method as usual.
        if (chosenOption.isAllyGain()) {
            allyOptionTriggered = true;
        }

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

    @Override
    public boolean addAlly() {
        if (allyOptionTriggered) {
            allyOptionTriggered = false;
            return true;
        }
        return false;
    }

    @Getter
    private enum Option {
        THREE_COINS(3, Range.between(0, 10)),
        FIVE_COINS(5, Range.between(11, 33)),
        ALLY_GAIN(0, Range.between(34, 36), true),
        ANYTHING_ELSE(0, Range.between(37, 99));

        private final int coinGain;
        private final Range<Integer> chance;
        private final boolean allyGain;

        Option(int coinGain, Range<Integer>  chance) {
            this.coinGain = coinGain;
            this.chance = chance;
            allyGain = false;
        }

        Option(int coinGain, Range<Integer>  chance, boolean allyGain) {
            this.coinGain = coinGain;
            this.chance = chance;
            this.allyGain = allyGain;
        }

        public static Option getRandomOption() {
            int random = RandomUtils.getRandomInt(0, 99);

            return Arrays.stream(Option.values())
                    .filter(o -> o.getChance().contains(random))
                    .findFirst()
                    .orElse(ANYTHING_ELSE);
        }
    }
}
