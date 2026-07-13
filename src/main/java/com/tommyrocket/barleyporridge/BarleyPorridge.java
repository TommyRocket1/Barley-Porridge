package com.tommyrocket.barleyporridge;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(BarleyPorridge.MOD_ID)
public final class BarleyPorridge {
    public static final String MOD_ID = "barleyporridge";

    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    private static final FoodProperties BARLEY_PORRIDGE_FOOD = new FoodProperties.Builder()
            .nutrition(6)
            .saturationModifier(0.6F)
            .build();

    // BowlFoodItem gives the vanilla stew behavior: the eat animation while consuming,
    // and an empty bowl handed back once the porridge is finished.
    public static final DeferredItem<BowlFoodItem> BARLEY_PORRIDGE = ITEMS.registerItem(
            "barley_porridge",
            properties -> new BowlFoodItem(properties
                    .stacksTo(1)
                    .food(BARLEY_PORRIDGE_FOOD)));

    public BarleyPorridge(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(BARLEY_PORRIDGE);
        }
    }
}
