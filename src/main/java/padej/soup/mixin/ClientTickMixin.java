package padej.soup.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import padej.soup.InfinityVisualsClient;

@Mixin(MinecraftClient.class)
public abstract class ClientTickMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void infinityvisuals$tick(CallbackInfo ci) {
        InfinityVisualsClient.tick();
    }
}