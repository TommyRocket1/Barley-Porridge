package com.tommyrocket.barleyporridge;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(BarleyPorridge.MOD_ID)
public final class BarleyPorridge {
    public static final String MOD_ID = "barleyporridge";

    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    // usingConvertsTo gives the vanilla stew behavior: an empty bowl handed back
    // once the porridge is finished (the eat animation comes with any food item).
    private static final FoodProperties BARLEY_PORRIDGE_FOOD = new FoodProperties.Builder()
            .nutrition(6)
            .saturationModifier(0.6F)
            .usingConvertsTo(Items.BOWL)
            .build();

    // craftRemainder doubles as Farmer's Delight's serving container: the cooking
    // pot hands out portions of this meal in exchange for an empty bowl.
    public static final DeferredItem<Item> BARLEY_PORRIDGE = ITEMS.registerSimpleItem(
            "barley_porridge",
            new Item.Properties()
                    .stacksTo(1)
                    .craftRemainder(Items.BOWL)
                    .food(BARLEY_PORRIDGE_FOOD));

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
