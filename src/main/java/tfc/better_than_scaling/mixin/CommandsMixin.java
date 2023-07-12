package tfc.better_than_scaling.mixin;

import net.minecraft.core.net.command.Commands;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.better_than_scaling.cmd.ScaleCommand;

@Mixin(value = Commands.class, remap = false)
public class CommandsMixin {
    @Inject(at = @At("TAIL"), method = "initCommands")
    private static void postInitCommands(CallbackInfo ci) {
        Commands.commands.add(new ScaleCommand(
                "scale"
        ));
    }
}
