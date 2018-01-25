package happyman.AStar;

import java.util.List;

public final class Solution<S>
{
    private final Node<S> solutionNode;

    Solution(Node<S> solutionNode)
    {
        this.solutionNode = solutionNode;
    }

    @Override
    public final String toString()
    {
        return solutionNode.toString();
    }

    public final List<Action<S>> getActions()
    {
        return solutionNode.getActions();
    }

    public final S getState()
    {
        return solutionNode.state;
    }
}
