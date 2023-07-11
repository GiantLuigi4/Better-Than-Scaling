package tfc.better_than_scaling.cmd;

import net.minecraft.src.Entity;
import net.minecraft.src.command.Command;
import net.minecraft.src.command.CommandHandler;
import net.minecraft.src.command.CommandSender;
import net.minecraft.src.command.commands.HelpCommand;
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
                ).then(scaleArgument).then(typeArg)
        ).then(
                new StringChoiceArg(
                        (a) -> action[0] = a,
                        "get"
                )
        );

        // parse command
        rootArg.next(args, 0);

        ScaleData data = ((EntityExtensions) commandSender.getPlayer()).getScaleData();
        if (action[0].equals("set")) {
            Entity entity = commandSender.getPlayer();
            double oldWidth = ScaleTypes.WIDTH.calculate(entity);
            double oldHeight = ScaleTypes.HEIGHT.calculate(entity);

            double scl = entity.yOffset * ScaleTypes.EYES.calculate(entity);

            data.setScale(ScaleTypes.byName(type[0]), scale[0]);

            double width = ScaleTypes.WIDTH.calculate(entity);
            double height = ScaleTypes.HEIGHT.calculate(entity);
            if (height != oldHeight || width != oldWidth) {
                entity.setPosition(entity.posX, entity.posY - scl +
                        entity.yOffset * ScaleTypes.EYES.calculate(entity), entity.posZ);
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
        commandSender.sendMessage("test");
    }
}
