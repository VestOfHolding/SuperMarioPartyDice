package boards.spaces.events;

import boards.layout.CustomSimpleDirectedGraph;
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

    Random RAND;

    //Three treasures event space. Rewards:
    //1. Golden Dash Mushroom
    //2. 10 coins
    //3. 3 coins
    List<Integer> REWARDS;

    public ChooseTreasureChestEvent(int spaceID) {
        super(spaceID);

        //Not using items yet.
        REWARDS = new ArrayList<>(Arrays.asList(0, 3, 10));
        RAND = new Random();
    }

    @Override
    public void processEvent(CustomSimpleDirectedGraph<BaseSpace, DefaultEdge> gameBoard,
                             GameStatTracker gameStatTracker, BaseSpace space) {
        if (REWARDS.isEmpty()) {
            return;
        }

        Integer reward = REWARDS.get(REWARDS.size() == 1 ? 0 : RAND.nextInt(REWARDS.size()));

        REWARDS.remove(reward);

        gameStatTracker.addCoins(reward);
    }
}
