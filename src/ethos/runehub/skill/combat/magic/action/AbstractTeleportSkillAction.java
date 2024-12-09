package ethos.runehub.skill.combat.magic.action;

import com.google.common.base.Preconditions;
import ethos.Config;
import ethos.model.players.Player;
import ethos.runehub.skill.SkillAction;
import ethos.runehub.skill.combat.magic.spell.Rune;
import ethos.runehub.skill.combat.magic.spell.RuneCache;
import ethos.runehub.skill.combat.magic.spell.Spell;
import ethos.util.PreconditionUtils;
import org.runehub.api.util.SkillDictionary;
import org.runehub.api.util.math.geometry.Point;
import org.runehub.api.util.math.geometry.impl.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractTeleportSkillAction extends SkillAction {

    @Override
    public void validateWorldRequirements() {
        Preconditions.checkArgument(PreconditionUtils.isFalse(this.getActor().getAttributes().getActiveInstance() != null &&
                this.getActor().getAttributes().getActiveInstance().getArea().contains(new Point(this.getActor().absX, this.getActor().absY))),"You can not teleport out of this instance.");
        Preconditions.checkArgument(PreconditionUtils.isFalse(this.getActor().getAttributes().isActionLocked()), "Please finish what you are doing.");
        Preconditions.checkArgument(PreconditionUtils.isFalse(this.getActor().isInHouse),"Please use the house settings to leave your house");
        Preconditions.checkArgument(PreconditionUtils.isFalse(this.getActor().teleTimer > 0),"A magic force stops you from teleporting.");
        Preconditions.checkArgument(PreconditionUtils.isFalse(this.getActor().wildLevel > Config.NO_TELEPORT_WILD_LEVEL),"You can't teleport above " + Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
    }

    public AbstractTeleportSkillAction(Player actor, int ticks, Rectangle teleportArea) {
        super(actor, SkillDictionary.Skill.MAGIC.getId(), ticks);
        this.teleportArea = teleportArea;
    }

    protected final Rectangle teleportArea;
}
