package happyman.MiniMaxAlphaBeta;

import happyman.Problem;

import java.util.List;

public abstract class MiniMaxProblem<S> extends Problem<S>
{
    protected MiniMaxProblem(S initialState)
    {
        super(initialState);
    }

    protected abstract int getUtility(S state, boolean isMaxsState);

//    final List<Move<S>> getMovesP(S state)
//    {
//        List<Move<S>> result = getMoves(state);
//        for (Move<S> move : result)
//        {
//            move.state.max = !state.max;
//        }
//        return result;
//    }

    protected abstract List<Move<S>> getMoves(S state);
}
