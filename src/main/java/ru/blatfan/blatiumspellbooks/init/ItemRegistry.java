package ru.blatfan.blatiumspellbooks.init;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.blatfan.blatiumspellbooks.BlatiumSpellbooks;
import ru.blatfan.blatiumspellbooks.item.CooldownCastTimeSpellBook;
import ru.blatfan.blatiumspellbooks.item.ModArmorMaterial;
import ru.blatfan.blatiumspellbooks.item.WizardArmorItem;

import java.util.Collection;
import java.util.function.Supplier;

public class ItemRegistry {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BlatiumSpellbooks.MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static final RegistryObject<Item> BLATIUM_MAGE_HELMET = generateItem("blatium_mage_helmet", () -> new WizardArmorItem(ModArmorMaterial.BLATIUM, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> BLATIUM_MAGE_CHESTPLATE = generateItem("blatium_mage_chestplate", () -> new WizardArmorItem(ModArmorMaterial.BLATIUM, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> BLATIUM_MAGE_LEGGINGS = generateItem("blatium_mage_leggings", () -> new WizardArmorItem(ModArmorMaterial.BLATIUM, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> BLATIUM_MAGE_BOOTS = generateItem("blatium_mage_boots", () -> new WizardArmorItem(ModArmorMaterial.BLATIUM, ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> NLIUM_MAGE_HELMET = generateItem("nlium_mage_helmet", () -> new WizardArmorItem(ModArmorMaterial.NLIUM, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> NLIUM_MAGE_CHESTPLATE = generateItem("nlium_mage_chestplate", () -> new WizardArmorItem(ModArmorMaterial.NLIUM, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> NLIUM_MAGE_LEGGINGS = generateItem("nlium_mage_leggings", () -> new WizardArmorItem(ModArmorMaterial.NLIUM, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> NLIUM_MAGE_BOOTS = generateItem("nlium_mage_boots", () -> new WizardArmorItem(ModArmorMaterial.NLIUM, ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> BLATIUM_SPELLBOOK = generateSpellBook("blatium_spell_book", () -> new CooldownCastTimeSpellBook(13, .3, .15));
    public static final RegistryObject<Item> NLIUM_SPELLBOOK = generateSpellBook("nlium_spell_book", () -> new CooldownCastTimeSpellBook(15, .5, .35));

    private static RegistryObject<Item> generateItem(String name, Supplier<Item> supplier) {
        return ITEMS.register(name, supplier);
    }

    private static RegistryObject<Item> generateSpellBook(String name, Supplier<Item> supplier) {
        return ITEMS.register(name, supplier);
    }

    public static Collection<RegistryObject<Item>> items() {
        return ITEMS.getEntries();
    }
}
