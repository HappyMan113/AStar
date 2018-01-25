package happyman.AStar;

import java.util.LinkedList;
import java.util.List;

public final class Node<S>
{
    private final float costSoFar;
    final S state;
    float estimatedOverallCost;
    private Node<S> parent;
    private final Action<S> action;

    private Node(Problem<S> problem, Node<S> parent, Action<S> action)
    {
        this.costSoFar = parent.costSoFar + action.cost;
        this.state = action.enact(parent.state);
        this.estimatedOverallCost = this.costSoFar + problem.getHeuristicCostToReachGoalFrom(this.state);
        this.parent = parent;
        this.action = action;
    }

    Node(Problem<S> problem)
    {
        this.costSoFar = 0;
        this.state = problem.initialState;
        this.estimatedOverallCost = problem.getHeuristicCostToReachGoalFrom(this.state);
        this.parent = null;
        this.action = null;
    }

    final List<Action<S>> getActions()
    {
        final List<Action<S>> actions = new LinkedList<>();
        for (Node<S> node = this; node != null && node.action != null; node = node.parent)
        {
            actions.add(0, node.action);
        }
        return actions;
    }

    @Override
    public final String toString()
    {
        final List<Action<S>> actions = getActions();
        if (actions.size() == 0)
        {
            return "solution: Do nothing (already in goal state " + state + ").";
        }
        else
        {
            final StringBuilder result = new StringBuilder().append(actions.size()).append("-step solution:").append('\n');
            for (Action<S> action : actions)
            {
                result.append(action).append('\n');
            }
            result.append("and arrive at ").append(state);
            return result.toString();
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Node)
        {
            Node other = (Node)o;
            return other.state.equals(this.state);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return state.hashCode();
    }

    List<Node<S>> getNeighbors(final Problem<S> problem)
    {
        List<Node<S>> neighbors = new LinkedList<>();
        for (Action<S> action : problem.getActions(state))
        {
            neighbors.add(new Node<>(problem, this, action));
        }
        return neighbors;
    }
}
