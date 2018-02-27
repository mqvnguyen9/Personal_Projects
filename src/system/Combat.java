package system;

import characters.Character;

public interface Combat {
	
	void attack(Character target);
	void magic(Character target, Magic magic);
	void statusAilmentCheck(Character target, StatusAilments ailment, int odds);
	void ailmentEffect();
	void removeAilment();
	void isDead();
}
