package ru.blatfan.blatiumspellbooks.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.api.spells.IPresetSpellContainer;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.blatfan.blatiumspellbooks.BlatiumSpellbooks;
import ru.blatfan.blatiumspellbooks.ServerConfig;
import ru.blatfan.blatiumspellbooks.client.armor.GenericArmorModel;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class WizardArmorItem extends ArmorItem implements GeoItem, IPresetSpellContainer {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final ModArmorMaterial material;

    public WizardArmorItem(ModArmorMaterial material, Type type) {
        this(material, type, material== ModArmorMaterial.BLATIUM ? BlatiumSpellbooks.RARITY_BLATIUM : BlatiumSpellbooks.RARITY_NLIUM);
    }

    public WizardArmorItem(ModArmorMaterial material, Type type, Rarity rarity) {
        super(material, type, new Properties().stacksTo(1).fireResistant().rarity(rarity));
        this.material = material;
    }
    
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltips, TooltipFlag tooltipFlag) {
        tooltips.add(Component.empty());
        tooltips.add(Component.translatable("tooltip.blatium.unbreakable").withStyle(style -> style.withColor(material== ModArmorMaterial.BLATIUM ? BlatiumSpellbooks.COLOR_BLATIUM : BlatiumSpellbooks.COLOR_NLIUM)));
        if(ServerConfig.FULL_SET.get())tooltips.add(Component.translatable("tooltip.blatium.full_set."+material.getName())
            .withStyle(style -> style.withColor(material== ModArmorMaterial.BLATIUM ? BlatiumSpellbooks.COLOR_BLATIUM : BlatiumSpellbooks.COLOR_NLIUM)));
        
        if(getType()==Type.HELMET && !ServerConfig.HELMET.get()) return;
        if(getType()==Type.CHESTPLATE && !ServerConfig.CHESTPLATE.get()) return;
        if(getType()==Type.LEGGINGS && !ServerConfig.LEGGINGS.get()) return;
        if(getType()==Type.BOOTS && !ServerConfig.BOOTS.get()) return;
        
        tooltips.add(Component.empty());
        tooltips.add(Component.translatable("tooltip.blatium."+getType().toString().toLowerCase()).withStyle(style -> style.withColor(material== ModArmorMaterial.BLATIUM ? BlatiumSpellbooks.COLOR_BLATIUM : BlatiumSpellbooks.COLOR_NLIUM)));
        
        super.appendHoverText(itemStack, level, tooltips, tooltipFlag);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canBeDepleted() {
        return false;
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return true;
    }

    @Override
    public ModArmorMaterial getMaterial() {
        return this.material;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        if (pEquipmentSlot == this.type.getSlot()) {
            return this.material.getSlotToAttributeMap().get(pEquipmentSlot);
        } else {
            return ImmutableMultimap.of();
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<WizardArmorItem>(this, "controller", 20, this::predicate));
    }

    private PlayState predicate(AnimationState<WizardArmorItem> extendedArmorItemAnimationState) {
        extendedArmorItemAnimationState.getController().setAnimation(RawAnimation.begin().thenLoop("idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new GeoArmorRenderer<>(new GenericArmorModel<>());
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }


    @Override
    public void initializeSpellContainer(ItemStack itemStack) {
        if (itemStack == null) {
            return;
        }

        if (itemStack.getItem() instanceof ArmorItem armorItem && armorItem.getType() == Type.CHESTPLATE) {
            if (!ISpellContainer.isSpellContainer(itemStack)) {
                var spellContainer = ISpellContainer.create(1, true, true);
                spellContainer.save(itemStack);
            }
        }
    }
}