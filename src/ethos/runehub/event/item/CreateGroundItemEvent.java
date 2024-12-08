package ethos.runehub.event.item;

import ethos.Server;
import ethos.event.Event;
import ethos.model.items.GroundItem;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import org.runehub.api.model.world.region.location.Location;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO Fix global drops. Currently drops only become public to players within 60 tiles. Drops should be visible to all when public
 */
public class CreateGroundItemEvent extends Event<GroundItem> {

    //~5 minutes
    private static final int EVENT_DURATION = 100;
    //Execute code for this event at a rate of half the total duration
    private static final int CYCLE_RATE = EVENT_DURATION / 2;

    @Override
    public void initialize() {
        super.initialize();
        if (owner != null) { //checks if the owner is non-null
            createItemForPlayer(owner);//creates the item for just the owner
        } else {
            this.getNearbyPlayers().forEach(this::createItemForPlayer); //this makes the item visible to all nearby players
        }
    }
    @Override
    public void execute() {
        if (elapsed == CYCLE_RATE && Server.itemHandler.itemExists(attachment)) { //this checks if the event is half over
            this.getNearbyPlayers().forEach(this::createItemForPlayer); //this makes the item visible to all nearby players
        } else { //this will only be true when the event executes the second time
            this.stop(); //stops the event and removes the ground item
        }
    }

    @Override
    public void stop() {
        this.getNearbyPlayers().forEach(this::removeItemForPlayer);
        if (owner != null) {
            removeItemForPlayer(owner);
        }
        Server.itemHandler.removeItem(attachment);
        super.stop();
    }

    /**
     * Creates a ground item for the given player
     * @param player the player the ground item is being created for
     */
    private void createItemForPlayer(Player player) {
        player.getItems().createGroundItem(attachment);
    }

    /**
     * Checks every player online and returns a list of all players within 60 tiles of the item
     *
     * @return
     */
    private List<Player> getNearbyPlayers() {
        return PlayerHandler.getPlayers().stream()
                .filter(player -> owner == null || player.getId() != owner.getId())
                .filter(player -> player.distanceToPoint(location.getXCoordinate(), location.getYCoordinate()) <= 60)
                .collect(Collectors.toList());
    }

    /**
     * Removes the item for a given player
     *
     * @param player the player the item is being removed for
     */
    private void removeItemForPlayer(Player player) {
        player.getItems().removeGroundItem(attachment);
    }

    public CreateGroundItemEvent(GroundItem attachment, Location location, Player owner) {
        this(attachment,location,EVENT_DURATION,owner);
    }

    public CreateGroundItemEvent(GroundItem attachment, Location location, int ticks, Player owner) {
        super(attachment, Math.max(ticks / 2, 1));
        this.location = location;
        this.owner = owner;
    }

    private final Location location;
    private final Player owner;
}
