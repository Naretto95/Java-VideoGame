package controller;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import game.Weapon;
import game.Effect;
import game.Entity;
import game.Item;
import game.Potion;
import game.WeaponType;
import display.InventoryBarDisplay;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
/**
 * @date 23/05/2020
 * @author Corentin BRILLANT
 */


public class InventoryBarController extends FlowPane implements Observer{

	
	InventoryBarDisplay inventoryDisplay;
	Entity entity;
	
	public InventoryBarController(Entity entity) {
		this.entity = entity;
		this.entity.addObserver(this);
		this.inventoryDisplay = new InventoryBarDisplay();
		this.refreshDisplay();
		this.inventoryDisplay.getBowSlot().setOnMouseClicked((e)->{this.entity.switchItem(new Weapon(WeaponType.BOW,1));});
		this.inventoryDisplay.getShortswordSlot().setOnMouseClicked((e)->{this.entity.switchItem(new Weapon(WeaponType.SHORTSWORD,1));});
		this.inventoryDisplay.getLongswordSlot().setOnMouseClicked((e)->{this.entity.switchItem(new Weapon(WeaponType.LONGSWORD,1));});
		this.inventoryDisplay.getDamagePotionSlot().setOnMouseClicked((e)->{this.entity.switchItem(new Potion(Effect.DAMAGE_BOOST,1));});
		this.inventoryDisplay.getStunPotionSlot().setOnMouseClicked((e)->{this.entity.switchItem(new Potion(Effect.STUN,1));});
		this.inventoryDisplay.getPoisonPotionSlot().setOnMouseClicked((e)->{this.entity.switchItem(new Potion(Effect.POISON,1));});
		this.inventoryDisplay.getHealPotionSlot().setOnMouseClicked((e)->{this.entity.switchItem(new Potion(Effect.HEAL,1));});
		this.inventoryDisplay.getEquippedSlot().setOnMouseClicked((e)->{this.entity.switchItem(new Weapon(WeaponType.UNARMED,1));});
		this.getChildren().add(inventoryDisplay);
		this.setAlignment(Pos.BOTTOM_RIGHT);
		FlowPane.setMargin(inventoryDisplay, new Insets(0, 10, 10, 0));
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Integer && ((Integer)arg1).intValue()==(Entity.ITEM_CHANGED).intValue()) {
			refreshDisplay();
		}
	}

	/**{@literal refreshes the inventory bar display to match the entity's current inventory}*/
	public void refreshDisplay() {
		this.inventoryDisplay.clearSlots();
		Item itemInHand = this.entity.getItemInHand();
		if (itemInHand instanceof Weapon) {
			switch(((Weapon) itemInHand).getType()) {
			case LONGSWORD:
				this.inventoryDisplay.setEquippedSlot(InventoryBarDisplay.longswordImage);
				break;
			case SHORTSWORD:
				this.inventoryDisplay.setEquippedSlot(InventoryBarDisplay.shortswordImage);
				break;
			case BOW:
				this.inventoryDisplay.setEquippedSlot(InventoryBarDisplay.arc);
				break;
			default:
				this.inventoryDisplay.setEquippedSlot(InventoryBarDisplay.unarmedImage);
					break;
			}
		}
		else if (itemInHand instanceof Potion) {
			switch(((Potion)itemInHand).getEffect()) {
			case STUN:
				this.inventoryDisplay.setEquippedSlot(InventoryBarDisplay.stunPotionImage);
				break;
			case POISON:
				this.inventoryDisplay.setEquippedSlot(InventoryBarDisplay.poisonPotionImage);
				break;
			case HEAL:
				this.inventoryDisplay.setEquippedSlot(InventoryBarDisplay.healPotionImage);
				break;
			case DAMAGE_BOOST:
				this.inventoryDisplay.setEquippedSlot(InventoryBarDisplay.damagePotionImage);
				break;
				default:
					this.inventoryDisplay.setEquippedSlot(InventoryBarDisplay.unarmedImage);
					break;
			}
		}
		else {
			this.inventoryDisplay.setEquippedSlot(InventoryBarDisplay.unarmedImage);
		}
		List<Weapon> weapons = this.entity.getWeaponInventory();
		List<Potion> potions = this.entity.getPotionInventory();
		for (int i = 0;i<weapons.size();i++) {
			switch(weapons.get(i).getType()) {
			case LONGSWORD:
				this.inventoryDisplay.setLongswordSlot();
				break;
			case SHORTSWORD:
				this.inventoryDisplay.setShortswordSlot();
				break;
			case BOW:
				this.inventoryDisplay.setBowSlot();
				break;
			default:
					break;
			}
		}
		for (int j = 0;j<potions.size();j++) {
			switch(potions.get(j).getEffect()) {
			case STUN:
				this.inventoryDisplay.setStunPotionSlot();
				break;
			case POISON:
				this.inventoryDisplay.setPoisonPotionSlot();
				break;
			case HEAL:
				this.inventoryDisplay.setHealPotionSlot();
				break;
			case DAMAGE_BOOST:
				this.inventoryDisplay.setDamagePotionSlot();
				break;
				default:
					break;
			}
		}
		
	}
}
