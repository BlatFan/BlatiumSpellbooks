package ru.blatfan.blatiumspellbooks;

import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.render.SpellBookCurioRenderer;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import ru.blatfan.blatiumspellbooks.init.CreativeTabRegistry;
import ru.blatfan.blatiumspellbooks.init.ItemRegistry;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.awt.*;

@Mod(BlatiumSpellbooks.MODID)
public class BlatiumSpellbooks {
    public static final String MODID = "blatium_spellbooks";
    public static int COLOR_BLATIUM = getColor(new Color(156, 39, 176));
    public static int COLOR_NLIUM = getColor(new Color(63, 81, 181));
    public static Rarity RARITY_BLATIUM = Rarity.create("blatium", style -> style.withColor(COLOR_BLATIUM));
    public static Rarity RARITY_NLIUM = Rarity.create("nlium", style -> style.withColor(COLOR_NLIUM));
    
    private static int getColor(Color color) {
        return FastColor.ARGB32.color(color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue());
    }
    
    public BlatiumSpellbooks() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ServerConfig.SPEC, "blatfan/blatium_spellbooks-common.toml");
        
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        CreativeTabRegistry.register(modEventBus);
        ItemRegistry.register(modEventBus);
        
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ItemRegistry.items().stream().filter(item -> item.get() instanceof SpellBook).forEach((item) -> CuriosRendererRegistry.register(item.get(), SpellBookCurioRenderer::new));
            });
        }
    }
}