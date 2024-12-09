package ethos.runehub.skill.combat.magic.action;

import com.google.common.base.Preconditions;
import ethos.model.players.Player;
import ethos.runehub.RunehubUtils;
import ethos.runehub.skill.combat.magic.RuneFee;
import ethos.runehub.skill.combat.magic.spell.Rune;
import ethos.runehub.skill.combat.magic.spell.RuneCache;
import ethos.runehub.skill.combat.magic.spell.Spell;
import org.runehub.api.util.math.geometry.impl.Rectangle;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModernSpellBookTeleportSkillAction extends ModernTeleportSkillAction{

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ModernSpellBookTeleportSkillAction.class);

    @Override
    public void onTick() {
        this.getActor().getSkillController().addXP(this.getSkillId(),spell.getCastXP());
        this.deleteRunes(this.getRuneItemIdsToRemove(this.getRuneIdentifiersToRemove()));
        super.onTick();
    }

    @Override
    protected void validateLevelRequirements() {
        Preconditions.checkArgument(this.getActor().getSkillController().getLevel(this.getSkillId()) >= spell.getLevel(), "You need a $"
                + RunehubUtils.getSkillName(this.getSkillId()) + " level of $"
                + spell.getLevel() + " to do this.");
    }

    @Override
    protected void validateItemRequirements() {
        Preconditions.checkArgument(Arrays.stream(spell.getRunes()).allMatch(rune ->
                        (Arrays.stream(RuneCache.getInstance().read(rune[0]).getValidEquipment()).anyMatch(equipmentId -> this.getActor().getItems().isWearingItem(equipmentId)))
                                || Arrays.stream(RuneCache.getInstance().read(rune[0]).getValidItems()).anyMatch(itemId -> (this.getActor().getItems().playerHasItem(itemId, rune[1]))))
                , "You do not have the required runes."
        );
    }

    private List<Integer> getRuneIdentifiersToRemove() {
        final List<Integer> usedRunes = new ArrayList<>(spell.getRunes().length);
        for (int i = 0; i < spell.getRunes().length; i++) {
            Rune rune = RuneCache.getInstance().read(spell.getRunes()[i][0]);
            if (Arrays.stream(rune.getValidEquipment()).noneMatch(value -> this.getActor().getItems().isWearingItem(value))) {
                usedRunes.add(rune.getRuneId().ordinal());
            }
        }
        return usedRunes;
    }

    private List<RuneFee> getRuneItemIdsToRemove(List<Integer> identifiers) {
        final List<RuneFee> usedRunes = new ArrayList<>(spell.getRunes().length);
        for (Integer identifier : identifiers) {
            Rune rune = RuneCache.getInstance().read(identifier);
            for (int j = 0; j < rune.getValidItems().length; j++) {
                if (this.getActor().getItems().playerHasItem(rune.getValidItems()[j])) {
                    int validItemId = rune.getValidItems()[j];
                    int runeIdentifier = rune.getRuneId().ordinal();
                    int amount = getAmountForRuneIdentifier(runeIdentifier);
                    final RuneFee runeFee = new RuneFee(runeIdentifier, validItemId, amount);
                    logger.debug("Adding rune fee: {}", runeFee);
                    usedRunes.add(new RuneFee(runeIdentifier, validItemId, amount));
                    break;
                }
            }
        }
        return usedRunes;
    }

    private void deleteRunes(List<RuneFee> runesToDelete) {
        for (RuneFee runeFee : runesToDelete) {
            if (this.getActor().getItems().playerHasItem(runeFee.getRuneItemId(), runeFee.getAmount())) {
                this.getActor().getItems().deleteItem2(runeFee.getRuneItemId(), runeFee.getAmount());
            }
        }
    }

    private int getAmountForRuneIdentifier(int identifier) {
        for (int i = 0; i < spell.getRunes().length; i++) {
            if (spell.getRunes()[i][0] == identifier) {
                return spell.getRunes()[i][1];
            }
        }
        return 0;
    }

    public ModernSpellBookTeleportSkillAction(Player actor, Rectangle teleportArea, Spell spell) {
        super(actor, teleportArea);
        this.spell = spell;
    }

    private final Spell spell;
}
