package org.voh.smp.boards;

import org.voh.smp.boards.spaces.BaseSpace;
import org.voh.smp.boards.spaces.StarSpace;
import org.jgrapht.graph.DefaultWeightedEdge;

public class MPEdge extends DefaultWeightedEdge {

    @Override
    public BaseSpace getSource() {
        return (BaseSpace)super.getSource();
    }

    @Override
    public BaseSpace getTarget() {
        return (BaseSpace)super.getTarget();
    }

    /**
     * Star spaces only don't count towards movement, and therefore don't have an incoming edge weight,
     *  if the star is currently active, which can obviously change over the course of the game.
     *
     *  Therefore, if the target of this edge is a star space, check again if it currently affects movement.
     */
    @Override
    public double getWeight() {
        double weight = super.getWeight();

        return getTarget() instanceof StarSpace && !getTarget().affectsMovement()
                ? 0.0 : weight;
    }

    public String getSourceName() {
        return Integer.toString(getSource().getSpaceID());
    }

    public String getTargetName() {
        return Integer.toString(getTarget().getSpaceID());
    }

    public String getEdgeName() {
        return getSourceName() + getTargetName();
    }
}
