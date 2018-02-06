package happyman.AStar;

import happyman.Problem;

import java.util.List;

public abstract class AStarProblem<S> extends Problem<S>
{
    protected AStarProblem(S initialState)
    {
        super(initialState);
    }

    protected abstract List<Action<S>> getActions(S state);

    protected abstract boolean isTerminal(S state);

    protected abstract float getHeuristicCostToReachGoalFrom(S state);
}
