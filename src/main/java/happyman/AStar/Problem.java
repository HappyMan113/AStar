package happyman.AStar;

import java.util.List;

public abstract class Problem<S>
{
    final S initialState;

    protected Problem(S initialState)
    {
        this.initialState = initialState;
    }

    protected abstract float getHeuristicCostToReachGoalFrom(S state);

    protected abstract boolean isGoal(S state);

    protected abstract List<Action<S>> getActions(S state);

    @Override
    public String toString()
    {
        return "\"Problem with an initial state of " + initialState + ".\"";
    }
}
