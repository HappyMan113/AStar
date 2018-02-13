package happyman.examples;

import happyman.MiniMaxAlphaBeta.MiniMaxProblem;
import happyman.MiniMaxAlphaBeta.Move;
import happyman.MiniMaxAlphaBeta.Planner;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class MiniMaxTest
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

    static class NimState
    {
        final Pile[] piles;

        NimState(Pile[] piles)
        {
            super();
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
        NimProblem(NimState initialState)
        {
            super(initialState);
        }

        @Override
        protected int getUtility(NimState state, boolean isMaxsState)
        {
            for (Pile pile : state.piles)
            {
                if (pile.getAmount() > 0)
                {
                    return 0;
                }
            }
            return isMaxsState ? 1 : -1;
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
                    result.add(new NimMove(state, amountToTake, pile));
                }
            }
            return result;
        }
    }

    static class NimMove extends Move<NimState>
    {
        private static NimState removeFromNimState(NimState state, int amount, int pile)
        {
            NimState newState = new NimState(state.piles);
            newState.getPile(pile).remove(amount);
            return newState;
        }

        private final int amount;
        private final int pile;

        protected NimMove(NimState originalState, int amount, int pile)
        {
            super(removeFromNimState(originalState, amount, pile));
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
                }));
        final int it = 40;

//        Move<NimState> bestPlan1 = MiniMaxPlanner.findBestPlan(problem);
//        final long start1 = System.currentTimeMillis();
//        for (int i = 0; i < it; i++)
//        {
//            bestPlan1 = MiniMaxPlanner.findBestPlan(problem);
//        }
//        final long stop1 = System.currentTimeMillis();

        Move<NimState> bestPlan2 = Planner.findBestPlan(problem);
        final long start2 = System.currentTimeMillis();
        for (int i = 0; i < it; i++)
        {
            bestPlan2 = Planner.findBestPlan(problem);
        }
        final long stop2 = System.currentTimeMillis();

//        System.out.println(bestPlan1 + " took " + (float)(stop1 - start1)/it + " ms ");
        System.out.println(bestPlan2 + " took " + (float)(stop2 - start2)/it + " ms ");
    }



    @Test
    public void testNim2()
    {
        class Node
        {
            private final Node left;
            private final Node right;
            private final int id;
            private final int utility;

            public Node(int id, int utility, Node left, Node right)
            {
                super();
                this.left = left;
                this.right = right;
                this.id = id;
                this.utility = utility;
            }

            public Node(int id, int utility)
            {
                super();
                this.left = null;
                this.right = null;
                this.id = id;
                this.utility = utility;
            }

            public List<Node> getChildren()
            {
                List<Node> result = new LinkedList<>();
                if (left != null)
                {
                    result.add(left);
                }
                if (right != null)
                {
                    result.add(right);
                }
                return result;
            }

            @Override
            public String toString()
            {
                return "" + id;
            }
        }

        Node fourteen = new Node(14, 0);
        Node thirteen = new Node(13, 0);
        Node twelve = new Node(12, 8);
        Node eleven = new Node(11, 8);
        Node ten = new Node(10, 0);
        Node nine = new Node(9, 0);
        Node eight = new Node(8, 0);
        Node seven = new Node(7, 0);
        Node six = new Node(6, 6, thirteen, fourteen);
        Node five = new Node(5, 0, eleven, twelve);
        Node four = new Node(4, 3, nine, ten);
        Node three = new Node(3, 7, seven, eight);
        Node two = new Node(2, 0, five, six);
        Node one = new Node(1, 0, three, four);
        Node root = new Node(0, 0, one, two);

        class NodeMove extends Move<Node>
        {
            protected NodeMove(Node destination)
            {
                super(destination);
            }
        }

        System.out.println(Planner.findBestPlan(new MiniMaxProblem<Node>(root)
        {
            @Override
            protected int getUtility(Node state, boolean isMaxsState)
            {
                return isMaxsState ? state.utility : -state.utility;
            }

            @Override
            protected List<Move<Node>> getMoves(Node state)
            {
                List<Move<Node>> result = new LinkedList<>();
                for (Node child : state.getChildren())
                {
                    result.add(new NodeMove(child));
                }
                return result;
            }
        }));
    }
}
