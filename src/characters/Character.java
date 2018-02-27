package characters;

import java.util.Random;

import system.Combat;
import system.StatusAilments;

public abstract class Character implements Combat {

	public enum Type {
		NEUTRAL, FIRE, ICE, LIGHTNING, HOLY, DARK
	}
	
	private int currentHP, maxHP, atk, def, intel;
	private String name;
	private boolean isAlive;
	private Type type;
	private StatusAilments currentAilment;
	private final Random rng;

	public Character(String name, Type type) {
		rng = new Random();
		this.name = name;
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
		if (this.currentHP > maxHP) this.currentHP = maxHP;
	}
	
	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}
	
	public int getIntel() {
		return intel;
	}

	public void setIntel(int intel) {
		this.intel = intel;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public StatusAilments getAilment() {
		return currentAilment;
	}
	
	public void setAilment(StatusAilments ailment) {
		this.currentAilment = ailment;
	}
	
	@Override
	public void attack(Character target) {
		int damage = getAtk() - rng.nextInt(target.getDef());
		damage = damage < 0 ? 0 : damage;
		damage = (currentAilment != null && currentAilment.equals(StatusAilments.WEAKEN) ? damage / 2 : damage);
		System.out.println(this.getName() + " attacks " + target.getName() + " for " + damage + " damage!");
		target.setCurrentHP(target.getCurrentHP() - damage);
		target.isDead();
	}
	
	@Override
	public void statusAilmentCheck(Character target, StatusAilments ailment, int odds) {
		int check = rng.nextInt(101) + 1;
		if (check < odds) {
			target.setAilment(ailment);
			System.out.println(target.getName() + " has been afflicted with " + ailment);
		} else if (ailment.equals(StatusAilments.WEAKEN)) {
			System.out.println("Weakening has no effect on " + target.getName() + "!");
		}
	}

	@Override
	public void ailmentEffect() {
		if (currentAilment != null) {
			if (currentAilment.equals(StatusAilments.POISON)) {
				int damage = rng.nextInt(maxHP/10) + 1;
				currentHP -= damage;
				System.out.println(StatusAilments.POISON.toString() + " causes " + name + " to take " + damage + " damage!");
			}
		}
	}

	@Override
	public void removeAilment() {
		if (currentAilment != null) {
			int check = rng.nextInt(101) + 1;
			if (check < currentAilment.getRemovalChance()) {
				System.out.println(name + " is no longer affected by " + currentAilment + ".");
				currentAilment = null;
			}
		}
	}
}
