package io.github.metaconscious.mod.lvb.mixin;

import io.github.metaconscious.mod.lvb.LegacyVisibleBarrierMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BarrierBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.render.block.BlockLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BarrierBlock.class)
public class BarrierBlockMixin extends Block {

    protected BarrierBlockMixin(Material material) {
        super(material);
    }

    /**
     * @author Mark Oven
     * @reason Change render type to 3
     */
    @Overwrite
    @Override
    public int getRenderType() {
        return LegacyVisibleBarrierMod.getInstance().getKeyBindings().getVisibilityViewer().isVisible()
                ? 3
                : -1;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public BlockLayer getRenderLayer() {
        return BlockLayer.TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean shouldRenderFace(@NotNull WorldView view, BlockPos pos, Direction facing) {
        return view.getBlockState(pos).getBlock() != this;
    }
}
