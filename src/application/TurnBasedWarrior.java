package application;

public class TurnBasedWarrior extends Character {

	public TurnBasedWarrior(String name, int x, int y) {
		super(name, 100, 10, x, y);
	}

	@Override
	public void attack(Character target) {
		System.out.println(name + " attacks " + target.getName() + "!");
		target.receiveDamage(damage);
	}

	@Override
	public void useSpecialAbility(Character target) {
		System.out.println(name + " uses special ability: Rage Strike!");
		target.receiveDamage(damage * 2);
	}
}
