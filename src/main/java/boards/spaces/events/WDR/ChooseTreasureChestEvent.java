package boards.spaces.events.WDR;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.events.EventSpace;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import simulation.Player;
import simulation.PlayerGroup;
import utils.RandomUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class ChooseTreasureChestEvent extends EventSpace {

    //Three treasures event space. Rewards:
    //1. Golden Dash Mushroom
    //2. 10 coins
    //3. 3 coins
    @ToString.Exclude
    List<Integer> REWARDS;

    public ChooseTreasureChestEvent(int spaceID) {
        this(spaceID, -1, -1);
    }

    public ChooseTreasureChestEvent(int spaceID, int x, int y) {
        super(spaceID);
        this.x = x;
        this.y = y;

        //Not using items yet.
        REWARDS = new ArrayList<>(Arrays.asList(0, 3, 10));
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        if (REWARDS.isEmpty()) {
            return false;
        }

        Integer reward = REWARDS.get(REWARDS.size() == 1 ? 0 : RandomUtils.getRandomInt(REWARDS.size() - 1));

        REWARDS.remove(reward);

        currentPlayer.addCoins(reward);

        return true;
    }
}
