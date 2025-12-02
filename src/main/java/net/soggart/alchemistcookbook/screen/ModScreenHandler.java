package net.soggart.alchemistcookbook.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.soggart.alchemistcookbook.TheAlchemistCookBook;

public class ModScreenHandler {
    public static final ScreenHandlerType<SeparatorScreenHandler> SEPARATOR_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(TheAlchemistCookBook.MOD_ID, "potion_separating"),
                    new ExtendedScreenHandlerType<>(SeparatorScreenHandler::new));

    public static void registerScreenHandlers(){
        TheAlchemistCookBook.LOGGER.info("Registering Screen Handlers for "+TheAlchemistCookBook.MOD_ID);
    }
}
