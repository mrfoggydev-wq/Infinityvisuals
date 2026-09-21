package padej.soup.mixin;

import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Fog;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import padej.soup.feature.FunctionalFeatures;

@Mixin(BackgroundRenderer.class)
public final class BackgroundRendererMixin {
    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void infinityvisuals$noFog(
            Camera camera,
            BackgroundRenderer.FogType fogType,
            Vector4f color,
            float viewDistance,
            boolean thickenFog,
            float tickDelta,
            CallbackInfoReturnable<Fog> cir) {
        if (FunctionalFeatures.enabled("NoFog")) {
            cir.setReturnValue(new Fog(
                    viewDistance,
                    100000.0f,
                    net.minecraft.client.render.FogShape.SPHERE,
                    color.x(), color.y(), color.z(), color.w()));
        }
    }
}
