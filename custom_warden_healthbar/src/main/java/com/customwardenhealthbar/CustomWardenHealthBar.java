package com.example.customwardenhealthbar;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.util.Identifier;

public class CustomWardenHealthBar implements ModInitializer {
    private static final Identifier CUSTOM_HEALTH_BAR_TEXTURE = new Identifier("custom_warden_healthbar", "textures/gui/warden_health_bar.png");

    @Override
    public void onInitialize() {
        // Register HUD render callback to draw the custom health bar
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null && client.player.getAttacking() instanceof WardenEntity) {
                drawCustomHealthBar(matrixStack);
            }
        });
    }

    // Method to draw the custom health bar
    private void drawCustomHealthBar(MatrixStack matrices) {
        MinecraftClient client = MinecraftClient.getInstance();
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();
        int barWidth = 182;
        int barHeight = 5;
        int x = (screenWidth - barWidth) / 2;
        int y = screenHeight - 39;

        client.getTextureManager().bindTexture(CUSTOM_HEALTH_BAR_TEXTURE);
        drawTexture(matrices, x, y, 0, 0, barWidth, barHeight, 256, 256);

        int healthBarWidth = (int) (client.player.getHealth() / client.player.getMaxHealth() * barWidth);
        drawTexture(matrices, x, y, 0, barHeight, healthBarWidth, barHeight, 256, 256);
    }
}
