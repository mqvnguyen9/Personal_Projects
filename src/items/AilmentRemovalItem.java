package items;

import system.StatusAilments;

public class AilmentRemovalItem extends Item {

	private final StatusAilments ailmentRemoved;
	public AilmentRemovalItem(String name, StatusAilments ailmentRemoved) {
		setName(name);
		this.ailmentRemoved = ailmentRemoved;
	}
	public StatusAilments getAilmentRemoved() {
		return ailmentRemoved;
	}
	
	@Override
	public String toString() {
		return getName() + " - Cures " + ailmentRemoved.toString();
	}
}
