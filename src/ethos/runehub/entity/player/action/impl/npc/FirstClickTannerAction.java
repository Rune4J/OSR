package ethos.runehub.entity.player.action.impl.npc;

import ethos.model.players.Player;
import ethos.runehub.dialog.DialogOption;
import ethos.runehub.dialog.DialogSequence;
import ethos.runehub.entity.player.action.FirstClickNPCActionFactory;
import ethos.runehub.entity.player.action.impl.ClickNPCAction;
import ethos.runehub.entity.player.action.impl.ClickOpenShopAction;
import ethos.runehub.skill.artisan.crafting.TanningItem;
import ethos.runehub.ui.TanningUI;

public class FirstClickTannerAction extends ClickNPCAction {

    final String NAME = "Tanner";
    final int ID = 5809;

    @Override
    public void perform() {
        player.getDH().sendDialogueSequence(talkTo());
    }

    private DialogSequence talkTo() {
        return new DialogSequence.DialogSequenceBuilder(player)
                .addNpcChat(NAME,ID,
                        "Aye my name is Tanner",
                        "and I'm a tanner... What are you after?",
                        "I can tan your hides for a price.",
                        "I even offer supplies if you're in need."
                        )
                .addOptions(getMainOptions())
                .build();
    }

    private DialogOption[] getMainOptions() {
        return new DialogOption[]{
                new DialogOption("I'd like to tan some hides") {
                    @Override
                    public void onAction() {
                        player.sendUI(new TanningUI(player,
                                new TanningItem[]{
                                        new TanningItem(1739, 1741, 5),
                                        new TanningItem(1739, 1743, 8),
                                        new TanningItem(6287, 6289, 12),
                                        new TanningItem(6287, 6289, 12),
                                        new TanningItem(1753, 1745, 15),
                                        new TanningItem(1751, 2505, 20),
                                        new TanningItem(1749, 2507, 25),
                                        new TanningItem(1747, 2509, 35)
                                }
                        ));
                    }
                },
                new DialogOption("Show me your supplies") {
                    @Override
                    public void onAction() {
                        player.getAttributes().getActionController().submit(new ClickOpenShopAction(player, player.absX, player.absY, ID, npcIndex));
                    }
                },
                new DialogOption("Nevermind") {
                    @Override
                    public void onAction() {
                        player.getAttributes().getActiveDialogSequence().next();
                        player.getPA().closeAllWindows();
                    }
                }
        };
    }

    public FirstClickTannerAction(Player player, int npcX, int npcY, int npcId, int npcIndex) {
        super(player, npcX, npcY, npcId, npcIndex);
    }
}
