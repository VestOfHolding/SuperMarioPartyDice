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
        return createStartSpace(index, -1, -1);
    }

    public static StartSpace createStartSpace(int index, int x, int y) {
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

    public static BlueSpace createBlueSpace(int index) {
        return createBlueSpace(index, 3);
    }

    public static BlueSpace createBlueSpace(int index, int coinAmount) {
        return createBlueSpace(index, coinAmount, -1, -1);
    }

    public static BlueSpace createBlueSpace(int index, int coinAmount, int x, int y) {
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

    public static RedSpace createRedSpace(int index) {
        return createRedSpace(index, 3);
    }

    public static RedSpace createRedSpace(int index, int coinAmount) {
        return createRedSpace(index, coinAmount, -1, -1);
    }

    public static RedSpace createRedSpace(int index, int coinAmount, int x, int y) {
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

    public static EventSpace createEventSpace(int index) {
        return createEventSpace(index, -1, -1);
    }

    public static EventSpace createEventSpace(int index, int x, int y) {
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

    public static MoveEventSpace createMoveEventSpace(int index, int destination) {
        return createMoveEventSpace(index, destination, false);
    }

    public static MoveEventSpace createMoveEventSpace(int index, int destination, boolean turnsBlue) {
        return createMoveEventSpace(index, destination, turnsBlue, -1, -1);
    }

    public static MoveEventSpace createMoveEventSpace(int index, int destination, int x, int y) {
        return createMoveEventSpace(index, destination, false, x, y);
    }

    public static MoveEventSpace createMoveEventSpace(int index, int destination, boolean turnsBlue, int x, int y) {
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

    public static NonMovementSpace createNonMovementSpace(int index) {
        return createNonMovementSpace(index, -1, -1);
    }

    public static NonMovementSpace createNonMovementSpace(int index, int x, int y) {
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

    public static OtherSpace createOtherSpace(int index) {
        return createOtherSpace(index, -1, -1);
    }

    public static OtherSpace createOtherSpace(int index, int x, int y) {
        OtherSpace space;
        if (!spaceCache.containsKey(index)) {
            space = new OtherSpace(index);
            spaceCache.put(index, space);
        }
        else if (!(spaceCache.get(index) instanceof StartSpace)) {
            space = new OtherSpace(index);
        }
        else {
            space = (OtherSpace)spaceCache.get(index);
        }

        return (OtherSpace)setXandY(space, x, y);
    }

    public static AllySpace createAllySpace(int index) {
        return createAllySpace(index, -1, -1);
    }

    public static AllySpace createAllySpace(int index, int x, int y) {
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

    public static LuckySpace createLuckySpace(int index) {
        return createLuckySpace(index, -1, -1);
    }

    public static LuckySpace createLuckySpace(int index, int x, int y) {
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

    public static BadLuckSpace createBadLuckSpace(int index) {
        return createBadLuckSpace(index, -1, -1);
    }

    public static BadLuckSpace createBadLuckSpace(int index, int x, int y) {
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

    public static VSSpace createVSSpace(int index) {
        return createVSSpace(index, -1, -1);
    }

    public static VSSpace createVSSpace(int index, int x, int y) {
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

    private static BaseSpace setXandY(BaseSpace space, int x, int y) {
        if (space != null && x > 0 && y > 0) {
            space.setX(x);
            space.setY(y);
        }
        return space;
    }
}
