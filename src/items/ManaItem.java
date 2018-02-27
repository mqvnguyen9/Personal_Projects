package items;

public class ManaItem extends Item {

	private int manaAmount;
	
	public ManaItem(String name, int manaAmount) {
		setName(name);
		this.manaAmount = manaAmount;
	}

	public int getManaAmount() {
		return manaAmount;
	}

	public void setManaAmount(int manaAmount) {
		this.manaAmount = manaAmount;
	}
	
	@Override
	public String toString() {
		return getName() + " - recover " + manaAmount + " MP";
	}
}
