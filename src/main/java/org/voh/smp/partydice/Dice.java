package org.voh.smp.partydice;

import lombok.Getter;
import org.apache.commons.text.WordUtils;
import org.voh.smp.results.CoinResult;
import org.voh.smp.results.DieResult;
import org.voh.smp.results.MoveResult;
import org.voh.smp.simulation.PlayerGroup;
import org.voh.smp.utils.RandomUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum Dice {
    BOO(Arrays.asList(new CoinResult(-2), new CoinResult(-2), new MoveResult(5),
            new MoveResult(5), new MoveResult(7), new MoveResult(7))),
    BOWSER(Arrays.asList(new CoinResult(-3), new CoinResult(-3), new MoveResult(1),
            new MoveResult(8), new MoveResult(9), new MoveResult(10))),
    BOWSER_JR(Arrays.asList(new MoveResult(1), new MoveResult(1), new MoveResult(1),
            new MoveResult(4), new MoveResult(4), new MoveResult(9))),
    DAISY(Arrays.asList(new MoveResult(3), new MoveResult(3), new MoveResult(3),
            new MoveResult(3), new MoveResult(4), new MoveResult(4))),
    DIDDY_KONG(Arrays.asList(new MoveResult(0), new MoveResult(0), new MoveResult(0),
            new MoveResult(7), new MoveResult(7), new MoveResult(7))),
    DONKEY_KONG(Arrays.asList(new CoinResult(5), new MoveResult(0), new MoveResult(0),
            new MoveResult(0), new MoveResult(10), new MoveResult(10))),
    DRY_BONES(Arrays.asList(new MoveResult(1), new MoveResult(1), new MoveResult(1),
            new MoveResult(6), new MoveResult(6), new MoveResult(6))),
    GOOMBA(Arrays.asList(new CoinResult(2), new CoinResult(2), new MoveResult(3),
            new MoveResult(4), new MoveResult(5), new MoveResult(6))),
    HAMMER_BRO(Arrays.asList(new CoinResult(3), new MoveResult(1), new MoveResult(1),
            new MoveResult(5), new MoveResult(5), new MoveResult(5))),
    KOOPA(Arrays.asList(new MoveResult(1), new MoveResult(1), new MoveResult(2),
            new MoveResult(3), new MoveResult(3), new MoveResult(10))),
    LUIGI(Arrays.asList(new MoveResult(1), new MoveResult(1), new MoveResult(1),
            new MoveResult(5), new MoveResult(6), new MoveResult(7))),
    MARIO(Arrays.asList(new MoveResult(1), new MoveResult(3), new MoveResult(3),
            new MoveResult(3), new MoveResult(5), new MoveResult(6))),
    MONTY_MOLE(Arrays.asList(new CoinResult(1), new MoveResult(2), new MoveResult(3),
            new MoveResult(4), new MoveResult(5), new MoveResult(6))),
    NORMAL_DICE(Arrays.asList(new MoveResult(1), new MoveResult(2), new MoveResult(3),
            new MoveResult(4), new MoveResult(5), new MoveResult(6))),
    PEACH(Arrays.asList(new MoveResult(0), new MoveResult(2), new MoveResult(4),
            new MoveResult(4), new MoveResult(4), new MoveResult(6))),
    POM_POM(Arrays.asList(new MoveResult(0), new MoveResult(3), new MoveResult(3),
            new MoveResult(3), new MoveResult(3), new MoveResult(8))),
    ROSALINA(Arrays.asList(new CoinResult(2), new CoinResult(2), new MoveResult(2),
            new MoveResult(3), new MoveResult(4), new MoveResult(8))),
    SHY_GUY(Arrays.asList(new MoveResult(0), new MoveResult(4), new MoveResult(4),
            new MoveResult(4), new MoveResult(4), new MoveResult(4))),
    WALUIGI(Arrays.asList(new CoinResult(-3), new MoveResult(1), new MoveResult(3),
            new MoveResult(5), new MoveResult(5), new MoveResult(7))),
    WARIO(Arrays.asList(new CoinResult(-2), new CoinResult(-2), new MoveResult(6),
            new MoveResult(6), new MoveResult(6), new MoveResult(6))),
    YOSHI(Arrays.asList(new MoveResult(0), new MoveResult(1), new MoveResult(3),
            new MoveResult(3), new MoveResult(5), new MoveResult(7)));

    List<? extends DieResult> possibleRolls;

    Dice(List<? extends DieResult> possibleRolls) {
        if (6 == possibleRolls.size()) {
            this.possibleRolls = possibleRolls;
        }
    }

    public static Dice getRandomCharacterDie() {
        return values()[RandomUtils.getRandomInt(values().length - 1)];
    }

    public static Dice getRandomCharacterDieNotInGroup(PlayerGroup playerGroup) {
        Dice result;
        do {
            result = getRandomCharacterDie();
        } while (playerGroup.isDieInGroup(result));
        return result;
    }

    public DieResult roll() {
        return possibleRolls.get(RandomUtils.getRandomInt(5));
    }

    public String getName() {
        return WordUtils.capitalizeFully(name().replaceAll("_", " "));
    }

    @Override
    public String toString() {
        String rolls = possibleRolls.stream().map(DieResult::toString).collect(Collectors.joining(","));

        return String.join(",", getName(), rolls);
    }
}