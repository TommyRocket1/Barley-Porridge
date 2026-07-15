package com.tommyrocket.barleyporridge;

import java.util.List;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(BarleyPorridge.MOD_ID)
public final class BarleyPorridge {
    public static final String MOD_ID = "barleyporridge";

    private static final ResourceLocation NOURISHMENT_ID =
            ResourceLocation.fromNamespaceAndPath("farmersdelight", "nourishment");

    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    // craftRemainder doubles as Farmer's Delight's serving container: the cooking
    // pot hands out portions of this meal in exchange for an empty bowl.
    public static final DeferredItem<Item> BARLEY_PORRIDGE = ITEMS.registerItem(
            "barley_porridge",
            properties -> new Item(properties
                    .stacksTo(1)
                    .craftRemainder(Items.BOWL)
                    .food(porridgeFood())));

    // Runs at item-registration time — mob effects register before items, so the
    // Farmer's Delight lookup sees the real registry state.
    private static FoodProperties porridgeFood() {
        FoodProperties.Builder builder = new FoodProperties.Builder()
                .nutrition(6)
                .saturationModifier(0.6F)
                .usingConvertsTo(Items.BOWL);
        // Soft dependency: when Farmer's Delight is installed, porridge nourishes.
        BuiltInRegistries.MOB_EFFECT.getOptional(NOURISHMENT_ID).ifPresent(nourishment ->
                builder.effect(new MobEffectInstance(
                        BuiltInRegistries.MOB_EFFECT.wrapAsHolder(nourishment), 1200), 1.0F));
        return builder.build();
    }

    public BarleyPorridge(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        modEventBus.addListener(this::addCreative);
        NeoForge.EVENT_BUS.addListener(BarleyPorridge::onItemTooltip);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(BARLEY_PORRIDGE);
        }
    }

    // Mirrors Farmer's Delight's food-effect tooltip, so the porridge reads
    // "Nourishment (01:00)" under its name and JEI's tooltip search can find it.
    private static void onItemTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() != BARLEY_PORRIDGE.get()) {
            return;
        }
        FoodProperties food = event.getItemStack().get(DataComponents.FOOD);
        if (food == null || food.effects().isEmpty()) {
            return;
        }
        List<Component> tooltip = event.getToolTip();
        int line = Math.min(1, tooltip.size());
        for (FoodProperties.PossibleEffect possibleEffect : food.effects()) {
            MobEffectInstance effect = possibleEffect.effect();
            MutableComponent text = Component.translatable(effect.getDescriptionId());
            if (effect.getDuration() > 20) {
                text = Component.translatable("potion.withDuration", text,
                        MobEffectUtil.formatDuration(effect, 1.0F, event.getContext().tickRate()));
            }
            tooltip.add(line++, text.withStyle(
                    effect.getEffect().value().getCategory().getTooltipFormatting()));
        }
    }
}
