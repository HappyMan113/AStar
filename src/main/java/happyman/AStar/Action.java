package happyman.AStar;

public abstract class Action<S>
{
    final float cost;

    protected Action(float cost)
    {
        this.cost = cost;
    }

    public abstract S enact(S state);

    @Override
    public String toString()
    {
        return "Action with cost " + cost;
    }
}
