package ethos.runehub.action.click.item;

import ethos.model.players.Player;

public abstract class ClickConsumableItemAction extends ClickItemAction {

    protected abstract void consume();

    @Override
    protected void onActionStart() {
        System.out.println("[ClickConsumableItemAction] onActionStart: Action started");
    }

    @Override
    protected void onActionStop() {
        System.out.println("[ClickConsumableItemAction] onActionStop: Action stopped");
        stop(); // Ensure to stop the action properly
    }

    @Override
    protected void onTick() {
        System.out.println("[ClickConsumableItemAction] onTick: Ticks = " + this.getTicks());
        // Do not handle item consumption here; it should be done on action start or specific clicks
    }

    @Override
    protected void onUpdate() {
        // No specific update logic for this scenario
    }

    protected void removeItem() {
        System.out.println("[ClickConsumableItemAction] removeItem: Removing item with ID " + this.getItemId());
        // This method should not be called automatically unless explicitly required
        // Ensure this method is only called when appropriate
        this.getActor().getItems().deleteItem(this.getItemId(), 1);
    }

    public ClickConsumableItemAction(Player attachment, int ticks, int itemId, int itemAmount, int itemSlot) {
        super(attachment, ticks, itemId, itemAmount, itemSlot);
    }
}
