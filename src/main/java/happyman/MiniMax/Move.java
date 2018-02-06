package happyman.MiniMax;

public class Move<S>
{
    final S state;

    protected Move(S destination)
    {
        this.state = destination;
    }
}
