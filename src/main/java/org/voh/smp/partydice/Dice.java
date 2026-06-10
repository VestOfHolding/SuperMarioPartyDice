package org.voh.smp.partydice;

import lombok.Getter;
import org.apache.commons.text.WordUtils;
import org.voh.smp.simulation.PlayerGroup;
import org.voh.smp.utils.RandomUtils;

@Getter
public enum Dice {
    //                 move faces                         coin faces
    BOO(          new int[]{0, 0, 5, 5, 7, 7},   new int[]{-2, -2, 0, 0, 0, 0}),
    BOWSER(       new int[]{0, 0, 1, 8, 9, 10},  new int[]{-3, -3, 0, 0, 0, 0}),
    BOWSER_JR(    new int[]{1, 1, 1, 4, 4, 9},   new int[]{ 0,  0, 0, 0, 0, 0}),
    DAISY(        new int[]{3, 3, 3, 3, 4, 4},   new int[]{ 0,  0, 0, 0, 0, 0}),
    DIDDY_KONG(   new int[]{0, 0, 0, 7, 7, 7},   new int[]{ 2,  0, 0, 0, 0, 0}),
    DONKEY_KONG(  new int[]{0, 0, 0, 0, 10, 10}, new int[]{ 5,  0, 0, 0, 0, 0}),
    DRY_BONES(    new int[]{1, 1, 1, 6, 6, 6},   new int[]{ 0,  0, 0, 0, 0, 0}),
    GOOMBA(       new int[]{0, 0, 3, 4, 5, 6},   new int[]{ 2,  2, 0, 0, 0, 0}),
    HAMMER_BRO(   new int[]{0, 1, 1, 5, 5, 5},   new int[]{ 3,  0, 0, 0, 0, 0}),
    KOOPA(        new int[]{1, 1, 2, 3, 3, 10},  new int[]{ 0,  0, 0, 0, 0, 0}),
    LUIGI(        new int[]{1, 1, 1, 5, 6, 7},   new int[]{ 0,  0, 0, 0, 0, 0}),
    MARIO(        new int[]{1, 3, 3, 3, 5, 6},   new int[]{ 0,  0, 0, 0, 0, 0}),
    MONTY_MOLE(   new int[]{0, 2, 3, 4, 5, 6},   new int[]{ 1,  0, 0, 0, 0, 0}),
    NORMAL_DICE(  new int[]{1, 2, 3, 4, 5, 6},   new int[]{ 0,  0, 0, 0, 0, 0}),
    PEACH(        new int[]{0, 2, 4, 4, 4, 6},   new int[]{ 0,  0, 0, 0, 0, 0}),
    POM_POM(      new int[]{0, 3, 3, 3, 3, 8},   new int[]{ 0,  0, 0, 0, 0, 0}),
    ROSALINA(     new int[]{0, 0, 2, 3, 4, 8},   new int[]{ 2,  2, 0, 0, 0, 0}),
    SHY_GUY(      new int[]{0, 4, 4, 4, 4, 4},   new int[]{ 0,  0, 0, 0, 0, 0}),
    WALUIGI(      new int[]{0, 1, 3, 5, 5, 7},   new int[]{-3,  0, 0, 0, 0, 0}),
    WARIO(        new int[]{0, 0, 6, 6, 6, 6},   new int[]{-2, -2, 0, 0, 0, 0}),
    YOSHI(        new int[]{0, 1, 3, 3, 5, 7},   new int[]{ 0,  0, 0, 0, 0, 0});

    private static final Dice[] VALUES = values();

    private final int[] move;
    private final int[] coin;

    Dice(int[] move, int[] coin) {
        if (6 != move.length || 6 != coin.length) {
            throw new IllegalArgumentException("Each die must have exactly 6 move and 6 coin faces: " + name());
        }
        this.move = move;
        this.coin = coin;
    }

    /** @return a face index in [0, 6). Caller reads move(face) and coin(face). */
    public int rollFace() {
        return RandomUtils.nextIntExclusive(6);
    }

    public int move(int face) {
        return move[face];
    }

    public int coin(int face) {
        return coin[face];
    }

    public static Dice getRandomCharacterDie() {
        return VALUES[RandomUtils.nextIntExclusive(VALUES.length)];
    }

    public static Dice getRandomCharacterDieNotInGroup(PlayerGroup playerGroup) {
        Dice result;
        do {
            result = getRandomCharacterDie();
        } while (playerGroup.isDieInGroup(result));
        return result;
    }

    public String getName() {
        return WordUtils.capitalizeFully(name().replaceAll("_", " "));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getName());
        for (int f = 0; f < 6; f++) {
            sb.append(',');
            if (coin[f] != 0) {
                sb.append(coin[f] > 0 ? "+" : "").append(coin[f]).append("c");
            } else {
                sb.append(move[f]);
            }
        }
        return sb.toString();
    }

}