package tfc.better_than_scaling.cmd.args;

import java.util.function.Consumer;

public class Argument {
    Argument[] then = new Argument[0];
    Consumer<String> execute;

    public Argument(Consumer<String> execute) {
        this.execute = execute;
    }

    public Argument then(Argument arg) {
        Argument[] andThen = new Argument[then.length + 1];
        System.arraycopy(then, 0, andThen, 0, then.length);
        then = andThen;
        andThen[andThen.length - 1] = arg;
        return this;
    }

    public boolean matches(String str) {
        return false;
    }

    public String handle(String arg) {
        return arg;
    }

    public void next(String[] args, int index) {
        execute.accept(handle(args[index]));
        for (Argument argument : then) {
            if (argument.matches(args[index + 1])) {
                argument.next(args, index + 1);
            }
        }
    }
}
