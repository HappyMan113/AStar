package happyman.MiniMax;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class MiniMaxPlanner
{
    static class Pile
    {
        private int amount;

        Pile(int amount)
        {
            this.amount = amount;
        }

        int getAmount()
        {
            return amount;
        }

        void setAmount(int amount)
        {
            this.amount = amount;
        }

        void remove(int amount)
        {
            this.amount -= amount;
        }

        void add(int amount)
        {
            this.amount += amount;
        }
    }

    static class NimState extends State
    {
        final Pile[] piles;

        static int getUtility(Pile[] piles, boolean max)
        {
            for (Pile pile : piles)
            {
                if (pile.getAmount() > 0)
                {
                    return 0;
                }
            }
            return max ? 1 : -1;
        }

        NimState(Pile[] piles, boolean max)
        {
            super(getUtility(piles, max), max);
            this.piles = new Pile[piles.length];
            for (int i = 0; i < this.piles.length; i++)
            {
                this.piles[i] = new Pile(piles[i].getAmount());
            }
        }

        Pile[] getPiles()
        {
            Pile[] result = new Pile[piles.length];
            for (int i = 0; i < piles.length; i++)
            {
                result[i] = piles[i];
            }
            return result;
        }

        Pile getPile(int i)
        {
            return piles[i];
        }
    }

    class NimProblem extends MiniMaxProblem<NimState>
    {
        protected NimProblem(NimState initialState)
        {
            super(initialState);
        }

        @Override
        protected List<Move<NimState>> getMoves(NimState state)
        {
            List<Move<NimState>> result = new LinkedList<>();

            Pile[] piles = state.getPiles();
            for (int pile = 0; pile < piles.length; pile++)
            {
                for (int amountToTake = piles[pile].getAmount(); amountToTake > 0; amountToTake--)
                {
                    result.add(new NimMove(state, amountToTake, pile, !state.max));
                }
            }
            return result;
        }
    }

    static class NimMove extends Move<NimState>
    {
        private static NimState removeFromNimState(NimState state, int amount, int pile, boolean max)
        {
            NimState newState = new NimState(state.piles, max);
            newState.getPile(pile).remove(amount);
            return newState;
        }

        private final int amount;
        private final int pile;

        protected NimMove(NimState originalState, int amount, int pile, boolean max)
        {
            super(removeFromNimState(originalState, amount, pile, max));
            this.amount = amount;
            this.pile = pile;
        }

        @Override
        public String toString()
        {
            return "(" + amount + ", " + pile + ")";
        }
    }

    @Test
    public void testNim()
    {
        NimProblem problem = new NimProblem(new NimState(new Pile[]
                {
                        new Pile(2),
                        new Pile(3),
                        new Pile(5)
                }, true));
        final int it = 40;

        Move<NimState> bestPlan1 = findBestPlan(problem);
        final long start1 = System.currentTimeMillis();
        for (int i = 0; i < it; i++)
        {
            bestPlan1 = findBestPlan(problem);
        }
        final long stop1 = System.currentTimeMillis();

        Move<NimState> bestPlan2 = Planner.findBestPlan(problem);
        final long start2 = System.currentTimeMillis();
        for (int i = 0; i < it; i++)
        {
            bestPlan2 = Planner.findBestPlan(problem);
        }
        final long stop2 = System.currentTimeMillis();

        System.out.println(bestPlan1 + " took " + (float)(stop1 - start1)/it + " ms ");
        System.out.println(bestPlan2 + " took " + (float)(stop2 - start2)/it + " ms ");
    }

    private static<S extends State> Move<S> findBestPlan(MiniMaxProblem<S> problem)
    {
        Move<S> best = null;
        int bestUtility = Integer.MIN_VALUE;
        for (Move<S> move : problem.getMoves(problem.initialState))
        {
            int utility = minValue(problem, move);
            if (utility > bestUtility)
            {
                best = move;
                bestUtility = utility;
            }
        }
        return best;
    }

    private static<S extends State> int maxValue(MiniMaxProblem<S> problem, Move<S> lastMove)
    {
        if (lastMove.state.terminal)
        {
            return lastMove.state.utility;
        }
        int highest = Integer.MIN_VALUE;
        for (Move<S> nextMove : problem.getMoves(lastMove.state))
        {
            int minsPick = minValue(problem, nextMove);
            if (minsPick > highest)
            {
                highest = minsPick;
            }
        }
        return highest;
    }

    private static<S extends State> int minValue(MiniMaxProblem<S> problem, Move<S> lastMove)
    {
        if (lastMove.state.terminal)
        {
            return lastMove.state.utility;
        }
        int lowest = Integer.MAX_VALUE;
        for (Move<S> nextMove : problem.getMoves(lastMove.state))
        {
            int maxsPick = maxValue(problem, nextMove);
            if (maxsPick < lowest)
            {
                lowest = maxsPick;
            }
        }
        return lowest;
    }
}
