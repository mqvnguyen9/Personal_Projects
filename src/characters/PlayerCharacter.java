package characters;

import java.util.Random;

import items.AilmentRemovalItem;
import items.EquippableItem;
import items.HealingItem;
import items.Inventory;
import items.Item;
import items.ManaItem;
import system.Magic;
import system.StatusAilments;

public class PlayerCharacter extends Character {

	private int currentExp, maxExp, level, currentMP, maxMP;
	private EquippableItem[] equipment;
	private Inventory inventory;
	private final Random rng;
	
	public PlayerCharacter(String name) {
		super(name, Character.Type.NEUTRAL);
		rng = new Random();

		setMaxHP(rng.nextInt(10) + 10);
		setCurrentHP(getMaxHP());
		setAtk(rng.nextInt(5) + 5);
		setDef(rng.nextInt(5) + 5);
		setIntel(rng.nextInt(5) + 5);
		setAlive(true);
		
		currentExp = 0;
		maxExp = 10;
		level = 1;
		maxMP = rng.nextInt(5) + 1;
		currentMP = maxMP;
		equipment = new EquippableItem[1];
		inventory = new Inventory();
		inventory.addItem(new HealingItem("Potion", 5));
		inventory.addItem(new ManaItem("Ether", 5));
		inventory.addItem(new AilmentRemovalItem("Antidote", StatusAilments.POISON));
	}
	
	public void equipItem(EquippableItem item) {
		destroyPreviousItem();
		equipment[0] = item;
		setCurrentHP(getCurrentHP() + equipment[0].getBonusHP());
		setMaxHP(getMaxHP() + equipment[0].getBonusHP());
		setAtk(getAtk() + equipment[0].getBonusAtk());
		setDef(getDef() + equipment[0].getBonusDef());
		System.out.println("You have equipped: " + item.getName());
	}
	
	private void destroyPreviousItem() {
		if (equipment[0] != null) {
			setMaxHP(getMaxHP() - equipment[0].getBonusHP());
			if (getMaxHP() < getCurrentHP()) {
				setCurrentHP(getMaxHP());
			}
			setAtk(getAtk() - equipment[0].getBonusAtk());
			setDef(getDef() - equipment[0].getBonusDef());
			equipment[0] = null;
		}
	}


	private void levelUp() {
		if (currentExp >= maxExp) {
			currentExp -= maxExp;
			level++;
			
			System.out.println(getName() + " is now level " + getLevel() + "!");
			setMaxHP(getMaxHP() + rng.nextInt(5) + 1);
			setCurrentHP(getMaxHP());
			maxMP += rng.nextInt(3) + 1;
			currentMP = maxMP;
			setAtk(getAtk() + rng.nextInt(2));
			setDef(getDef() + rng.nextInt(1));
			setIntel(getIntel() + rng.nextInt(2));
			setMaxExp(getMaxExp() + getMaxExp()/2);
		}
	}
	
	private void checkExp(Character target, int modifier) {
		if (!target.isAlive()) {
			int exp = rng.nextInt(modifier) + 1;
			System.out.println("You gained " + exp + " experience!");
			currentExp += exp;
			levelUp();
			System.out.printf("You now have %d/%d experience.\n", getCurrentExp(), getMaxExp());
		}
	}
	
	public void attack(Character target, int modifier) {
		super.attack(target);
		checkExp(target, modifier);
	}

	@Override
	public void attack(Character target) {
		super.attack(target);
	}
	
	public void magic(Character target, Magic magicType, int modifier) {
		magic(target, magicType);
		checkExp(target, modifier);
	}
	
