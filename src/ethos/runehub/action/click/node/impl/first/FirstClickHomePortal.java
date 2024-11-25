package ethos.runehub.action.click.node.impl.first;

import ethos.model.players.Player;
import ethos.runehub.action.click.node.ClickNodeAction;
import ethos.runehub.dialog.DialogOption;
import ethos.runehub.dialog.DialogSequence;

public class FirstClickHomePortal extends ClickNodeAction {

    private static final int REQUIRED_AIR_RUNES = 30;
    private static final int REQUIRED_BODY_RUNES = 20;
    private static final int AIR_RUNE_ITEM_ID = 556;  // Replace with the correct ID
    private static final int BODY_RUNE_ITEM_ID = 559;  // Replace with the correct ID
    private static final int TELEPORT_INTERFACE_ID = 31000;

    @Override
    protected void onActionStart() {
        this.getActor().getDH().sendDialogueSequence(new DialogSequence.DialogSequenceBuilder(this.getActor())
                .addOptions(getPaymentOptions())
                .build());
    }

    @Override
    protected void onActionStop() {
        // Any cleanup logic can go here
    }

    @Override
    protected void onTick() {
        this.stop();  // Stops the action after the initial interaction
    }

    @Override
    protected void onUpdate() {
        // If you need to handle updates, add logic here
    }

    private DialogOption[] getPaymentOptions() {
        return new DialogOption[]{
                new DialogOption("Charge Portal (30x Air Runes & 20x Body Runes)") {
                    @Override
                    public void onAction() {
                        if (getActor().getItems().playerHasItem(AIR_RUNE_ITEM_ID, REQUIRED_AIR_RUNES) &&
                                getActor().getItems().playerHasItem(BODY_RUNE_ITEM_ID, REQUIRED_BODY_RUNES)) {

                            getActor().getItems().deleteItem(AIR_RUNE_ITEM_ID, REQUIRED_AIR_RUNES);  // Remove Air Runes
                            getActor().getItems().deleteItem(BODY_RUNE_ITEM_ID, REQUIRED_BODY_RUNES);  // Remove Body Runes
                            getActor().getPA().showInterface(TELEPORT_INTERFACE_ID);  // Show teleport interface

                        } else {
                            getActor().getPA().closeAllWindows();
                            getActor().sendMessage("You do not have enough resources to activate this portal.");
                        }
                    }
                },
                new DialogOption("Nevermind") {
                    @Override
                    public void onAction() {
                        getActor().getPA().closeAllWindows();  // Close the dialogue window
                    }
                }
        };
    }

    public FirstClickHomePortal(Player attachment, int nodeId, int nodeX, int nodeY) {
        super(attachment, 1, nodeId, nodeX, nodeY);  // Initialize the action
    }
}
