package ethos.model.content.staff;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import ethos.model.players.Player;
import ethos.model.players.Right;
import ethos.util.Misc;

public class PunishmentPanel {

	/**
	 * The player that will manage this panel
	 */
	private final Player player;

	/**
	 * The target of the punishment
	 */
	private Player target;

	/**
	 * Creates a new class that will manage punishments for other players through a panel, or interface.
	 * 
	 * @param player the player that will be managing this interface
	 */
	public PunishmentPanel(Player player) {
		this.player = player;
	}

	/**
	 * Visually displays the interface for the player
	 */
	public void open(Player target) {
		this.target = target;
		player.getPA().sendString("'" + Misc.capitalize(target.playerName) + "'", 33206);
		player.getPA().sendString("Reason", 33205);
		player.getPA().sendString("Duration", 33211);
		for (Punishment punishment : Punishment.values()) {
			player.getPA().sendChangeSprite(punishment.getSpriteId(), (byte) 0);
			player.getPA().sendString(punishment.toString(), punishment.getTextId());
		}
		player.getPA().showInterface(33200);
	}

	public boolean clickButton(int buttonId) {
		if (!player.getRightGroup().isOrInherits(Right.MODERATOR)) {
			return false;
		}
		for (Punishment punishment : Punishment.values()) {
			if (punishment.getButtonId() == buttonId) {
				if (target == null) {
					player.sendMessage("You can only operate this panel after selecting 'moderate' when right clicking.");
					return true;
				}
				if (!player.getRightGroup().isOrInherits(punishment.rights)) {
					player.sendMessage("You do not have the rights to operate this.");
					return true;
				}
				Punishment.PUNISHMENTS.forEach(p -> player.getPA().sendChangeSprite(p.getSpriteId(), (byte) 0));
				player.getPA().sendChangeSprite(punishment.getSpriteId(), (byte) 1);
				return true;
			}
		}
		return false;
	}

	/**
	 * Modifies the reasoning behind the punishment
	 * 
	 * @param reason the reason for punishing a player
	 */
	public void setReason(String reason) {
	}

	/**
	 * Modifies the duration of the punishment
	 * 
	 * @param duration the duration of time in minutes
	 */
	public void setDuration(int duration) {
	}

	/**
	 * The target for this punishment
	 * 
	 * @return the player that is the target of this punishment
	 */
	public Player getTarget() {
		return target;
	}

	enum Punishment {
		MUTE(Right.MODERATOR, true, true), BAN(Right.MODERATOR, true, true), KICK(Right.MODERATOR, false, false), JAIL(Right.MODERATOR, true, true), IPBAN(Right.ADMINISTRATOR,
				true, false), MACBAN(Right.ADMINISTRATOR, false, false);

		/**
		 * The rights required to execute the punishment
		 */
		private final Right rights;

		/**
		 * A new punishment that can be made on a player
		 * 
		 * @param rights the rights required to execute
		 * @param reason determines if a reason is required
		 */
		private Punishment(Right rights, boolean reason, boolean duration) {
			this.rights = rights;
		}

		/**
		 * The button that when clicked enacts the punishment
		 * 
		 * @return the button id
		 */
		public int getButtonId() {
			return 129191 + (ordinal() * 2);
		}

		/**
		 * The value of the component that displays the sprite
		 * 
		 * @return the sprite component id
		 */
		public int getSpriteId() {
			return 33215 + (ordinal() * 2);
		}

		/**
		 * The component id the string of text is displayed on
		 * 
		 * @return the text component id
		 */
		public int getTextId() {
			return 33216 + (ordinal() * 2);
		}

		@Override
		public String toString() {
			return Misc.capitalize(name().toLowerCase());
		}

		/**
		 * An unmodifiable set of punishments
		 */
		static final Set<Punishment> PUNISHMENTS = Collections.unmodifiableSet(EnumSet.allOf(Punishment.class));
	}

}
