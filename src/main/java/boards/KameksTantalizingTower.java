package boards;

import boards.spaces.BaseSpace;
import boards.spaces.BlueSpace;
import boards.spaces.RedSpace;
import boards.spaces.SpaceFactory;
import boards.spaces.events.EventSpace;
import boards.spaces.events.KTT.ChainChompSpace;
import boards.spaces.events.KTT.ForcedShopSpace;
import boards.spaces.events.KTT.ThwompShortcutSpace;
import boards.spaces.events.SandBridgeCollapse;
import stattracker.GameStatTracker;

public class KameksTantalizingTower extends BaseBoard  {
    public KameksTantalizingTower() {
        initializeBoard();
    }

    @Override
    protected void buildInitialGraph() {
        int index = 0;

        graphBuilder.addEdgeChain(
                //Start
                SpaceFactory.createStartSpace(index++), //ID = 0
                //Main path to star
                SpaceFactory.createMoveEventSpace(index++, 23),
                newBlueKamekSpace(index++),
                newBlueKamekSpace(index++),
                newBlueKamekSpace(index++),
                SpaceFactory.createNonMovementSpace(index++),
                newBlueKamekSpace(index++),
                newBlueKamekSpace(index++),
                newBlueKamekSpace(index++),
                SpaceFactory.createNonMovementSpace(index++), //ID = 9
                SpaceFactory.createLuckySpace(index++),
                newBlueKamekSpace(index++),
                new ForcedShopSpace(index++), //ID = 12
                newBlueKamekSpace(index++),
                newBlueKamekSpace(index++),
                new ChainChompSpace(index++),
                new ChainChompSpace(index++),
                newBlueKamekSpace(index++), //ID = 17
                SpaceFactory.createBadLuckSpace(index++),
                SpaceFactory.createEventSpace(index++),
                SpaceFactory.createNonMovementSpace(index++),
                SpaceFactory.createNonMovementSpace(index++) //ID = 21
        ).addEdgeChain(
                //Past the Thwomp
                newRedKamekSpace(index++), //ID = 22
                newBlueKamekSpace(index++),
                newBlueKamekSpace(index++),
                SpaceFactory.createMoveEventSpace(index++, 30), //ID = 25
                SpaceFactory.createNonMovementSpace(index++), //ID = 26
                SpaceFactory.createOtherSpace(index++) //ID = 27
        ).addEdgeChain(
                //Through the Thwomp
                new ThwompShortcutSpace(index++), //ID = 28
                newBlueKamekSpace(index++) //ID = 29
        ).addEdgeChain(
                //Odd side path
                newBlueKamekSpace(index++), //ID = 30
                SpaceFactory.createNonMovementSpace(index++),
                SpaceFactory.createVSSpace(index++),
                SpaceFactory.createNonMovementSpace(index++)  //ID = 33
        ).addEdgeChain(
                //And wrapping it back around
                newRedKamekSpace(index++), //ID = 34
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createAllySpace(index++),
                SpaceFactory.createVSSpace(index) //ID = 37
        );

        board = graphBuilder.build();
        connectPaths();
    }

    @Override
    public void lastThreeTurns() {
        setRedAndBlueCoinAmounts(10);
    }

    @Override
    public void resetRedAndBlueCoinAmounts() {
        setRedAndBlueCoinAmounts(6);
    }

    @Override
    public void resetBoard() {
        board.setOrReplaceVertex(32, SpaceFactory.createVSSpace(32));
        board.setOrReplaceVertex(37, SpaceFactory.createVSSpace(37));

        board.setOrReplaceVertex(28, new ThwompShortcutSpace(28));

        resetRedAndBlueCoinAmounts();
    }

    private void connectPaths() {
        board.addEdge(board.getVertexById(9), board.getVertexById(22));
        board.addEdge(board.getVertexById(27), board.getVertexById(10));
        board.addEdge(board.getVertexById(26), board.getVertexById(28));
        board.addEdge(board.getVertexById(29), board.getVertexById(17));
        board.addEdge(board.getVertexById(37), board.getVertexById(1));

        board.addEdge(board.getVertexById(21), board.getVertexById(34));
        board.addEdge(board.getVertexById(33), board.getVertexById(34));
    }
    
    private BlueSpace newBlueKamekSpace(int index) {
        return SpaceFactory.createBlueSpace(index,6);
    }
    
    private RedSpace newRedKamekSpace(int index) {
        return SpaceFactory.createRedSpace(index,-6);
    }

    @Override
    public BaseSpace getDestination(BaseSpace currentSpace, int distance, GameStatTracker gameStatTracker) {

        for (int i = 0; i < distance; ++i) {
            currentSpace = getNextSpace(currentSpace, gameStatTracker);

            if (!currentSpace.affectsMovement()) {
                i -= 1;
            }

            if (currentSpace.isPassingEvent()) {
                if (currentSpace instanceof SandBridgeCollapse) {
                    currentSpace = processBridgeCollapseEvent(gameStatTracker, currentSpace);
                }
                else {
                    currentSpace = processEvent(gameStatTracker, currentSpace);
                }
            }
        }

        if (currentSpace instanceof EventSpace && !currentSpace.isPassingEvent()) {
            currentSpace = processEvent(gameStatTracker, currentSpace);
        }

        return currentSpace;
    }
}
