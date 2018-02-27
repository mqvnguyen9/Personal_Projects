package items;

public class EquippableItem extends Item {

	private int bonusHP, bonusAtk, bonusDef;
	
	public EquippableItem(String name, int bonusHP, int bonusAtk, int bonusDef) {
		setName(name);
		this.bonusHP = bonusHP;
		this.bonusAtk = bonusAtk;
		this.bonusDef = bonusDef;
	}

	public int getBonusHP() {
		return bonusHP;
	}

	public void setBonusHP(int bonusHP) {
		this.bonusHP = bonusHP;
	}

	public int getBonusAtk() {
		return bonusAtk;
	}

	public void setBonusAtk(int bonusAtk) {
		this.bonusAtk = bonusAtk;
	}

	public int getBonusDef() {
		return bonusDef;
	}

	public void setBonusDef(int bonusDef) {
		this.bonusDef = bonusDef;
	}
	
	@Override
	public String toString() {
		return getName() + " HP:" + bonusHP + " Atk:" + bonusAtk + " Def:" + bonusDef;
	}
}
