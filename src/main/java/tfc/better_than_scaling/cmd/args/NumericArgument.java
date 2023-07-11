package tfc.better_than_scaling.cmd.args;

import java.util.function.Consumer;

public class NumericArgument extends Argument {
    boolean allowFractions;
    boolean allowDecimals;

    public NumericArgument(Consumer<String> execute, boolean allowFractions, boolean allowDecimals) {
        super(execute);
        this.allowFractions = allowFractions;
        this.allowDecimals = allowDecimals;
    }

    @Override
    public boolean matches(String str) {
        return handle(str) != null;
    }

    @Override
    public String handle(String arg) {
        try {
            int i = Integer.parseInt(arg);
            return "" + i;
        } catch (Throwable err) {
        }
        if (allowDecimals) {
            try {
                double i = Double.parseDouble(arg);
                return "" + i;
            } catch (Throwable err) {
            }
        }
        if (allowFractions) {
            try {
                String[] split = arg.split("/");
                return "" + (Double.parseDouble(split[0]) / Double.parseDouble(split[1]));
            } catch (Throwable err) {
            }
        }
        return null;
    }
}
