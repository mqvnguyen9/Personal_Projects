package system;

public enum StatusAilments {
	POISON(20, 33), WEAKEN(50, 10);
	
	private final int applyChance;
    private final int removalChance;
	StatusAilments(int applyChance, int removalChance) {
		this.applyChance = applyChance;
		this.removalChance = removalChance;
	}
	public int getApplyChance() {
		return applyChance;
	}
	public int getRemovalChance() {
		return removalChance;
	}
}
