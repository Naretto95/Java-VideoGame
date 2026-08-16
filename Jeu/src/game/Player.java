package game;

import java.util.Map.Entry;
/**
 * 
 * @author Lilian Naretto
 *
 */
public class Player extends Entity {
	
	/**
	 * 
	 */
	public static Integer EXPERIENCE_GAINED = new Integer(737);
	private static final long serialVersionUID = 1L;
	private int experience;
	private String name;
	
	public Player(String name, int level, int positionX, int positionY) {
		super(level, positionX,positionY);
		this.setItemInHand(new Weapon(WeaponType.UNARMED,level));
		this.setName(name);
		this.setExperience(0);
	}
	
	public void levelUp() {
		if (this.getExperience()>this.getLevel()*100) {
			this.setExperience(this.getExperience()-this.getLevel()*100);
			this.setLevel(this.getLevel()+1);
			this.getWeaponInventory().get(0).setDamage(this.getLevel());
			this.getWeaponInventory().get(0).setLevel(this.getLevel());
		}
		this.setChanged();
		this.notifyObservers(EXPERIENCE_GAINED);
	}
	
	public void upgrade() {
		if (this.getItemInHand() instanceof Weapon) {
			upgradeWeapon((Weapon)this.getItemInHand());
		}
		this.refreshInventory();
	}
	
	public void upgradeWeapon(Weapon weapon) {
		for(Entry<Resource, Integer> entry : this.getResourceInventory().entrySet()) {
		    Resource resourceKey = entry.getKey();
		    Integer valeur = entry.getValue();
		    switch (weapon.getType()) {
			case LONGSWORD:
				if (resourceKey.getType()==ResourceType.IRON) {
					if (valeur>=5) {
						weapon.upgrade(this);
						this.getResourceInventory().put(resourceKey,this.getResourceInventory().get(resourceKey)-5);
					}			
				}
				break;
				
			case SHORTSWORD:
				if (resourceKey.getType()==ResourceType.IRON) {
					if (valeur>=3) {
						weapon.upgrade(this);
						this.getResourceInventory().put(resourceKey,this.getResourceInventory().get(resourceKey)-3);
					}			
				}
				break;
				
			case BOW:
				if (resourceKey.getType()==ResourceType.WOOD) {
					if (valeur>=3) {
						weapon.upgrade(this);
						this.getResourceInventory().put(resourceKey,this.getResourceInventory().get(resourceKey)-3);
					}			
				}
				break;

			default:
				break;
			}
		}
		this.setChanged();
		this.notifyObservers(Entity.RESOURCES_CHANGED);
	}
	
	public void repair() {
		if (this.getItemInHand() instanceof Weapon) {
			repairWeapon((Weapon)this.getItemInHand());
		}
		this.refreshInventory();
	}
	
	public void repairWeapon(Weapon weapon) {
		for(Entry<Resource, Integer> entry : this.getResourceInventory().entrySet()) {
		    Resource resourceKey = entry.getKey();
		    Integer valeur = entry.getValue();
		    switch (weapon.getType()) {
			case LONGSWORD:
				if (resourceKey.getType()==ResourceType.IRON) {
					if (valeur>=2) {
						weapon.repair(this);
						this.getResourceInventory().put(resourceKey,this.getResourceInventory().get(resourceKey)-2);
					}			
				}
				break;
				
			case SHORTSWORD:
				if (resourceKey.getType()==ResourceType.IRON) {
					if (valeur>=1) {
						weapon.repair(this);
						this.getResourceInventory().put(resourceKey,this.getResourceInventory().get(resourceKey)-1);
					}			
				}
				break;
				
			case BOW:
				if (resourceKey.getType()==ResourceType.WOOD) {
					if (valeur>=1) {
						weapon.repair(this);
						this.getResourceInventory().put(resourceKey,this.getResourceInventory().get(resourceKey)-1);
					}			
				}
				break;

			default:
				break;
			}
		}
	}
	
