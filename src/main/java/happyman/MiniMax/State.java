package happyman.MiniMax;

public abstract class State
{
    final int utility;
    final boolean terminal;
    final boolean max;

    public State(int utility, boolean max)
    {
        this.utility = utility;
        this.terminal = utility > 0;
        this.max = max;
    }

//    public State()
//    {
//        this.utility = 0;
//        terminal = false;
//    }
//    protected abstract boolean isTerminal();
}
