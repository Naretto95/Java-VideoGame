package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;
/**
 *
 * @author Lilian Naretto
 *
 */
public class Entity extends Observable implements Serializable {

	private static final long serialVersionUID = 1L;
	public static Integer HEALTH_CHANGED = new Integer(0);
	public static Integer DIED = new Integer(1);
	public static Integer RESOURCES_CHANGED = new Integer(2);
	public static Integer ITEM_CHANGED = new Integer(3);

	/** Denominator for the random chance of missing an attack or stunning the target (1 in N). */
	private static final int STUN_CHANCE_DENOMINATOR = 5;
	/** Max health granted per level. */
	private static final int HEALTH_PER_LEVEL = 100;
	/** Number of turns a STUNNED entity stays stunned before recovering. */
	private static final int STUN_DURATION_TURNS = 5;
	/** Damage dealt per potion level when the POISON effect is applied. */
	private static final int POISON_DAMAGE_PER_LEVEL = 10;

	private int health;
	private int positionX;
	private int positionY;
	private int level;
	private int stunCounter;
	private Item itemInHand;
	private EntityState state;
	private List<Weapon> weaponInventory;
	private List<Potion> potionInventory;
	private Map<Resource, Integer> resourceInventory;

	public Entity(int level, int positionX, int positionY) {
		List<Weapon> weaponInventory = new ArrayList<>();
		List<Potion> potionInventory = new ArrayList<>();
		Map<Resource, Integer> resourceInventory= new HashMap<>();
		this.setWeaponInventory(weaponInventory);
		this.setPotionInventory(potionInventory);
		this.setState(EntityState.ALIVE);
		this.setStunCounter(0);
		this.setLevel(level);
		this.setResourceInventory(resourceInventory);
		this.setPositionX(positionX);
		this.setPositionY(positionY);
		this.setHealth(level*HEALTH_PER_LEVEL);
		this.getWeaponInventory().add(new Weapon(WeaponType.UNARMED,this.getLevel()));
		this.getResourceInventory().put(new Resource(ResourceType.KEY),0);
		this.getResourceInventory().put(new Resource(ResourceType.WOOD),0);
		this.getResourceInventory().put(new Resource(ResourceType.IRON),0);
		this.getResourceInventory().put(new Resource(ResourceType.GOLD),0);
	}

	public void takeDamage(Weapon weapon){
		this.setHealth(this.getHealth()-weapon.getDamage());
		if (this.getHealth()<=0) {
			this.setState(EntityState.DEAD);
			this.setChanged();
			this.notifyObservers(Entity.DIED);
		}
	}

	/**
	 * Attempts to hit {@code target} with {@code weapon}: there is a 1-in-{@value #STUN_CHANCE_DENOMINATOR}
	 * chance the attack misses entirely, and (if it doesn't) a 1-in-{@value #STUN_CHANCE_DENOMINATOR} chance
	 * it stuns the target in addition to dealing damage, provided the target is within weapon range.
	 */
	public void attack(Entity target, Weapon weapon){
		if (weapon.getDurability()>0) {
			boolean missed = new Random().nextInt(STUN_CHANCE_DENOMINATOR)==0;
				if (!missed) {
					boolean stunned = new Random().nextInt(STUN_CHANCE_DENOMINATOR)==0;
					if (Math.abs(target.getPositionX()-this.getPositionX())<= weapon.getRange() && target.getPositionY()==this.getPositionY() || Math.abs(target.getPositionY()-this.getPositionY())<= weapon.getRange() && target.getPositionX()==this.getPositionX() ) {
						if (stunned) {
							target.setState(EntityState.STUNNED);
						}target.takeDamage(weapon);

						weapon.setDurability(weapon.getDurability()-1);
						if (weapon.getDurability()<=0) {
							weapon.setActive(false);
						}
						this.refreshInventory();
					}
				}
		}
	}

