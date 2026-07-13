package application;

public class TurnBasedThief extends Character {

	public TurnBasedThief(String name, int x, int y) {
		super(name, 70, 12, x, y);
	}

	@Override
	public void attack(Character target) {
		System.out.println(name + " stealth attacks " + target.getName() + "!");
		target.receiveDamage(damage);
		if (Math.random() < 0.2) {
			System.out.println(name + " lands a critical hit!");
			target.receiveDamage(damage);
		}
	}

	@Override
	public void useSpecialAbility(Character target) {
		System.out.println(name + " uses special ability: Quick Strike!");
		target.receiveDamage(damage * 3);
	}
}
