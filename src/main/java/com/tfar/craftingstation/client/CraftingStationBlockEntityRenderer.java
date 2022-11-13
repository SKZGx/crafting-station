package com.tfar.craftingstation.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tfar.craftingstation.CraftingStationBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.SlabType;
import com.mojang.math.Vector3f;

public class CraftingStationBlockEntityRenderer extends BlockEntityRenderer<CraftingStationBlockEntity> {

  public CraftingStationBlockEntityRenderer(BlockEntityRenderDispatcher p_i226006_1_) {
    super(p_i226006_1_);
  }

  @Override
  public void render(CraftingStationBlockEntity blockEntity, float var2, PoseStack matrixStack, MultiBufferSource iRenderTypeBuffer, int light, int var6) {
    if (this.renderer.camera != null) {

      if (blockEntity.input.isEmpty())return;

      BlockState state = blockEntity.getBlockState();

      double height = state.hasProperty(SlabBlock.TYPE) ? state.getValue(SlabBlock.TYPE) == SlabType.BOTTOM ?.5:1:1;

      final double spacing = .189;
      final double offset = .31;
      //translate x,y,z
      matrixStack.translate(0,.0625 + height, 0);
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          ItemStack item = blockEntity.input.getStackInSlot(j + 3 * i);
          if (item.isEmpty()) continue;

          //pushmatrix
          matrixStack.pushPose();
          //translate x,y,z
          matrixStack.translate(spacing * i +offset, 0, spacing * j +offset);
          matrixStack.mulPose(Vector3f.YP.rotation(0));
          //scale x,y,z
          matrixStack.scale(0.25F, 0.25F, 0.25F);

          int lightAbove = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().above());
          Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemTransforms.TransformType.FIXED,
                  lightAbove, OverlayTexture.NO_OVERLAY, matrixStack, iRenderTypeBuffer);
          //popmatrix
          matrixStack.popPose();
        }
      }
    }
  }
}
