package system;

import characters.Character;

public enum Magic {
	FIRE(3, Character.Type.LIGHTNING, Character.Type.ICE), 
	BLIZZARD(3, Character.Type.FIRE, Character.Type.LIGHTNING), 
	LIGHTNING(3, Character.Type.ICE, Character.Type.FIRE),
	CURE(3, Character.Type.DARK, null),
	POISONA(2, null, null);

	/**
	 * Enum for Magic spells. Each spell is associated with it MP cost, and the types it is effective/non-effective against.
	 */
	private final int mpCost;
	private final Character.Type strongAgainst;
	private final Character.Type weakAgainst;
	Magic(int mpCost, Character.Type strongAgainst, Character.Type weakAgainst) {
		this.mpCost = mpCost;
		this.strongAgainst = strongAgainst;
		this.weakAgainst = weakAgainst;
	}
	
	public int getMPCost() {
		return mpCost;
	}
	
	public Character.Type getStrongAgainst() {
		return strongAgainst;
	}
	
	public Character.Type getWeakAgainst() {
		return weakAgainst;
	}
}