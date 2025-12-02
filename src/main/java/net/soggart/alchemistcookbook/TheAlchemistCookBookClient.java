package net.soggart.alchemistcookbook;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.soggart.alchemistcookbook.screen.ModScreenHandler;
import net.soggart.alchemistcookbook.screen.SeparatorScreen;

public class TheAlchemistCookBookClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandler.SEPARATOR_SCREEN_HANDLER, SeparatorScreen::new);
    }
}