	@Override
	public void magic(Character target, Magic magicType) {
		if (getCurrentMP() >= magicType.getMPCost()) {
			currentMP -= magicType.getMPCost();
			if (magicType.equals(Magic.CURE)) {
				int healing = getIntel();
				target.setCurrentHP(target.getCurrentHP() + healing);
				System.out.printf("%s uses %s to recover %d health (%d/%d HP)\n", getName(), magicType, healing, target.getCurrentHP(), target.getMaxHP());
			} else if (magicType.equals(Magic.POISONA)) {
				if (getAilment() != null && getAilment().equals(StatusAilments.POISON)) {
					setAilment(null);
					System.out.println(target.getName() + " is no longer affected by " + StatusAilments.POISON);
				}
			} else {
				int damage = getIntel();
				if (magicType.getStrongAgainst().equals(target.getType())) {
					damage *= 2;
					System.out.print("SUPER EFFECTIVE! ");
				} else if (magicType.getWeakAgainst().equals(target.getType())) {
					damage /= 2;
					System.out.println("Not very effective... ");
				}
				System.out.println(getName() + " uses a " + magicType + " spell for " + damage + " damage!");
				target.setCurrentHP(target.getCurrentHP() - damage);
				target.isDead();
			}
		} else {
			System.out.println(getName() + " does not have enough mana!");
		}
	}

	@Override
	public void isDead() {
		if (getCurrentHP() <= 0) {
			System.out.println(getName() + " has been slain...");
			setAlive(false);
		}
	}
	
	public void useItem(Item item) {
		if (inventory.getItem(item.getName()) != null) {
			if (item instanceof HealingItem) {
				setCurrentHP(getCurrentHP() + ((HealingItem) item).getHealingAmount() <= getMaxHP() ? getCurrentHP() + ((HealingItem) item).getHealingAmount() : getMaxHP());
				System.out.printf("%s uses a %s - HP: %d/%d (%d %ss remaining)\n", getName(), item.getName(), getCurrentHP(), getMaxHP(), inventory.getQuantityOf(item.getName())-1,
						item.getName());
				inventory.removeItem(item);
			} else if (item instanceof ManaItem) {
				setCurrentMP(getCurrentMP() + ((ManaItem) item).getManaAmount() <= getMaxMP() ? getCurrentMP() + ((ManaItem) item).getManaAmount() : getMaxMP());
				System.out.printf("%s uses a %s - MP: %d/%d (%d %ss remaining)\n", getName(), item.getName(), currentMP, maxMP, inventory.getItemMap().get(item.getName()).size()-1,
						item.getName());
				inventory.removeItem(item);
			} else if (item instanceof AilmentRemovalItem) {
				if (getAilment() != null && getAilment().equals(((AilmentRemovalItem) item).getAilmentRemoved())) {
					System.out.printf("%s uses a %s! (%d %ss remaining)\n", getName(), item.getName(), inventory.getQuantityOf(item.getName())-1, item.getName());
					setAilment(null);
					inventory.removeItem(item);
				} else {
					System.out.println(item.getName() + " has no effect!");
				}
			}
		} else {
			System.out.println("Item doesn't exist!");
		}
	}
	
	public int getCurrentExp() {
		return currentExp;
	}
	
	public void setCurrentExp(int currentExp) {
		this.currentExp = currentExp;
	}
	
	public int getMaxExp() {
		return maxExp;
	}
	
	public void setMaxExp(int maxExp) {
		this.maxExp = maxExp;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getCurrentMP() {
		return currentMP;
	}

	public void setCurrentMP(int currentMP) { this.currentMP = currentMP; }

	public int getMaxMP() {
		return maxMP;
	}

	public void setMaxMP(int maxMP) {
		this.maxMP = maxMP;
	}

	public EquippableItem[] getEquipment() {
		return equipment;
	}

	public void setEquipment(EquippableItem[] equipment) {
		this.equipment = equipment;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public void rest() {
		int heal = getCurrentHP()/2;
		setCurrentHP(getCurrentHP() + heal);
		System.out.printf("%s rests and heals for %d health! (%d/%d HP)\n", getName(), heal, getCurrentHP(), getMaxHP());
		if (getAilment() != null) {
			System.out.println(getName() + " also recovers from " + getAilment());
			setAilment(null);
		}
	}
}