	public void switchItem(Item item) {
		if (this.getState()==EntityState.ALIVE) {
			if (item instanceof Weapon) {
				for (int i = 0; i < this.getWeaponInventory().size(); i++) {
					if (this.itemInHand instanceof Weapon) {
						if (this.getWeaponInventory().get(i).getType()==((Weapon)this.itemInHand).getType()) {
							for (int j = 0; j < this.getWeaponInventory().size(); j++) {
								if (this.getWeaponInventory().get(j).getType()==((Weapon)this.itemInHand).getType() && this.getWeaponInventory().get(j)!=(Weapon)this.itemInHand && ((Weapon)item).getType()==((Weapon)this.itemInHand).getType()) {
									this.itemInHand=this.getWeaponInventory().get(j);
									this.getWeaponInventory().set(j,this.getWeaponInventory().get(i));
									this.getWeaponInventory().set(i,(Weapon)this.itemInHand);
								}
								else {
									if (this.getWeaponInventory().get(j).getType()==((Weapon)item).getType() && this.getWeaponInventory().get(j)!=(Weapon)this.itemInHand) {
										this.itemInHand=this.getWeaponInventory().get(j);
										break;
									}
								}
							}
							break;
						}
					}else {
						for (int j = 0; j < this.getWeaponInventory().size(); j++) {
							if (this.getWeaponInventory().get(j).getType()==((Weapon)item).getType()) {
								this.itemInHand=this.getWeaponInventory().get(j);
							}
						}
					}
				}
			}
			if (item instanceof Potion) {
				for (int i = 0; i < this.getPotionInventory().size(); i++) {
					if (this.itemInHand instanceof Potion) {
						if (this.getPotionInventory().get(i).getEffect()==((Potion)this.itemInHand).getEffect()) {
							for (int j = 0; j < this.getPotionInventory().size(); j++) {
								if (this.getPotionInventory().get(j).getEffect()==((Potion)this.itemInHand).getEffect() && this.getPotionInventory().get(j)!=(Potion)this.itemInHand && ((Potion)item).getEffect()==((Potion)this.itemInHand).getEffect()) {
									this.itemInHand=this.getPotionInventory().get(j);
									this.getPotionInventory().set(j,this.getPotionInventory().get(i));
									this.getPotionInventory().set(i,(Potion)this.itemInHand);
								}
								else {
									if (this.getPotionInventory().get(j).getEffect()==((Potion)item).getEffect() && this.getPotionInventory().get(j)!=(Potion)this.itemInHand) {
										this.itemInHand=this.getPotionInventory().get(j);
										break;
									}
								}
							}
							break;
						}
					}else {
						for (int j = 0; j < this.getPotionInventory().size(); j++) {
							if (this.getPotionInventory().get(j).getEffect()==((Potion)item).getEffect()) {
								this.itemInHand=this.getPotionInventory().get(j);
							}
						}
					}
				}
			}
		}else {
			if (this.getState()==EntityState.STUNNED) {
				this.setStunCounter(this.getStunCounter()+1);
				if (this.getStunCounter()==STUN_DURATION_TURNS) {
					this.setStunCounter(0);
					this.setState(EntityState.ALIVE);
				}
			}
		}
		this.setChanged();
		this.notifyObservers(ITEM_CHANGED);
	}

	public void refreshInventory(){
		if (this.itemInHand instanceof Weapon) {
			for (int i = 0; i < this.getWeaponInventory().size(); i++) {
					if (((Weapon)this.itemInHand)==this.getWeaponInventory().get(i)) {
						if (!this.itemInHand.isActive()) {
							for (int j = 0; j < this.getWeaponInventory().size(); j++) {
								if (this.getWeaponInventory().get(j).getType()==this.getWeaponInventory().get(i).getType() && this.getWeaponInventory().get(j)!=(Weapon)this.itemInHand) {
										this.itemInHand=this.getWeaponInventory().get(j);
								}
							}
							if ((Weapon)this.itemInHand==this.getWeaponInventory().get(i)) {
								this.itemInHand=this.getWeaponInventory().get(0);
							}
							this.getWeaponInventory().remove(i);
						}else {
							this.getWeaponInventory().set(i,(Weapon)this.itemInHand);
							break;
						}
				}
			}
		}
		if (this.itemInHand instanceof Potion) {
			for (int i = 0; i < this.getPotionInventory().size(); i++) {
					if (((Potion)this.itemInHand)==this.getPotionInventory().get(i)) {
						if (!this.itemInHand.isActive()) {
							for (int j = i+1; j < this.getPotionInventory().size(); j++) {
								if (this.getPotionInventory().get(j).getEffect()==this.getPotionInventory().get(i).getEffect()&& this.getPotionInventory().get(j)!=(Potion)this.itemInHand) {
										this.itemInHand=this.getPotionInventory().get(j);
								}
							}
							if ((Potion)this.itemInHand==this.getPotionInventory().get(i)) {
								this.itemInHand=this.getWeaponInventory().get(0);
							}
							this.getPotionInventory().remove(i);
						}else {
							this.getPotionInventory().set(i,(Potion)this.itemInHand);
							break;
						}
				}
			}
		}
	}

