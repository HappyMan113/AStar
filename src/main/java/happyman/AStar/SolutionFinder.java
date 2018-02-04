package happyman.AStar;

import javax.xml.ws.Holder;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class SolutionFinder
{
    public static<S> Solution<S> findSolution(final Problem<S> problem,
                                              final boolean limitedMemory)
    {
        return limitedMemory ?
                findSolutionWithLimitedMemory(problem) :
                findSolution(problem);
    }

    private static<S> Solution<S> findSolution(final Problem<S> problem)
    {

        final Set<S> explored = new HashSet<>();
        final PriorityQueue<Node<S>> frontier = new PriorityQueue<>();
        frontier.add(new Node<>(problem));

        do
        {
            final Node<S> best = frontier.remove();
            if (problem.isGoal(best.state))
            {
                return new Solution<>(best);
            }
            explored.add(best.state);
            for (final Node<S> neighbor : best.getNeighbors(problem))
            {
                if (!explored.contains(neighbor.state))
                {
                    frontier.add(neighbor);
                }
            }
        } while (!frontier.isEmpty());
        return null;
    }

    private static<S> Solution<S> findSolutionWithLimitedMemory(final Problem<S> problem)
    {
        float estimatedCostLimit = 0;
        while(true)
        {
            final Holder<Float> smallestExceeder = new Holder<>(Float.MAX_VALUE);
            Solution<S> solution = findSolutionWithLimitedMemory(problem, new Node<>(problem), estimatedCostLimit, smallestExceeder);
            if (solution != null)
            {
                return solution;
            }
            estimatedCostLimit = smallestExceeder.value;
        }
    }

//    private static<S> Solution<S> findSolutionID2(final Problem<S> problem,
//                                                  Node<S> node,
//                                                  final float estimatedCostLimit,
//                                                  final Holder<Float> smallestExceeder)
//    {
//        final Stack<Node<S>> stack = new Stack<>();
//        stack.push(node);
//        if (problem.isGoal(node.state))
//        {
//            return new Solution<>(node);
//        }
//        do
//        {
//            node = stack.pop();
//            for (final Node<S> neighbor : node.getNeighbors(problem))
//            {
//                if (neighbor.estimatedOverallCost <= estimatedCostLimit)
//                {
//                    if (problem.isGoal(neighbor.state))
//                    {
//                        return new Solution<>(neighbor);
//                    }
//                    stack.push(neighbor);
//                }
//                else if (neighbor.estimatedOverallCost < smallestExceeder.value)
//                {
//                    smallestExceeder.value = neighbor.estimatedOverallCost;
//                }
//            }
//        } while (!stack.isEmpty());
//        return null;
//    }

    private static<S> Solution<S> findSolutionWithLimitedMemory(final Problem<S> problem,
                                                                final Node<S> node,
                                                                final float estimatedCostLimit,
                                                                final Holder<Float> smallestExceeder)
    {
        if (problem.isGoal(node.state))
        {
            return new Solution<>(node);
        }
        Node<S>[] neighbors = node.getNeighbors(problem);
        QuickSorter.quickSort(neighbors);
        for (final Node<S> neighbor : neighbors)
        {
            if (neighbor.estimatedOverallCost <= estimatedCostLimit)
            {
                Solution<S> solution = findSolutionWithLimitedMemory(problem, neighbor, estimatedCostLimit, smallestExceeder);
                if (solution != null)
                {
                    return solution;
                }
            }
            else if (neighbor.estimatedOverallCost < smallestExceeder.value)
            {
                smallestExceeder.value = neighbor.estimatedOverallCost;
            }
        }
        return null;
    }

//    private static <S> Node<S> popBest(Set<Node<S>> openSet)
//    {
//        final Iterator<Node<S>> it = openSet.iterator();
//        Node<S> best = it.next();
//        while (it.hasNext())
//        {
//            final Node<S> node = it.next();
//            if (node.estimatedOverallCost < best.estimatedOverallCost)
//            {
//                best = node;
//            }
//        }
//        openSet.remove(best);
//        return best;
//    }
}
