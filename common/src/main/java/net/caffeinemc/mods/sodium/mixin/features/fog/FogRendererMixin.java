package net.caffeinemc.mods.sodium.mixin.features.fog;

import com.mojang.blaze3d.shaders.FogShape;
import net.caffeinemc.mods.sodium.client.gui.SodiumGameOptions;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @Unique
    private static final float FOG_START = -8.0F;
    @Unique
    private static final float FOG_END = 1_000_000.0F;

    @Inject(method = "setupFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogStart(F)V", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void modifyFogDistance(Camera camera, FogRenderer.FogMode fogMode, float farPlaneDistance, boolean shouldCreateFog, float partialTick, CallbackInfo ci, FogType fogType, Entity entity, FogRenderer.FogData fogData) {
        if (SodiumGameOptions.defaults().quality.useFog) {
            fogData.start = FOG_START;
            fogData.end = FOG_END;
            fogData.shape = FogShape.SPHERE;
        }
    }
}
