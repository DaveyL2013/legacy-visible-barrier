package io.github.metaconscious.mod.lvb.mixin;

import io.github.metaconscious.mod.lvb.LegacyVisibleBarrierMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.particle.BarrierParticle;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.vertex.BufferBuilder;

@Environment(EnvType.CLIENT)
@Mixin(BarrierParticle.class)
public class BarrierParticleMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(BufferBuilder builder, Entity entity, float tickDelta, float g, float h, float i, float j, float k, @NotNull CallbackInfo ci) {
        if (LegacyVisibleBarrierMod.getInstance().getKeyBindings().getVisibilityViewer().isVisible()) {
            ci.cancel();
        }
    }

}
