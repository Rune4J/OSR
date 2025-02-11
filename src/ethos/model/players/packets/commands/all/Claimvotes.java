package ethos.model.players.packets.commands.all;

import java.util.Optional;

import ethos.model.players.Player;
import ethos.model.players.packets.commands.Command;

/**
 * Opens the vote page in the default web browser.
 *
 * @author Emiel
 */
public class Claimvotes extends Command {

    String lastAuth = "";


    @Override
    public void execute(Player player, String input) {

        String[] args = input.split(" ");
        if (args.length == 1) {
            player.sendMessage("Please use [::reward 1 amount], or [::reward 1 all].");
            player.sendMessage("1 Vote ticket is 1 Vote point.");
            return;
        }
        final String playerName = player.playerName;
        final String id = args[0];
        final String amount = args[1];
        com.everythingrs.vote.Vote.service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    com.everythingrs.vote.Vote[] reward = com.everythingrs.vote.Vote.reward("zv0KjfltVLgXGhSvTkHUbxmAttWqVDTV3DzCvXZBGsr6dHzhI0xJuKeQR30Q1xHHbhP4bsbw",
                            playerName, id, amount);
                    if (reward[0].message != null) {
                        player.sendMessage(reward[0].message);
                        return;
                    }
                    player.getItems().addItemUnderAnyCircumstance(reward[0].reward_id, reward[0].give_amount);
                    player.sendMessage(
                            "@cr10@Thank you for voting! You now have " + reward[0].vote_points + " vote points.");
                    player.bankCharges += 1;
                    player.sendMessage("@red@You now have @dre@"+player.bankCharges+" @red@bank charges.");
                    player.bonusXpTime += 600;
                    player.relicUpgradeMedium();
                } catch (Exception e) {
                    player.sendMessage("Api Services are currently offline. Please check back shortly");
                    e.printStackTrace();
                }
            }

        });
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.of("Claims your vote from ::vote");
    }

    @Override
    public Optional<String> getParameter() {
        return Optional.of("id# amount#");
    }
}

