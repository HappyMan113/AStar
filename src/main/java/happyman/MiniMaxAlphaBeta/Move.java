package happyman.MiniMaxAlphaBeta;

public class Move<S>
{
    final S state;

    protected Move(S destination)
    {
        this.state = destination;
    }

    @Override
    public String toString()
    {
        return state.toString();
    }
}
