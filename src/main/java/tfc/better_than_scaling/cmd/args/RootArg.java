package tfc.better_than_scaling.cmd.args;

public class RootArg extends Argument {
    public RootArg() {
        super((v) -> {
        });
    }

    public boolean matches(String str) {
        return true;
    }

    public void next(String[] args, int index) {
        for (Argument argument : then) {
            if (argument.matches(args[0])) {
                argument.next(args, 0);
            }
        }
    }
}
