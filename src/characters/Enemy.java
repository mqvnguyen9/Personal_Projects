package characters;

import java.util.Random;

import system.Magic;
import system.StatusAilments;

public class Enemy extends Character {

	public Enemy(String name, int modifier) {
		super(name, Character.Type.LIGHTNING);
		Random rng = new Random();

		setMaxHP(rng.nextInt(5) + 5 + (modifier / 2));
		setCurrentHP(getMaxHP());
		setAtk(rng.nextInt(5) + 1 + (modifier / 2));
		setDef(rng.nextInt(5) + 1 + (modifier /2));
		setAlive(true);
	}

	@Override
	public void attack(Character target) {
		super.attack(target);
		if (target.isAlive()) {
			statusAilmentCheck(target, StatusAilments.POISON, 20);
		}
	}

	@Override
	public void isDead() {
		if (getCurrentHP() <= 0) {
			setAlive(false);
			System.out.println(getName() + " has been slain!");
		}
		
	}

	@Override
	public void magic(Character target, Magic magic) {
		//Enemies can't use magic... yet
	}
}
