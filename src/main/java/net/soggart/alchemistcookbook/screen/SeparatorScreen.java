package net.soggart.alchemistcookbook.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.soggart.alchemistcookbook.TheAlchemistCookBook;

public class SeparatorScreen extends HandledScreen<SeparatorScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(TheAlchemistCookBook.MOD_ID, "textures/gui/separator_gui.png");

    public SeparatorScreen(SeparatorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleY -= 2;
        titleX -= 2;
        playerInventoryTitleY = 10000;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = ((height - backgroundHeight) / 2);

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight+9);

        renderProgressArrows(context, x, y);
    }

    private void renderProgressArrows(DrawContext context, int x, int y) {
        if(handler.isCrafting()){
            context.drawTexture(TEXTURE, x+78, y+24, 176, 0, handler.getScaledProgress(), 8);
            context.drawTexture(TEXTURE, x+98, y+24, 176, 8, -handler.getScaledProgress(), 8);
        }
    }

    private void renderTank1(DrawContext context, int x, int y) {
        if(handler.isCrafting()){
            context.drawTexture(TEXTURE, x+27, y+16, 176, 16, 16, handler.getScaledCapacity1());
        }
    }

    private void renderTank2(DrawContext context, int x, int y) {
        if(handler.isCrafting()){
            context.drawTexture(TEXTURE, x+135, y+16, 176, 88, 16, handler.getScaledCapacity2());
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
