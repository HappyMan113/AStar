package happyman.AStar;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SolutionFinder
{
    public static<S> Solution<S> findSolution(final Problem<S> problem)
    {
        final Set<S> explored = new HashSet<>();
        final Set<Node<S>> frontier = new HashSet<>();
        frontier.add(new Node<>(problem));

        do
        {
            final Node<S> best = popBest(frontier);
            if (problem.isGoal(best.state))
            {
                return new Solution<>(best);
            }
            explored.add(best.state);
            for (Node<S> neighbor : best.getNeighbors(problem))
            {
                if (!explored.contains(neighbor.state))
                {
                    frontier.add(neighbor);
                }
            }
        } while (!frontier.isEmpty());
        return null;
    }

    private static <S> Node<S> popBest(Set<Node<S>> openSet)
    {
        final Iterator<Node<S>> it = openSet.iterator();
        Node<S> best = it.next();
        while (it.hasNext())
        {
            final Node<S> node = it.next();
            if (node.estimatedOverallCost < best.estimatedOverallCost)
            {
                best = node;
            }
        }
        openSet.remove(best);
        return best;
    }
}
