package org.voh.smp.boards.spaces;

import org.voh.smp.boards.spaces.events.BadLuckSpace;
import org.voh.smp.boards.spaces.events.EventSpace;
import org.voh.smp.boards.spaces.events.LuckySpace;
import org.voh.smp.boards.spaces.events.MoveEventSpace;
import org.voh.smp.boards.spaces.events.VSSpace;

import java.util.HashMap;
import java.util.Map;

public class SpaceFactory {
    public final Map<Integer, BaseSpace> spaceCache;
    
    public SpaceFactory() {
        spaceCache = new HashMap<>(70);
    }

    public StartSpace createStartSpace(int index, int x, int y) {
        StartSpace space;
        if (!spaceCache.containsKey(index)) {
            space = new StartSpace(index);
            spaceCache.put(index, space);
        }
        else if (!(spaceCache.get(index) instanceof StartSpace)) {
            space = new StartSpace(index);
        }
        else {
            space = (StartSpace)spaceCache.get(index);
        }

        return (StartSpace)setXandY(space, x, y);
    }

    public BlueSpace createBlueSpace(int index, int x, int y) {
        return createBlueSpace(index, 3, x, y);
    }

    public BlueSpace createBlueSpace(int index, int coinAmount, int x, int y) {
        BlueSpace space;
        if (!spaceCache.containsKey(index)) {
            space = new BlueSpace(index, coinAmount);
            spaceCache.put(index, space);
        }
        else if (!(spaceCache.get(index) instanceof StartSpace)) {
            space = new BlueSpace(index, coinAmount);
        }
        else {
            space = (BlueSpace)spaceCache.get(index);
        }

        return (BlueSpace)setXandY(space, x, y);
    }

    public RedSpace createRedSpace(int index, int x, int y) {
        return createRedSpace(index, 3, x, y);
    }

    public RedSpace createRedSpace(int index, int coinAmount, int x, int y) {
        RedSpace space;
        if (!spaceCache.containsKey(index)) {
            space = new RedSpace(index, coinAmount);
            spaceCache.put(index, space);
        }
        else if (!(spaceCache.get(index) instanceof StartSpace)) {
            space = new RedSpace(index, coinAmount);
        }
        else {
            space = (RedSpace)spaceCache.get(index);
        }

        return (RedSpace)setXandY(space, x, y);
    }

    public EventSpace createEventSpace(int index, int x, int y) {
        EventSpace space;
        if (!spaceCache.containsKey(index)) {
            space = new EventSpace(index);
            spaceCache.put(index, space);
        }
        else if (!(spaceCache.get(index) instanceof StartSpace)) {
            space = new EventSpace(index);
        }
        else {
            space = (EventSpace)spaceCache.get(index);
        }

        return (EventSpace)setXandY(space, x, y);
    }

    public MoveEventSpace createMoveEventSpace(int index, int destination, int x, int y) {
        return createMoveEventSpace(index, destination, false, x, y);
    }

    public MoveEventSpace createMoveEventSpace(int index, int destination, boolean turnsBlue, int x, int y) {
        MoveEventSpace space;
        if (!spaceCache.containsKey(index)) {
            space = new MoveEventSpace(index, destination, turnsBlue);
            spaceCache.put(index, space);
        }
        else if (!(spaceCache.get(index) instanceof MoveEventSpace)) {
            space = new MoveEventSpace(index, destination, turnsBlue);
        }
        else {
            space = (MoveEventSpace)spaceCache.get(index);
        }

        return (MoveEventSpace)setXandY(space, x, y);
    }

    public NonMovementSpace createNonMovementSpace(int index, int x, int y) {
        NonMovementSpace space;
        if (!spaceCache.containsKey(index)) {
            space = new NonMovementSpace(index);
            spaceCache.put(index, space);
        }
        else if (!(spaceCache.get(index) instanceof StartSpace)) {
            space = new NonMovementSpace(index);
        }
        else {
            space = (NonMovementSpace)spaceCache.get(index);
        }

        return (NonMovementSpace)setXandY(space, x, y);
    }

    public AllySpace createAllySpace(int index, int x, int y) {
        AllySpace space;
        if (!spaceCache.containsKey(index)) {
            space = new AllySpace(index);
            spaceCache.put(index, space);
        }
        else if (!(spaceCache.get(index) instanceof StartSpace)) {
            space = new AllySpace(index);
        }
        else {
            space = (AllySpace)spaceCache.get(index);
        }

        return (AllySpace)setXandY(space, x, y);
    }

    public LuckySpace createLuckySpace(int index, int x, int y) {
        LuckySpace space;
        if (!spaceCache.containsKey(index)) {
            space = new LuckySpace(index);
            spaceCache.put(index, space);
        }
        else if (!(spaceCache.get(index) instanceof StartSpace)) {
            space = new LuckySpace(index);
        }
        else {
            space = (LuckySpace)spaceCache.get(index);
        }

        return (LuckySpace)setXandY(space, x, y);
    }

    public BadLuckSpace createBadLuckSpace(int index, int x, int y) {
        BadLuckSpace space;
        if (!spaceCache.containsKey(index)) {
            space = new BadLuckSpace(index);
            spaceCache.put(index, space);
        }
        else if (!(spaceCache.get(index) instanceof StartSpace)) {
            space = new BadLuckSpace(index);
        }
        else {
            space = (BadLuckSpace)spaceCache.get(index);
        }

        return (BadLuckSpace)setXandY(space, x, y);
    }

    public VSSpace createVSSpace(int index, int x, int y) {
        VSSpace space;
        if (!spaceCache.containsKey(index)) {
            space = new VSSpace(index);
            spaceCache.put(index, space);
        }
        else if (!(spaceCache.get(index) instanceof StartSpace)) {
            space = new VSSpace(index);
        }
        else {
            space = (VSSpace)spaceCache.get(index);
        }

        return (VSSpace)setXandY(space, x, y);
    }

    public ItemSpace createItemSpace(int index, int x, int y) {
        ItemSpace space;
        if (!spaceCache.containsKey(index)) {
            space = new ItemSpace(index);
            spaceCache.put(index, space);
        }
        else if (!(spaceCache.get(index) instanceof StartSpace)) {
            space = new ItemSpace(index);
        }
        else {
            space = (ItemSpace)spaceCache.get(index);
        }

        return (ItemSpace)setXandY(space, x, y);
    }

    public StarSpace createStarSpace(int index, int x, int y) {
        return createStarSpace(index, 3, x, y);
    }

    public StarSpace createStarSpace(int index, int coinAmount, int x, int y) {
        StarSpace space;
        if (!spaceCache.containsKey(index)) {
            space = new StarSpace(index, coinAmount);
            spaceCache.put(index, space);
        }
        else if (!(spaceCache.get(index) instanceof StartSpace)) {
            space = new StarSpace(index, coinAmount);
        }
        else {
            space = (StarSpace)spaceCache.get(index);
        }

        return (StarSpace)setXandY(space, x, y);
    }

    private static BaseSpace setXandY(BaseSpace space, int x, int y) {
        if (null != space && 0 < x && 0 < y) {
            space.setX(x);
            space.setY(y);
        }
        return space;
    }
}
