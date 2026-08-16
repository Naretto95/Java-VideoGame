package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
/**
 * 
 * @author Lilian Naretto
 *
 */
public class Enemy extends Entity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Category Category;
	private Race Race;
	private int experienceReward;
	
	public Enemy( int level, int positionX, int positionY,Category category, Race race) {
		super(level, positionX,positionY);
		this.setExperienceReward(level*10);
		this.setCategory(category);
		this.setRace(race);
		if (this.getCategory()==game.Category.BOSS) {
			this.setHealth(this.getHealth()*2);
			this.getResourceInventory().put(new Resource(ResourceType.KEY),1);
		}
		switch (this.getRace()) {
		case ORC:
			this.getWeaponInventory().add(new Weapon(WeaponType.LONGSWORD,this.getLevel()));
			this.switchItem(new Weapon(WeaponType.LONGSWORD,this.getLevel()));
			this.getPotionInventory().add(new Potion(Effect.POISON,this.getLevel()));
			this.getResourceInventory().put(new Resource(ResourceType.IRON),1);
			this.getResourceInventory().put(new Resource(ResourceType.GOLD),1);
			break;
			
		case HUMAN:
			this.getWeaponInventory().add(new Weapon(WeaponType.SHORTSWORD,this.getLevel()));
			this.switchItem(new Weapon(WeaponType.SHORTSWORD,this.getLevel()));
			this.getResourceInventory().put(new Resource(ResourceType.WOOD),2);
			this.getPotionInventory().add(new Potion(Effect.HEAL,this.getLevel()));
			break;
			
		case DRAGON:
			this.getWeaponInventory().add(new Weapon(WeaponType.LONGSWORD,this.getLevel()));
			this.switchItem(new Weapon(WeaponType.LONGSWORD,this.getLevel()));
			this.getPotionInventory().add(new Potion(Effect.DAMAGE_BOOST,this.getLevel()));
			this.getResourceInventory().put(new Resource(ResourceType.GOLD),2);
			
			break;
			
		case DWARF:
			this.getWeaponInventory().add(new Weapon(WeaponType.BOW,this.getLevel()));
			this.switchItem(new Weapon(WeaponType.BOW,this.getLevel()));
			this.getPotionInventory().add(new Potion(Effect.STUN,this.getLevel()));
			this.getResourceInventory().put(new Resource(ResourceType.IRON),2);
			
			break;

		default:
			break;
		}
	}
	
	public List<GameObject> dropLoot() {
		List<GameObject> Liste = new ArrayList<>();
		for (int i = 0; i < this.getWeaponInventory().size(); i++) {
			if (this.getWeaponInventory().get(i).getType()!=WeaponType.UNARMED) {
				this.getWeaponInventory().get(i).setPickedUp(false);
				this.getWeaponInventory().get(i).repair(this);
				Liste.add(this.getWeaponInventory().get(i));
				this.getWeaponInventory().remove(i);
			}
		}
		for (int i = 0; i < this.getPotionInventory().size(); i++) {
			this.getPotionInventory().get(i).setPickedUp(false);
			Liste.add(this.getPotionInventory().get(i));
			this.getPotionInventory().remove(i);
		}
		for(Entry<Resource, Integer> entry : this.getResourceInventory().entrySet()) {
			Resource resourceKey = entry.getKey();
			Integer valeur = entry.getValue();
			if (valeur >0) {
				resourceKey.setPickedUp(false);
				while (valeur>0) {
					Liste.add(resourceKey);
					this.getResourceInventory().put(resourceKey,this.getResourceInventory().get(resourceKey)-1);
					valeur=valeur-1;
				}
			}
			
		}
		return Liste;
	}
	
	public Category getCategory() {
		return Category;
	}
	
	public void setCategory(Category category) {
		Category = category;
	}
	
	public Race getRace() {
		return Race;
	}
	
	public void setRace(Race race) {
		Race = race;
	}

	public int getExperienceReward() {
		return experienceReward;
	}

	public void setExperienceReward(int experienceReward) {
		this.experienceReward = experienceReward;
	}
	
}
