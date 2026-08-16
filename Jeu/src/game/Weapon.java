package game;

import java.io.Serializable;
/**
 * 
 * @author Lilian Naretto
 *
 */

public class Weapon extends Item implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int durability;
	private WeaponType Type;
	private boolean damageBoosted;
	private int damage;
	private int range;
	
	public Weapon(WeaponType type, int level ){
		super(true,level,false);
		this.setDamageBoosted(false);
		switch (type) {
		case LONGSWORD:
			this.setDamage(level * 5);
			this.setDurability(20);
			this.setType(type);
			this.setRange(2);
			break;
			
		case SHORTSWORD:
			this.setDamage(level * 3);
			this.setDurability(10);
			this.setType(type);
			this.setRange(1);
			break;
		
		case BOW:
			this.setDamage(level * 2);
			this.setDurability(15);
			this.setType(type);
			this.setRange(10);
			break;
		
		case UNARMED:
			this.setPickedUp(true);
			this.setDamage(level*1);
			this.setDurability(9999999);
			this.setType(type);
			this.setRange(1);
			break;
			
		default:
			break;
		}
		
	}
	
	public void upgrade(Player player) {
		((Weapon)player.getItemInHand()).setDamage(player.getLevel()*3);
		switch (((Weapon)player.getItemInHand()).getType()) {
		case LONGSWORD:
			((Weapon)player.getItemInHand()).setDamage(player.getLevel()*5);
			((Weapon)player.getItemInHand()).setLevel(player.getLevel());
			break;
			
		case SHORTSWORD:
			((Weapon)player.getItemInHand()).setDamage(player.getLevel()*3);
			((Weapon)player.getItemInHand()).setLevel(player.getLevel());
			break;
		
		case BOW:
			((Weapon)player.getItemInHand()).setDamage(player.getLevel()*2);
			((Weapon)player.getItemInHand()).setLevel(player.getLevel());
			break;
			
		default:
			break;
		}
	}
	
	public void repair(Entity entity) {
		switch (((Weapon)entity.getItemInHand()).getType()) {
		case LONGSWORD:
			((Weapon)entity.getItemInHand()).setDurability(20);
			break;

		case SHORTSWORD:
			((Weapon)entity.getItemInHand()).setDurability(10);
			break;

		case BOW:
			((Weapon)entity.getItemInHand()).setDurability(15);
			break;
			
		default:
			break;
		}
	}
	
	public int getDurability() {
		return durability;
	}
	
	public void setDurability(int durability) {
		this.durability = durability;
	}
	
	public WeaponType getType() {
		return Type;
	}
	
	public void setType(WeaponType type) {
		Type = type;
	}
	
	public int getRange() {
		return range;
	}
	
	public void setRange(int range) {
		this.range = range;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public String toString() {
		return "Type:"+this.getType()+" level:"+this.getLevel() +" durability:"+this.getDurability();
	}

	public boolean isDamageBoosted() {
		return damageBoosted;
	}

	public void setDamageBoosted(boolean damageBoosted) {
		this.damageBoosted = damageBoosted;
	}
	

}
