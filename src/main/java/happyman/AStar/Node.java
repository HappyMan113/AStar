package happyman.AStar;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public final class Node<S>
{
    private final float cost;
    final S state;
    float estimatedOverallCost;
    private Node<S> parent;
    private final Action<S> action;

    Node(Problem<S> problem, Node<S> parent, Action<S> action)
    {
        this.cost = parent.cost + action.cost;
        this.state = action.enact(parent.state);
        this.estimatedOverallCost = this.cost + problem.getHeuristicCostToReachGoalFrom(this.state);
        this.parent = parent;
        this.action = action;
    }

    Node(Problem<S> problem)
    {
        this.cost = 0;
        this.state = problem.initialState;
        this.estimatedOverallCost = problem.getHeuristicCostToReachGoalFrom(this.state);
        this.parent = null;
        this.action = null;
    }

    public final List<Action<S>> getActions()
    {
        final List<Action<S>> actions = new LinkedList<>();
        Node<S> node = this;
        if (node.action != null)
        {
            for (; node != null; node = node.parent)
            {
                actions.add(0, node.action);
            }
        }
        return actions;
    }

    @Override
    public final String toString()
    {
        if (action == null)
        {
            return "Do nothing";
        }
        else
        {
            final List<Action<S>> actions = new LinkedList<>();
            Node<S> root = this;
            for (Node<S> node = this; node != null; node = node.parent)
            {
                if (node.action != null)
                {
                    actions.add(0, node.action);
                }
                else
                {
                    root = node;
                    break;
                }
            }
            return actions.size() + "-step solution: Starting at " + root.state + ", " + actions.toString() + " and arrive at " + state;
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

    void getNeighbors(final Problem<S> problem, Consumer<Node<S>> consumer)
    {
        for (Action<S> action : problem.getActions(state))
        {
            consumer.accept(new Node<>(problem, this, action));
        }
    }
}
