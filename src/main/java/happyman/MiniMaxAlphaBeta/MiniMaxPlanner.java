//package happyman.MiniMax;
//
//public class MiniMaxPlanner
//{
//    public static<S extends State> Move<S> findBestPlan(MiniMaxProblem<S> problem)
//    {
//        Move<S> best = null;
//        int bestUtility = Integer.MIN_VALUE;
//        for (Move<S> move : problem.getMoves(problem.initialState))
//        {
//            int utility = minValue(problem, move);
//            if (utility > bestUtility)
//            {
//                best = move;
//                bestUtility = utility;
//            }
//        }
//        return best;
//    }
//
//    private static<S extends State> int maxValue(MiniMaxProblem<S> problem, Move<S> lastMove)
//    {
//        if (lastMove.state.terminal)
//        {
//            return lastMove.state.utility;
//        }
//        int highest = Integer.MIN_VALUE;
//        for (Move<S> nextMove : problem.getMoves(lastMove.state))
//        {
//            int minsPick = minValue(problem, nextMove);
//            if (minsPick > highest)
//            {
//                highest = minsPick;
//            }
//        }
//        return highest;
//    }
//
//    private static<S extends State> int minValue(MiniMaxProblem<S> problem, Move<S> lastMove)
//    {
//        if (lastMove.state.terminal)
//        {
//            return lastMove.state.utility;
//        }
//        int lowest = Integer.MAX_VALUE;
//        for (Move<S> nextMove : problem.getMoves(lastMove.state))
//        {
//            int maxsPick = maxValue(problem, nextMove);
//            if (maxsPick < lowest)
//            {
//                lowest = maxsPick;
//            }
//        }
//        return lowest;
//    }
//}
