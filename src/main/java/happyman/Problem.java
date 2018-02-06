package happyman;

public abstract class Problem<S>
{
    public final S initialState;

    protected Problem(S initialState)
    {
        this.initialState = initialState;
    }

    @Override
    public String toString()
    {
        return "problem with an initial state of " + initialState + "";
    }
}