	public Item dropLoot() {
		if (this.getItemInHand() instanceof Weapon) {
			if (((Weapon)this.getItemInHand()).getType()!=WeaponType.UNARMED) {
				for (int i = 0; i < this.getWeaponInventory().size(); i++) {
					if (((Weapon)this.getItemInHand()).getType()==this.getWeaponInventory().get(i).getType()) {
							for (int j = i+1; j < this.getWeaponInventory().size(); j++) {
								if (this.getWeaponInventory().get(j).getType()==this.getWeaponInventory().get(i).getType()) {
										this.setItemInHand(this.getWeaponInventory().get(j));
										break;
								}
							}
							if ((Weapon)this.getItemInHand()==this.getWeaponInventory().get(i)) {
								this.setItemInHand(this.getWeaponInventory().get(0));
							}
							this.getWeaponInventory().get(i).setPickedUp(false);
							return this.getWeaponInventory().remove(i);
				}
			}
			}
		}
		if (this.getItemInHand() instanceof Potion) {
			for (int i = 0; i < this.getPotionInventory().size(); i++) {
					if (((Potion)this.getItemInHand()).getEffect()==this.getPotionInventory().get(i).getEffect()) {
							for (int j = i+1; j < this.getPotionInventory().size(); j++) {
								if (this.getPotionInventory().get(j).getEffect()==this.getPotionInventory().get(i).getEffect()) {
										this.setItemInHand(this.getPotionInventory().get(j));
										break;
								}
							}
							if ((Potion)this.getItemInHand()==this.getPotionInventory().get(i)) {
								this.setItemInHand(this.getWeaponInventory().get(0));
							}
							this.getPotionInventory().get(i).setPickedUp(false);
							return this.getPotionInventory().remove(i);
				}
			}
		}
		return null;
	}
	
	public boolean pickUp(GameObject gameObject) {
		if (gameObject instanceof Item) {
			int accum=0;
			if (((Item)gameObject).getLevel()<=this.getLevel()) {
				if (((Item)gameObject) instanceof Weapon) {
					for (int i = 0; i < this.getWeaponInventory().size(); i++) {
						if (this.getWeaponInventory().get(i).getType()==((Weapon)((Item)gameObject)).getType()) {
							accum++;
						}
					}
					if (accum<3) {
						((Weapon)((Item)gameObject)).setPickedUp(true);
						this.getWeaponInventory().add((Weapon)((Item)gameObject));
						this.setChanged();
						this.notifyObservers(Entity.ITEM_CHANGED);
						return true;
					}
				}
				if (((Item)gameObject) instanceof Potion) {
					for (int i = 0; i < this.getPotionInventory().size(); i++) {
						if (this.getPotionInventory().get(i).getEffect()==((Potion)((Item)gameObject)).getEffect()) {
							accum++;
						}
					}
					if (accum<3) {
						((Potion)((Item)gameObject)).setPickedUp(true);
						this.getPotionInventory().add((Potion)((Item)gameObject));
						this.setChanged();
						this.notifyObservers(Entity.ITEM_CHANGED);
						return true;
					}
				}
			}
		}
		if (gameObject instanceof Resource) {
			for(Entry<Resource, Integer> entry : this.getResourceInventory().entrySet()) {
				Resource resourceKey = entry.getKey();
			    if (((Resource)gameObject).getType()==resourceKey.getType()) {
			    	resourceKey.setPickedUp(true);
			    	this.getResourceInventory().put(resourceKey,this.getResourceInventory().get(resourceKey)+1);
			    	this.setChanged();
			    	this.notifyObservers(Entity.RESOURCES_CHANGED);
			    	return true;
				}
			}
		}
		return false;
	}
	
	public void move(int left, int right, int down, int up){
		if (this.getState()==EntityState.ALIVE) {
			this.setPositionX(this.getPositionX()+right);
			this.setPositionX(this.getPositionX()-left);
			this.setPositionY(this.getPositionY()+up);
			this.setPositionY(this.getPositionY()-down);
		}
	}
	
	public int getExperience() {
		return experience;
	}
	
	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
