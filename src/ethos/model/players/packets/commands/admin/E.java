package ethos.model.players.packets.commands.admin;

import ethos.Server;
import ethos.clip.Region;
import ethos.model.players.Player;
import ethos.model.players.packets.commands.Command;
import ethos.model.players.packets.commands.owner.Object;
import ethos.runehub.RunehubUtils;
import ethos.runehub.skill.artisan.crafting.TanningItem;
import ethos.runehub.ui.TanningUI;
import org.runehub.api.util.math.geometry.Point;
import org.runehub.api.util.math.geometry.impl.Rectangle;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Force the player to perform a given emote.
 *
 * @author Emiel
 * <p>
 * And log if args extend to 2.
 * @author Matt
 */
public class E extends Command {


    @Override
    public void execute(Player c, String input) {
        String[] args = input.split(" ");

        c.sendUI(new TanningUI(c,
                new TanningItem[]{
                        new TanningItem(1739, 1741, 1),
                        new TanningItem(1739, 1743, 3),
                        new TanningItem(6287, 6289, 5),
                        new TanningItem(6287, 6289, 7),
                        new TanningItem(1753, 1745, 9),
                        new TanningItem(1751, 2505, 11),
                        new TanningItem(1749, 2507, 13),
                        new TanningItem(1747, 2509, 15)
                }
                ));
    }
}
