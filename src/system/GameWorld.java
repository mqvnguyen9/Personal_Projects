package system;
import java.util.Random;
import java.util.Scanner;

import characters.Enemy;
import characters.PlayerCharacter;
import items.AilmentRemovalItem;
import items.EquippableItem;
import items.HealingItem;
import items.Item;
import items.ManaItem;

public class GameWorld {
	
	private PlayerCharacter player;
    private int floor;
    private Item roomItem;
	
	private Scanner kb;
	private final Random rng;
	
	public static void main (String[] args) {
		new GameWorld();
	}
	
	public GameWorld() {
		floor = 1;
		rng = new Random();

		createPlayerCharacter();
		gameLoop();
		kb.close();
	}

	private void createPlayerCharacter() {
		kb = new Scanner(System.in);
		boolean flag = false;
		System.out.println("What is your name?");
		String name = kb.nextLine();
		player = new PlayerCharacter(name);
		do {
			System.out.printf("You are %s, a Level %d warrior with %d/%d health, %d/%d mana, %d attack, %d defense, %d intelligence, and %d/%d experience.\n",
					player.getName(), player.getLevel(), player.getCurrentHP(), player.getMaxHP(), player.getCurrentMP(), player.getMaxMP(),
					player.getAtk(), player.getDef(), player.getIntel(), player.getCurrentExp(), player.getMaxExp());
			System.out.println("Would you like to reroll?");
			String answer = kb.nextLine().toLowerCase();
			if (answer.equals("yes")) {
				player = new PlayerCharacter(name);
			} else if (answer.equals("no")) {
				flag = true;
			}
		} while (!flag);
	}

	private void gameLoop() {
		while (player.isAlive()) {
			System.out.println("----------------------------------------------------------------------");
			System.out.println("Floor " + floor + "\n");
			if (floor % 5 != 0) {
                Enemy enemy = new Enemy("Bat", floor);
				while (enemy.isAlive()) {
					System.out.printf("%s: %d/%d HP; %d/%d MP\n", player.getName(), player.getCurrentHP(), player.getMaxHP(), player.getCurrentMP(), player.getMaxMP());
					System.out.printf("%s: %d/%d HP\n", enemy.getName(), enemy.getCurrentHP(), enemy.getMaxHP());
					System.out.println("What would you like to do?");
					switch(kb.nextLine().toLowerCase()) {
						case "attack": player.attack(enemy, floor); break;
						case "potion": player.useItem(player.getInventory().getItem("Potion")); break;
						case "ether": player.useItem(player.getInventory().getItem("Ether")); break;
						case "antidote": player.useItem(player.getInventory().getItem("Antidote")); break;
						case "fire": player.magic(enemy, Magic.FIRE, floor); break;
						case "blizzard": player.magic(enemy, Magic.BLIZZARD, floor); break;
						case "lightning": player.magic(enemy, Magic.LIGHTNING, floor); break;
						case "cure": player.magic(player, Magic.CURE); break;
						case "poisona": player.magic(player, Magic.POISONA); break;
						case "weaken" : player.statusAilmentCheck(enemy, StatusAilments.WEAKEN, 50); break;
						default: System.out.println("Invalid command. Try again."); continue;
					}
					player.ailmentEffect();
					player.removeAilment();
					if (enemy.isAlive()) {
						enemy.attack(player);
						enemy.ailmentEffect();
						enemy.removeAilment();
						if (!player.isAlive()) {
							gameOver();
							return;
						}
					}
				} 
			} else {
				Item item;
				System.out.print("You enter a safe room. You can either rest or pick up the following item: ");
				if (rng.nextBoolean()) {
					roomItem = new EquippableItem("Sword", rng.nextInt(floor), rng.nextInt(floor), rng.nextInt(floor));
					System.out.println(roomItem.toString() + ".");
					if (player.getEquipment()[0] != null) {
						System.out.println(player.getName() + " currently has: " + player.getEquipment()[0].toString());
					}
				} else {
					switch (rng.nextInt(3)) {
						case 0:
							roomItem = new HealingItem("Potion", 10);
							System.out.println(roomItem.toString() + ".");

							break;
						case 1:
							roomItem = new ManaItem("Ether", 5);
							System.out.println(roomItem.toString() + ".");
							break;
						case 2:
							roomItem = new AilmentRemovalItem("Antidote", StatusAilments.POISON);
							System.out.println(roomItem.toString() + ".");
							break;
					}
				}
				System.out.println("What will you do?");
				String answer = kb.nextLine().toLowerCase();
				if (answer.equals("rest")) {
					player.rest();
				} else if (answer.equals("item") || answer.equals("pickup")) {
					if (!(roomItem instanceof EquippableItem)) {
						player.getInventory().addItem(roomItem);
						System.out.println(player.getName() + " currently has " + player.getInventory().getQuantityOf(roomItem.getName()) + " " + roomItem.getName() + "s.");
					} else {
						player.equipItem((EquippableItem) roomItem);
					}
				}
			}
			floor++;
		}
	}
	
	private void gameOver() {
		System.out.println("You got to floor " + floor + ". Thanks for playing!");
	}
}
