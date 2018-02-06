package happyman.MiniMax;

public class Planner
{
    public static<S extends State> Move<S> findBestPlan(MiniMaxProblem<S> problem)
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

    private static<S extends State> int maxValue(MiniMaxProblem<S> problem,
                                                 Move<S> lastMove,
                                                 int lowerBound, int upperBound)
    {
        if (lastMove.state.terminal)
        {
            return lastMove.state.utility;
        }
        int highest = Integer.MIN_VALUE;
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

    private static<S extends State> int minValue(MiniMaxProblem<S> problem,
                                                 Move<S> lastMove,
                                                 int lowerBound, int upperBound)
    {
        if (lastMove.state.terminal)
        {
            return lastMove.state.utility;
        }
        int lowest = Integer.MAX_VALUE;
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
