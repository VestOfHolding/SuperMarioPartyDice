package boards;

import boards.spaces.BaseSpace;
import org.jgrapht.graph.DefaultEdge;

public class MPEdge extends DefaultEdge {

    @Override
    public BaseSpace getSource() {
        return (BaseSpace)super.getSource();
    }

    @Override
    public BaseSpace getTarget() {
        return (BaseSpace)super.getTarget();
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
