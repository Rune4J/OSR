package ethos.runehub.skill.gathering.farming.action;

import com.google.common.base.Preconditions;
import ethos.model.players.Player;
import ethos.rune4j.model.dto.skill.farming.PatchContext;
import ethos.runehub.RunehubUtils;
import ethos.runehub.entity.item.ItemInteractionContext;
import ethos.runehub.skill.SkillAction;
import ethos.util.PreconditionUtils;
import org.runehub.api.util.SkillDictionary;

public class WaterPatchAction extends SkillAction {

    private static final int FULL_WATERING_CAN = 5340;
    private static final int EMPTY_WATERING_CAN = 5331;

    @Override
    protected void onActionStart() {
        this.getActor().startAnimation(2293);
    }

    @Override
    protected void onActionStop() {

    }

    @Override
    protected void onTick() {
        this.getActor().getItems().deleteItem(interactionContext.getUsedId(),1);
        this.getActor().getItems().addOrDropItem(getNextWateringCanState(interactionContext.getUsedId()),1);

        patchContext.setWateredState(1);

        this.getActor().getSkillController().getFarming().savePatchContext(patchContext);
        this.getActor().getSkillController().getFarming().updateFarm(RunehubUtils.getRegionId(interactionContext.getX(),interactionContext.getY()));
        this.stop();
    }

    @Override
    protected void onUpdate() {

    }

    @Override
    protected void validateInventory() {

    }

    @Override
    protected void validateLevelRequirements() {
        Preconditions.checkArgument(PreconditionUtils.isTrue(this.getActor().getSkillController().getLevel(this.getSkillId()) >= 1),
                "You need a ?"
                        + SkillDictionary.getSkillNameFromId(this.getSkillId())
                        + " level of at least #"
                        + 1
                        + " to do this.");
    }

    @Override
    protected void validateItemRequirements() {
        Preconditions.checkArgument( // We are checking the ID of each watering can state 8 - 1
                (
                        this.getActor().getItems().playerHasItem(5340)
                        || this.getActor().getItems().playerHasItem(5333)
                        || this.getActor().getItems().playerHasItem(5334)
                        || this.getActor().getItems().playerHasItem(5335)
                        || this.getActor().getItems().playerHasItem(5336)
                        || this.getActor().getItems().playerHasItem(5337)
                        || this.getActor().getItems().playerHasItem(5338)
                        || this.getActor().getItems().playerHasItem(5339)
                ),
                "You need a watering can");
    }

    @Override
    protected void validateWorldRequirements() {
        Preconditions.checkArgument(PreconditionUtils.isFalse((patchContext.getWateredState() == 1 && patchContext.getDiseasedState() == 1)),"This patch is dead.");
        Preconditions.checkArgument(patchContext.getDiseasedState() == 0,"You should cure this plant before watering it.");
        Preconditions.checkArgument(patchContext.getPatchLocationId() == 8 || patchContext.getPatchLocationId() == 0,"You cannot water this patch.");
        Preconditions.checkArgument(patchContext.getCurrentGrowthStage() != patchContext.getMaturityStage(),"This patch is already fully grown.");
        Preconditions.checkArgument(patchContext.getWateredState() != 1,"This patch has been watered recently.");
        Preconditions.checkArgument(patchContext.getOccupiedById() != 0,"This patch is empty.");
    }

    @Override
    protected void updateAnimation() {

    }

    @Override
    protected void addItems(int id, int amount) {

    }

    private int getNextWateringCanState(int currentWateringCanState) {
        return currentWateringCanState == 5333 ? EMPTY_WATERING_CAN : currentWateringCanState - 1;
    }

    public WaterPatchAction(Player actor, ItemInteractionContext interactionContext, PatchContext patchContext) {
        super(actor, SkillDictionary.Skill.FARMING.getId(),6);
        this.interactionContext = interactionContext;
        this.patchContext = patchContext;
    }

    private final ItemInteractionContext interactionContext;
    private final PatchContext patchContext;
}
