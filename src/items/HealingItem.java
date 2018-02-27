package items;

public class HealingItem extends Item {
	private int healingAmount;
	
	public HealingItem(String name, int healingAmount) {
		setName(name);
		this.healingAmount = healingAmount;
	}

	public int getHealingAmount() {
		return healingAmount;
	}

	public void setHealingAmount(int healingAmount) {
		this.healingAmount = healingAmount;
	}
	
	@Override
	public String toString() {
		return getName() + " - Heals for " + healingAmount + " HP";
	}
}
