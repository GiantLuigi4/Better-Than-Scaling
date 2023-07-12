package tfc.better_than_scaling.cmd;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.net.command.TextFormatting;
import tfc.better_than_scaling.api.ScaleData;
import tfc.better_than_scaling.api.ScaleType;
import tfc.better_than_scaling.api.ScaleTypes;
import tfc.better_than_scaling.cmd.args.Argument;
import tfc.better_than_scaling.cmd.args.NumericArgument;
import tfc.better_than_scaling.cmd.args.RootArg;
import tfc.better_than_scaling.cmd.args.StringChoiceArg;
import tfc.better_than_scaling.ducks.EntityExtensions;

import java.util.ArrayList;

public class ScaleCommand extends Command {

    public ScaleCommand(String name, String... alts) {
        super(name, alts);
    }

    @Override
    public boolean execute(CommandHandler commandHandler, CommandSender commandSender, String[] args) {
        final String[] action = {"set"};
        final String[] type = {ScaleTypes.BASE.name};
        final double[] scale = {1};

        RootArg rootArg = new RootArg();

        if (args.length == 0) {
            commandHandler.sendCommandFeedback(commandSender, "Syntax:");

            commandHandler.sendCommandFeedback(commandSender, TextFormatting.LIGHT_GRAY + "/scale [number]");
            commandHandler.sendCommandFeedback(commandSender, TextFormatting.LIGHT_GRAY + "/scale set [number]");
            commandHandler.sendCommandFeedback(commandSender, "Sets your base scale");

            commandHandler.sendCommandFeedback(commandSender, TextFormatting.LIGHT_GRAY + "/scale [type] [number]");
            commandHandler.sendCommandFeedback(commandSender, TextFormatting.LIGHT_GRAY + "/scale set [type] [number]");
            commandHandler.sendCommandFeedback(commandSender, "Sets your scale factor for a scale type");

            commandHandler.sendCommandFeedback(commandSender, TextFormatting.LIGHT_GRAY + "/scale get");
            commandHandler.sendCommandFeedback(commandSender, "Gets your base scale");

            commandHandler.sendCommandFeedback(commandSender, TextFormatting.LIGHT_GRAY + "/scale get");
            commandHandler.sendCommandFeedback(commandSender, "Gets your scale factor for a scale type");

            return true;
        }

        NumericArgument scaleArgument = new NumericArgument(
                (v) -> scale[0] = Double.parseDouble(v),
                true, true
        );

        ScaleType[] types = ScaleTypes.getScaleTypes();
        ArrayList<String> choices = new ArrayList<>();
        ArrayList<String> specialChoices = new ArrayList<>();
        for (int i = 0; i < types.length; i++) {
            choices.add(types[i].name);
            if (types[i].name.startsWith("better_than_scaling:")) {
                String str = types[i].name.substring("better_than_scaling:".length());
                choices.add(str);
                specialChoices.add(str);
            }
        }

        Argument typeArg = new StringChoiceArg(
                (v) -> {
                    if (specialChoices.contains(v))
                        v = "better_than_scaling:" + v;
                    type[0] = v;
                },
                choices.toArray(new String[0])
        ).then(scaleArgument);

        rootArg.then(
                new StringChoiceArg(
                        (a) -> action[0] = a,
                        "set"
                ).then(typeArg).then(scaleArgument)
        ).then(
                new StringChoiceArg(
                        (a) -> action[0] = a,
                        "get"
                )
        ).then(typeArg).then(scaleArgument);

        // parse command
        rootArg.next(args, 0);

        ScaleData data = ((EntityExtensions) commandSender.getPlayer()).getScaleData();
        if (action[0].equals("set")) {
            Entity entity = commandSender.getPlayer();
            double oldWidth = ScaleTypes.WIDTH.calculate(entity);
            double oldHeight = ScaleTypes.HEIGHT.calculate(entity);

            double scl = entity.heightOffset * ScaleTypes.EYES.calculate(entity);
            double oy = entity.y - scl;

            ScaleTypes.byName(type[0]).set(entity, scale[0]);

            double width = ScaleTypes.WIDTH.calculate(entity);
            double height = ScaleTypes.HEIGHT.calculate(entity);
            // update hitbox if necessary
            if (height != oldHeight || width != oldWidth) {
                entity.setPos(entity.x, entity.y - scl +
                        entity.heightOffset * ScaleTypes.EYES.calculate(entity), entity.z);
            }

            double ny = entity.y - entity.heightOffset * ScaleTypes.EYES.calculate(entity);
            if (oy > ny) {
                entity.move(0, 0.05, 0);
            }

        } else if (action[0].equals("get")) {
            commandHandler.sendCommandFeedback(
                    commandSender,
                    "" + ScaleTypes.byName(type[0]).calculate(commandSender.getPlayer())
            );
        }

        return true;
    }

    @Override
    public boolean opRequired(String[] strings) {
        return true;
    }

    @Override
    public void sendCommandSyntax(CommandHandler commandHandler, CommandSender commandSender) {
        commandSender.sendMessage("/test");
    }
}