	public void use(Entity target){
		if (this.getState()==EntityState.ALIVE) {
			if (this.itemInHand instanceof Weapon) {
				if (target.getState()==EntityState.ALIVE || target.getState()==EntityState.STUNNED) {
					attack(target,(Weapon)this.itemInHand);
					if (this instanceof Player && target instanceof Enemy) {
						if (target.getState()==EntityState.DEAD) {
							((Player)this).setExperience(((Player)this).getExperience()+((Enemy)target).getExperienceReward());
							((Player)this).levelUp();
						}
					}
				}
			}else {
				if (this.itemInHand instanceof Potion) {
					switch (((Potion)this.itemInHand).getEffect()) {
					case STUN:
						if (target.getPositionX() == this.getPositionX()) {
							this.poison(target);
						}else {
							if (target.getPositionY() == this.getPositionY()) {
								this.poison(target);
							}
						}
						break;

					case POISON:
						if (target.getState()==EntityState.ALIVE || target.getState()==EntityState.STUNNED) {
							if (Math.abs(target.getPositionX()-this.getPositionX())<= 3 && target.getPositionY()==this.getPositionY() || Math.abs(target.getPositionY()-this.getPositionY())<= 3 && target.getPositionX()==this.getPositionX() ) {
								this.poison(target);
							}
						}
						break;

					case HEAL :
							if (this.getHealth()<this.getLevel()*HEALTH_PER_LEVEL) {
								if (((Potion)this.itemInHand).getLevel()+this.getHealth() <= this.getLevel()*HEALTH_PER_LEVEL) {
									this.setHealth(((Potion)this.itemInHand).getLevel()+this.getHealth());
									((Potion)this.itemInHand).setActive(false);
									this.refreshInventory();
								}else {
									this.setHealth(this.getLevel()*HEALTH_PER_LEVEL);
									((Potion)this.itemInHand).setActive(false);
									this.refreshInventory();
								}
						}
						break;

					case DAMAGE_BOOST :
						for (int i = 0; i < this.getWeaponInventory().size(); i++) {
							if (this.getWeaponInventory().get(i).getType()!=WeaponType.UNARMED && !this.getWeaponInventory().get(i).isDamageBoosted()) {
								this.getWeaponInventory().get(i).setDamageBoosted(true);
								this.getWeaponInventory().get(i).setDamage(this.getWeaponInventory().get(i).getDamage()+((Potion)this.itemInHand).getLevel());
								((Potion)this.itemInHand).setActive(false);
								this.refreshInventory();
								break;
							}
						}
						break;

					default:
						break;
					}
				}
			}
		}else {
			if (this.getState()==EntityState.STUNNED) {
				this.setStunCounter(this.getStunCounter()+1);
				if (this.getStunCounter()==STUN_DURATION_TURNS) {
					this.setStunCounter(0);
					this.setState(EntityState.ALIVE);
				}
			}
		}
		this.refreshInventory();
		this.setChanged();
		this.notifyObservers(ITEM_CHANGED);
	}

	public void poison(Entity target) {
		target.applyPoison((Potion)this.itemInHand);
		((Potion)this.itemInHand).setActive(false);
		this.refreshInventory();
		if (this instanceof Player && target instanceof Enemy) {
			if (target.getState()==EntityState.DEAD) {
				((Player)this).setExperience(((Player)this).getExperience()+((Enemy)target).getExperienceReward());
				((Player)this).levelUp();
				((Enemy)target).dropLoot();
				target=null;
			}
		}
	}


	public void applyPoison(Potion potion){
		switch (potion.getEffect()) {
		case STUN:
			this.setState(EntityState.STUNNED);
			break;
		case POISON:
			this.setHealth(this.getHealth()-potion.getLevel()*POISON_DAMAGE_PER_LEVEL);
			if (this.getHealth()<=0) {
				this.setState(EntityState.DEAD);
			}
			break;

		default:
			break;
		}
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
		this.setChanged();
		this.notifyObservers(Entity.HEALTH_CHANGED);
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Item getItemInHand() {
		return itemInHand;
	}

	public void setItemInHand(Item itemInHand) {
		this.itemInHand = itemInHand;
	}

	public EntityState getState() {
		return state;
	}

	public void setState(EntityState state) {
		this.state = state;
	}

	public Map<Resource, Integer> getResourceInventory() {
		return resourceInventory;
	}

	public void setResourceInventory(Map<Resource, Integer> resourceInventory) {
		this.resourceInventory = resourceInventory;
		this.setChanged();
		this.notifyObservers(RESOURCES_CHANGED);
	}

	public List<Weapon> getWeaponInventory() {
		return weaponInventory;
	}

	public void setWeaponInventory(List<Weapon> weaponInventory) {
		this.weaponInventory = weaponInventory;
	}

	public List<Potion> getPotionInventory() {
		return potionInventory;
	}

	public void setPotionInventory(List<Potion> potionInventory) {
		this.potionInventory = potionInventory;
	}

	public int getStunCounter() {
		return stunCounter;
	}

	public void setStunCounter(int stunCounter) {
		this.stunCounter = stunCounter;
	}




}
