package padej.soup.mixin;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import padej.soup.feature.FunctionalFeatures;

@Mixin(GameRenderer.class)
public final class GameRendererMixin {
    @Inject(method = "tiltViewWhenHurt", at = @At("HEAD"), cancellable = true)
    private void infinityvisuals$noHurtCam(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (FunctionalFeatures.enabled("NoHurtCam")) {
            ci.cancel();
        }
    }
}
