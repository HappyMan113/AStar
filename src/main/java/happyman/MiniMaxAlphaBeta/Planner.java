package happyman.MiniMaxAlphaBeta;

public class Planner
{
    public static<S> Move<S> findBestPlan(MiniMaxProblem<S> problem) //We will assume that max is going first
    {
        Move<S> best = null;
        int highest = Integer.MIN_VALUE;
        for (Move<S> nextMove : problem.getMoves(problem.initialState))
        {
            int minsPick = minValue(problem, nextMove, highest, Integer.MAX_VALUE);
            if (minsPick > highest)
            {
                best = nextMove;
                highest = minsPick;
            }
        }
        return best;
    }

    private static<S> int maxValue(MiniMaxProblem<S> problem,
                                   Move<S> lastMove,
                                   int lowerBound, int upperBound)
    {
        int highest = problem.getUtility(lastMove.state, false);
        if (highest > 0)
        {
            return highest;
        }
        highest = Integer.MIN_VALUE;
        for (Move<S> nextMove : problem.getMoves(lastMove.state))
        {
            highest = Math.max(highest, minValue(problem, nextMove, lowerBound, upperBound));
            if (highest >= upperBound)
            {
                return highest;
            }
            lowerBound = Math.max(lowerBound, highest);
        }
        return highest;
    }

    private static<S> int minValue(MiniMaxProblem<S> problem,
                                   Move<S> lastMove,
                                   int lowerBound, int upperBound)
    {
        int lowest = problem.getUtility(lastMove.state, true);
        if (lowest > 0)
        {
            return lowest;
        }
        lowest = Integer.MAX_VALUE;
        for (Move<S> nextMove : problem.getMoves(lastMove.state))
        {
            lowest = Math.min(lowest, maxValue(problem, nextMove, lowerBound, upperBound));
            if (lowest <= lowerBound)
            {
                return lowest;
            }
            upperBound = Math.min(upperBound, lowest);
        }
        return lowest;
    }
}
