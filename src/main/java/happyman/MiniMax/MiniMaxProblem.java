package happyman.MiniMax;

import happyman.Problem;

import java.util.List;

public abstract class MiniMaxProblem<S> extends Problem<S>
{
    protected MiniMaxProblem(S initialState)
    {
        super(initialState);
    }

    protected abstract List<Move<S>> getMoves(S state);
}
