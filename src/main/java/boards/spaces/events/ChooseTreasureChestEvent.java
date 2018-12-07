package boards.spaces.events;

import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jgrapht.graph.DefaultEdge;
import stattracker.GameStatTracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class ChooseTreasureChestEvent extends EventSpace {

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Random RAND;

    //Three treasures event space. Rewards:
    //1. Golden Dash Mushroom
    //2. 10 coins
    //3. 3 coins
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    List<Integer> REWARDS;

    public ChooseTreasureChestEvent(int spaceID) {
        super(spaceID);

        //Not using items yet.
        REWARDS = new ArrayList<>(Arrays.asList(0, 3, 10));
        RAND = new Random();
    }

    @Override
    public void processEvent(MPBoard<BaseSpace, DefaultEdge> gameBoard,
                             GameStatTracker gameStatTracker, BaseSpace space) {
        if (REWARDS.isEmpty()) {
            return;
        }

        Integer reward = REWARDS.get(REWARDS.size() == 1 ? 0 : RAND.nextInt(REWARDS.size()));

        REWARDS.remove(reward);

        gameStatTracker.addCoins(reward);
    }
}
