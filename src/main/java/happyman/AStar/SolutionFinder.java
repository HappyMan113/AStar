package happyman.AStar;

import javax.xml.ws.Holder;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SolutionFinder
{
    public static<S> Solution<S> findSolution(final Problem<S> problem)
    {
        float upperBound = 0;
        final Holder<Float> lowestExceeder = new Holder<>();
        while (true)
        {
            lowestExceeder.value = Float.MAX_VALUE;
            Solution<S> solution = findSolution(problem, upperBound, lowestExceeder);
            if (solution != null)
            {
                return solution;
            }
            upperBound = lowestExceeder.value;
        }
    }

    private static<S> Solution<S> findSolution(final Problem<S> problem, final float upperBound, final Holder<Float> lowestExceeder)
    {
        final Set<S> closedSet = new HashSet<>();
        final Set<Node<S>> openSet = new HashSet<>();

        openSet.add(new Node<>(problem));

        do
        {
            final Node<S> best = popBest(openSet);
            if (problem.isGoal(best.state))
            {
                return new Solution<>(best);
            }

            closedSet.add(best.state);

            best.getNeighbors(problem, neighbor ->
            {
                if (!closedSet.contains(neighbor.state))
                {
                    if (neighbor.estimatedOverallCost <= upperBound)
                    {
                        openSet.add(neighbor);
                    }
                    else if (neighbor.estimatedOverallCost < lowestExceeder.value)
                    {
                        lowestExceeder.value = neighbor.estimatedOverallCost;
                    }
                }
            });
        } while (!openSet.isEmpty());
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
