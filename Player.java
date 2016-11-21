/**
 * A player in jeopardy. Players have names and an amount of
 * points/dollars.
 */
public class Player {
  /**
   * The player's name. This is how the players are identified to the
   * user.
   */
  private String name;

  /**
   * The amount of money the player has. Each player starts at $0, and
   * no player can have negative money.
   */
  private int dollars;

  /**
   * Create a player. Players start at $0.
   *
   * @param name the player's name
   */
  public Player(String name) {
    this.name = name;
    this.dollars = 0;
  }

  public String getName() {
    return this.name;
  }

  public int getDollars() {
    return this.dollars;
  }

  /**
   * Add to the amount of money the player has. If the result is
   * negative, the player has $0.
   *
   * @param amount the amount of money to add to the player's total
   */
  public void addDollars(int amount) {
    this.setDollars(this.dollars + amount);
  }

  /**
   * Set the amount of money the player has. If the amount is
   * negative, the player has $0.
   *
   * @param amount the new amount of money the player has
   */
  public void setDollars(int amount) {
    this.dollars = amount;

    if (this.dollars < 0) { // If a player goes below $0, raise them up to $0
      this.dollars = 0;
    }
  }
}
