package ru.blatfan.blatiumspellbooks.events;

import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.blatfan.blatiumspellbooks.item.ModArmorMaterial;
import ru.blatfan.blatiumspellbooks.item.WizardArmorItem;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
public class ArmorEvents {
	


	@SubscribeEvent
	public static void onPlayerFall(LivingFallEvent event) {
		Item boots = event.getEntity().getItemBySlot(EquipmentSlot.FEET).getItem();
		
		if(boots instanceof WizardArmorItem)
			event.setCanceled(true);
	}



	@SubscribeEvent
	public static void onEntityHurt(LivingAttackEvent event) {
		if (!event.getEntity().getCommandSenderWorld().isClientSide) {
			Item helmet = event.getEntity().getItemBySlot(EquipmentSlot.HEAD).getItem();
			Item chestplate = event.getEntity().getItemBySlot(EquipmentSlot.CHEST).getItem();
			Item leggings = event.getEntity().getItemBySlot(EquipmentSlot.LEGS).getItem();
			Item boots = event.getEntity().getItemBySlot(EquipmentSlot.FEET).getItem();
			
			if (helmet instanceof WizardArmorItem) {
				if (event.getSource().is(DamageTypes.FLY_INTO_WALL)) {
					event.setCanceled(true);
				}
				if (event.getSource().is(DamageTypes.DROWN)) {
					event.getEntity().setAirSupply(event.getEntity().getMaxAirSupply());
					event.setCanceled(true);
				}
			}
			if (chestplate instanceof WizardArmorItem) {
				if ((event.getSource().is(DamageTypes.IN_FIRE)) || (event.getSource().is(DamageTypes.ON_FIRE)) || (event.getSource().is(DamageTypes.LAVA)) || (event.getSource().is(DamageTypes.HOT_FLOOR))) {
					event.getEntity().clearFire();
					event.setCanceled(true);
				}
			}
			if (leggings instanceof WizardArmorItem) {
				if (event.getSource().is(DamageTypes.EXPLOSION)) {
					event.setCanceled(true);
				}
			}
			if(event.isCanceled()) return;
			if(helmet instanceof WizardArmorItem h &&
				chestplate instanceof WizardArmorItem c &&
				leggings instanceof WizardArmorItem l &&
				boots instanceof WizardArmorItem b){
				if(h.getMaterial()== ModArmorMaterial.BLATIUM &&
					c.getMaterial()==ModArmorMaterial.BLATIUM &&
					l.getMaterial()==ModArmorMaterial.BLATIUM &&
					b.getMaterial()==ModArmorMaterial.BLATIUM) {
					if (Math.random()>0.5)
						event.setCanceled(true);
				}
				if(h.getMaterial()==ModArmorMaterial.NLIUM &&
					c.getMaterial()==ModArmorMaterial.NLIUM &&
					l.getMaterial()==ModArmorMaterial.NLIUM &&
					b.getMaterial()==ModArmorMaterial.NLIUM) {
					if (Math.random()>0.25)
						event.setCanceled(true);
				}
			}
		}

	}
}

