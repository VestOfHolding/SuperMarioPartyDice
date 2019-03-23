package simulation;

import boards.BaseBoard;
import boards.MegafruitParadise;
import boards.WhompsDominoRuins;
import boards.spaces.AllySpace;
import boards.spaces.BaseSpace;
import stattracker.GameStatTracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShortestAllyPath {

    private static final int NUM_TURNS = 20;

    private static final int SHY_GUY_ROLL = 4;
    private static final int WARIO_ROLL = 6;
    private static final int DIDDY_ROLL = 7;
    private static final int DK_ROLL = 10;


    public void shortestPathToFirstAlly(BaseBoard board) {
        Map<Integer, List<List<BaseSpace>>> firstAllyPaths = findAllyPaths(board, WARIO_ROLL, NUM_TURNS).stream()
                .collect(Collectors.groupingBy(List::size));

        int i = 0;
    }

    /**
     * An incredible amount of guidance taken from the JGraphT class AllDirectedPaths.
     * I was going to extend that to a new class,
     *  but I wanted something different too much out of the inputs and outputs.
     */
    protected List<List<BaseSpace>> findAllyPaths(BaseBoard gameBoard, int diceRoll, int maxRolls) {
        if (diceRoll <= 0 || maxRolls <= 0) {
            return new ArrayList<>();
        }

        GameStatTracker gameStatTracker = new GameStatTracker();
        List<List<BaseSpace>> finishedPaths = new ArrayList<>();
        Deque<List<BaseSpace>> unfinishedPaths = new LinkedList<>();

        List<BaseSpace> firstRoll = gameBoard.getDestinations(gameBoard.getStartSpace(), diceRoll, gameStatTracker);

        for (BaseSpace space : firstRoll) {
            if (space instanceof AllySpace) {
                finishedPaths.add(Arrays.asList(gameBoard.getStartSpace(), space));
            }
            else {
                unfinishedPaths.add(Stream.of(gameBoard.getStartSpace(), space).collect(Collectors.toList()));
            }
        }

        for (List<BaseSpace> unfinishedPath; (unfinishedPath = unfinishedPaths.poll()) != null;) {
            if (unfinishedPath.size() >= maxRolls) {
                continue;
            }

            BaseSpace lastSpace = unfinishedPath.get(unfinishedPath.size() - 1);

            List<BaseSpace> nextRoll = gameBoard.getDestinations(lastSpace, diceRoll, gameStatTracker);

            for (BaseSpace nextSpace : nextRoll) {
                List<BaseSpace> nextPath = new ArrayList<>(unfinishedPath);
                nextPath.add(nextSpace);

                if (nextSpace instanceof AllySpace) {
                    finishedPaths.add(nextPath);
                    continue;
                }

                unfinishedPaths.addFirst(nextPath);
            }
        }

        return finishedPaths;
    }
}
