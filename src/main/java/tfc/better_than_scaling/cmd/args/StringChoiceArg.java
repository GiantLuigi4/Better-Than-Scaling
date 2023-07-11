package tfc.better_than_scaling.cmd.args;

import java.util.function.Consumer;

public class StringChoiceArg extends Argument {
    String[] options;

    public StringChoiceArg(Consumer<String> execute, String... options) {
        super(execute);
        this.options = options;
    }

    @Override
    public boolean matches(String str) {
        for (String option : options) {
            if (option.equals(str)) {
                return true;
            }
        }
        return false;
    }
}
