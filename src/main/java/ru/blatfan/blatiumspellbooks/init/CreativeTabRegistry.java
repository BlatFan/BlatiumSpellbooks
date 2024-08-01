package ru.blatfan.blatiumspellbooks.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import ru.blatfan.blatiumspellbooks.BlatiumSpellbooks;

public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BlatiumSpellbooks.MODID);

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("blatiumspellbooks_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .title(Component.literal("Blatium Spellbooks"))
            .icon(() -> ItemRegistry.NLIUM_MAGE_CHESTPLATE.get().getDefaultInstance())
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .displayItems((parameters, output) -> {
                output.accept(ItemRegistry.BLATIUM_MAGE_HELMET.get());
                output.accept(ItemRegistry.BLATIUM_MAGE_CHESTPLATE.get());
                output.accept(ItemRegistry.BLATIUM_MAGE_LEGGINGS.get());
                output.accept(ItemRegistry.BLATIUM_MAGE_BOOTS.get());
                output.accept(ItemRegistry.BLATIUM_SPELLBOOK.get());

                output.accept(ItemRegistry.NLIUM_MAGE_HELMET.get());
                output.accept(ItemRegistry.NLIUM_MAGE_CHESTPLATE.get());
                output.accept(ItemRegistry.NLIUM_MAGE_LEGGINGS.get());
                output.accept(ItemRegistry.NLIUM_MAGE_BOOTS.get());
                output.accept(ItemRegistry.NLIUM_SPELLBOOK.get());
            }).build());
}
