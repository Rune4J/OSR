package ethos.runehub.action.click.node.impl.first;

import ethos.clip.Region;
import ethos.model.players.Player;
import ethos.runehub.action.click.node.ClickNodeAction;
import org.runehub.api.util.math.geometry.Point;

public class LadderAction extends ClickNodeAction {

    private final int nodeZ;

    public LadderAction(Player attachment, int nodeId, int nodeX, int nodeY, int nodeZ) {
        super(attachment, 2, nodeId, nodeX, nodeY);
        this.nodeZ = nodeZ;
    }

    @Override
    protected void onActionStart() {
        if (this.getNodeId() == 10 || this.getNodeId() == 11793) {
            this.getActor().startAnimation(827); // Animation for going down
        } else if (this.getNodeId() == 11 || this.getNodeId() == 17789) {
            this.getActor().startAnimation(828); // Animation for going up
        }
    }

    @Override
    protected void onActionStop() {
    }

    @Override
    protected void onTick() {
        if (this.getNodeId() == 11 || this.getNodeId() == 17789) { // Move player up
            final Point upPoint = this.getUpPoint();
            if (upPoint != null) {
                this.getActor().getPA().movePlayer(upPoint.getX(), upPoint.getY(), nodeZ + 1);
            } else {
                this.getActor().sendMessage("The way is blocked.");
            }
        } else if (this.getNodeId() == 10 || this.getNodeId() == 11793) { // Move player down
            final Point downPoint = this.getDownPoint();
            if (downPoint != null) {
                this.getActor().getPA().movePlayer(downPoint.getX(), downPoint.getY(), nodeZ - 1);
            } else {
                this.getActor().sendMessage("The way is blocked.");
            }
        } else if (this.getNodeId() == 19004 || this.getNodeId() == 20785 || this.getNodeId() == 23706 || this.getNodeId() == 23732) {
            if (this.getNodeId() != 23732 || (this.getNodeId() == 23732 && this.getNodeX() == 2350 && this.getNodeY() == 5215)) {
                this.getActor().getAttributes().getRiftInstance().nextFloor(this.getActor());
            } else {
                this.getActor().getAttributes().getRiftInstance().previousFloor(this.getActor());
            }
        } else if (this.getNodeId() == 19003 || this.getNodeId() == 20784 || this.getNodeId() == 23705) {
            this.getActor().getAttributes().getRiftInstance().previousFloor(this.getActor());
        }
        this.stop();
    }

    @Override
    protected void onUpdate() {
    }

    private Point getUpPoint() {
        if (!Region.blockedNorth(this.getNodeX(), this.getNodeY(), nodeZ)) {
            return new Point(this.getNodeX(), this.getNodeY() + 1);
        } else if (Region.blockedNorth(this.getNodeX(), this.getNodeY(), nodeZ) && !Region.blockedSouth(this.getNodeX(), this.getNodeY(), nodeZ)) {
            return new Point(this.getNodeX(), this.getNodeY() - 1);
        } else if (Region.blockedNorth(this.getNodeX(), this.getNodeY(), nodeZ)
                && Region.blockedSouth(this.getNodeX(), this.getNodeY(), nodeZ)
                && !Region.blockedEast(this.getNodeX(), this.getNodeY(), nodeZ)) {
            return new Point(this.getNodeX() - 1, this.getNodeY());
        } else if (Region.blockedNorth(this.getNodeX(), this.getNodeY(), nodeZ)
                && Region.blockedSouth(this.getNodeX(), this.getNodeY(), nodeZ)
                && Region.blockedEast(this.getNodeX(), this.getNodeY(), nodeZ)
                && !Region.blockedWest(this.getNodeX(), this.getNodeY(), nodeZ)) {
            return new Point(this.getNodeX() + 1, this.getNodeY());
        }
        return null;
    }

    private Point getDownPoint() {
        if (!Region.blockedSouth(this.getNodeX(), this.getNodeY(), nodeZ)) {
            return new Point(this.getNodeX(), this.getNodeY() - 1);
        } else if (!Region.blockedNorth(this.getNodeX(), this.getNodeY(), nodeZ) && Region.blockedSouth(this.getNodeX(), this.getNodeY(), nodeZ)) {
            return new Point(this.getNodeX(), this.getNodeY() + 1);
        } else if (Region.blockedNorth(this.getNodeX(), this.getNodeY(), nodeZ)
                && Region.blockedSouth(this.getNodeX(), this.getNodeY(), nodeZ)
                && !Region.blockedEast(this.getNodeX(), this.getNodeY(), nodeZ)) {
            return new Point(this.getNodeX() - 1, this.getNodeY());
        } else if (Region.blockedNorth(this.getNodeX(), this.getNodeY(), nodeZ)
                && Region.blockedSouth(this.getNodeX(), this.getNodeY(), nodeZ)
                && Region.blockedEast(this.getNodeX(), this.getNodeY(), nodeZ)
                && !Region.blockedWest(this.getNodeX(), this.getNodeY(), nodeZ)) {
            return new Point(this.getNodeX() + 1, this.getNodeY());
        }
        return null;
    }
}
