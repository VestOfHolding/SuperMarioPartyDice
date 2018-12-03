package boards.spaces.events;

import boards.layout.Board;
import boards.layout.Edge;
import boards.spaces.BaseSpace;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import stattracker.GameStatTracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

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

    @Builder(builderMethodName = "chooseChestEventBuilder")
    public ChooseTreasureChestEvent(int spaceID, Set<Edge> edges) {
        super(spaceID, edges);

        //Not using items yet.
        REWARDS = new ArrayList<>(Arrays.asList(0, 3, 10));
        RAND = new Random();
    }

    @Override
    public void processEvent(Board gameBoard, GameStatTracker gameStatTracker, BaseSpace space) {
        if (REWARDS.isEmpty()) {
            return;
        }

        Integer reward = REWARDS.get(REWARDS.size() == 1 ? 0 : RAND.nextInt(REWARDS.size()));

        REWARDS.remove(reward);

        gameStatTracker.addCoins(reward);
    }
}
