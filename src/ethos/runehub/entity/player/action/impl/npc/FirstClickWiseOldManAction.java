package ethos.runehub.entity.player.action.impl.npc;

import ethos.model.players.Player;
import ethos.runehub.content.journey.JourneyStepType;
import ethos.runehub.dialog.Dialog;
import ethos.runehub.dialog.DialogOption;
import ethos.runehub.dialog.DialogSequence;
import ethos.runehub.entity.item.ItemInteractionContext;
import ethos.runehub.entity.player.action.impl.ClickNPCAction;
import ethos.runehub.skill.gathering.farming.action.AdvanceGrowthStageAction;

public class FirstClickWiseOldManAction extends ClickNPCAction {

    private static final int ID = 2108;
    private static final String NAME = "Wise Old Man";

    @Override
    public void perform() {
        player.getDH().sendDialogueSequence(new DialogSequence.DialogSequenceBuilder(player)
                .addNpcChat(NAME, ID, "Erm... I.. Uh... I'm far too busy to speak now.")
                .addOptions(this.getHelpOptions())
                .setMovementRestricted(false)
                .setActionLocked(false)
                .build());
    }

    private DialogSequence getInfoSequence() {
        return new DialogSequence.DialogSequenceBuilder(player)
                .addNpcChat(NAME, ID, "Seriously? You really don't know?", "You're in Draynor! I know, I know... a lot has changed.",
                        "After the Assasination of Guthix mountains were moved,", "Cities were reconstructed, and balance was disrupted.")
                .addNpcChat(NAME, ID, "I never thought I would see the day.", "With Port Sarim so close by, why would this be",
                        "a place for ships to dock. We used to be so quiet.", "You'll have to figure out what's going on here alone.")
                .addNpcChat(NAME, ID, "Time is of the essence and I don't have it to be", "wasted explaining everything that has taken place.",
                        "If you'd like, you can Look around here and you may", "find my journal. In it I have kept notes through the ages.")
                .addNpcChat(NAME, ID, "It should point you in the right direction", "to find the answers you so eagerly are seeking.",
                        "Oh and whatever you do, I cannot stress this enough,", "Do NOT go upstairs! that area is restricted!")
                .addNpcChat(NAME, ID, "Good luck! Now, leave me...")
                .addDialogueAction(new Dialog() {
                    @Override
                    public void onSend() {
//                        player.getAttributes().getAchievementController().completeAchievement(-9010718152367518955L);
//                        player.getAttributes().getJourneyController().checkJourney(-7567195874892689851L,1);
//                        player.getAttributes().getJourneyController().checkJourney(ID,1, JourneyStepType.DIALOG);
                    }
                })
                .build();
    }

    private DialogOption[] getHelpOptions() {
        return new DialogOption[]{
                new DialogOption("Wait, but where am I?") {
                    @Override
                    public void onAction() {
                        player.getDH().sendDialogueSequence(getInfoSequence());
                    }
                },

                new DialogOption("Nevermind.") {
                    @Override
                    public void onAction() {
                        player.getAttributes().getActiveDialogSequence().next();
                        player.getPA().closeAllWindows();
                    }
                }
        };
    }

    public FirstClickWiseOldManAction(Player player, int npcX, int npcY, int npcId, int npcIndex) {
        super(player, npcX, npcY, npcId, npcIndex);
    }
}
