package io.github.metaconscious.mod.lvb.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.model.BlockModels;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Environment(EnvType.CLIENT)
@Mixin(BlockModels.class)
public class BlockModelsMixin {

    @Shadow
    private Set<Block> custom;

    @Inject(method = "register([Lnet/minecraft/block/Block;)V", at = @At("TAIL"))
    private void register(Block[] blocks, CallbackInfo ci) {
        this.custom.remove(Blocks.BARRIER);
    }

}
