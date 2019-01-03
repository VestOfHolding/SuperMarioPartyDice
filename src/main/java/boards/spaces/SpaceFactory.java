package boards.spaces;

import boards.spaces.events.BadLuckSpace;
import boards.spaces.events.EventSpace;
import boards.spaces.events.LuckySpace;
import boards.spaces.events.MoveEventSpace;
import boards.spaces.events.VSSpace;

import java.util.HashMap;
import java.util.Map;

public class SpaceFactory {
    public static Map<Integer, BaseSpace> spaceCache = new HashMap<>();

    public static StartSpace createStartSpace(int index) {
        if (!spaceCache.containsKey(index)) {
            StartSpace space = new StartSpace(index);
            spaceCache.put(index, space);
            return space;
        }
        else if (!(spaceCache.get(index) instanceof StartSpace)) {
            return new StartSpace(index);
        }
        else {
            return (StartSpace)spaceCache.get(index);
        }
    }

    public static BlueSpace createBlueSpace(int index) {
        return createBlueSpace(index, 3);
    }

    public static BlueSpace createBlueSpace(int index, int coinAmount) {
        if (!spaceCache.containsKey(index)) {
            BlueSpace space = new BlueSpace(index, coinAmount);
            spaceCache.put(index, space);
            return space;
        }
        else if (!(spaceCache.get(index) instanceof BlueSpace)) {
            return new BlueSpace(index, coinAmount);
        }
        else {
            return (BlueSpace)spaceCache.get(index);
        }
    }

    public static RedSpace createRedSpace(int index) {
        return createRedSpace(index, 3);
    }

    public static RedSpace createRedSpace(int index, int coinAmount) {
        if (!spaceCache.containsKey(index)) {
            RedSpace space = new RedSpace(index, coinAmount);
            spaceCache.put(index, space);
            return space;
        }
        else if (!(spaceCache.get(index) instanceof RedSpace)) {
            return new RedSpace(index, coinAmount);
        }
        else {
            return (RedSpace)spaceCache.get(index);
        }
    }

    public static EventSpace createEventSpace(int index) {
        if (!spaceCache.containsKey(index)) {
            EventSpace space = new EventSpace(index);
            spaceCache.put(index, space);
            return space;
        }
        else if (!(spaceCache.get(index) instanceof EventSpace)) {
            return new EventSpace(index);
        }
        else {
            return (EventSpace)spaceCache.get(index);
        }
    }

    public static MoveEventSpace createMoveEventSpace(int index, int destination) {
        return createMoveEventSpace(index, destination, false);
    }

    public static MoveEventSpace createMoveEventSpace(int index, int destination, boolean turnsBlue) {
        if (!spaceCache.containsKey(index)) {
            MoveEventSpace space = new MoveEventSpace(index, destination, turnsBlue);
            spaceCache.put(index, space);
            return space;
        }
        else if (!(spaceCache.get(index) instanceof MoveEventSpace)) {
            return new MoveEventSpace(index, destination, turnsBlue);
        }
        else {
            return (MoveEventSpace)spaceCache.get(index);
        }
    }

    public static NonMovementSpace createNonMovementSpace(int index) {
        if (!spaceCache.containsKey(index)) {
            NonMovementSpace space = new NonMovementSpace(index);
            spaceCache.put(index, space);
            return space;
        }
        else if (!(spaceCache.get(index) instanceof NonMovementSpace)) {
            return new NonMovementSpace(index);
        }
        else {
            return (NonMovementSpace)spaceCache.get(index);
        }
    }

    public static OtherSpace createOtherSpace(int index) {
        if (!spaceCache.containsKey(index)) {
            OtherSpace space = new OtherSpace(index);
            spaceCache.put(index, space);
            return space;
        }
        else if (!(spaceCache.get(index) instanceof OtherSpace)) {
            return new OtherSpace(index);
        }
        else {
            return (OtherSpace)spaceCache.get(index);
        }
    }

    public static AllySpace createAllySpace(int index) {
        if (!spaceCache.containsKey(index)) {
            AllySpace space = new AllySpace(index);
            spaceCache.put(index, space);
            return space;
        }
        else if (!(spaceCache.get(index) instanceof AllySpace)) {
            return new AllySpace(index);
        }
        else {
            return (AllySpace)spaceCache.get(index);
        }
    }

    public static LuckySpace createLuckySpace(int index) {
        if (!spaceCache.containsKey(index)) {
            LuckySpace space = new LuckySpace(index);
            spaceCache.put(index, space);
            return space;
        }
        else if (!(spaceCache.get(index) instanceof LuckySpace)) {
            return new LuckySpace(index);
        }
        else {
            return (LuckySpace)spaceCache.get(index);
        }
    }

    public static BadLuckSpace createBadLuckSpace(int index) {
        if (!spaceCache.containsKey(index)) {
            BadLuckSpace space = new BadLuckSpace(index);
            spaceCache.put(index, space);
            return space;
        }
        else if (!(spaceCache.get(index) instanceof BadLuckSpace)) {
            return new BadLuckSpace(index);
        }
        else {
            return (BadLuckSpace)spaceCache.get(index);
        }
    }

    public static VSSpace createVSSpace(int index) {
        if (!spaceCache.containsKey(index)) {
            VSSpace space = new VSSpace(index);
            spaceCache.put(index, space);
            return space;
        }
        else if (!(spaceCache.get(index) instanceof VSSpace)) {
            return new VSSpace(index);
        }
        else {
            return (VSSpace)spaceCache.get(index);
        }
    }
}
