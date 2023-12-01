package org.voh.smp.boards.spaces.events.KBPM;

import org.voh.smp.boards.layout.MPBoard;
import org.voh.smp.boards.spaces.BaseSpace;
import org.voh.smp.boards.spaces.BlueSpace;
import org.voh.smp.boards.spaces.events.LuckySpace;
import lombok.ToString;
import org.jgrapht.Graphs;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;

@ToString(callSuper = true)
public class KBPMChangePathEvent extends BaseSpace {

    public KBPMChangePathEvent(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public boolean processEvent(MPBoard gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {

        KBPMChangePathEvent firstEventSpace = (KBPMChangePathEvent)gameBoard.getVertexById(27);

        BaseSpace nextSpace = Graphs.successorListOf(gameBoard, firstEventSpace).get(0);

        if (nextSpace instanceof LuckySpace) {
            gameBoard.removeEdge(firstEventSpace, gameBoard.getVertexById(28));
            gameBoard.addEdge(firstEventSpace, gameBoard.getVertexById(38));
        }
        else if (nextSpace instanceof BlueSpace) {
            gameBoard.removeEdge(firstEventSpace, gameBoard.getVertexById(38));
            gameBoard.addEdge(firstEventSpace, gameBoard.getVertexById(28));
        }
        return true;
    }
}
