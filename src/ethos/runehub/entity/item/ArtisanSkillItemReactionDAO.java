package ethos.runehub.entity.item;


import ethos.runehub.RunehubConstants;
import ethos.runehub.skill.artisan.ArtisanSkillItemReaction;
import org.runehub.api.io.data.impl.beta.BetaAbstractDataAcessObject;

public class ArtisanSkillItemReactionDAO extends BetaAbstractDataAcessObject<ArtisanSkillItemReaction> {

    private static ArtisanSkillItemReactionDAO instance = null;

    public static ArtisanSkillItemReactionDAO getInstance() {
        if (instance == null)
            instance = new ArtisanSkillItemReactionDAO();
        return instance;
    }

    private ArtisanSkillItemReactionDAO() {
        super(RunehubConstants.ITEM_INTERACTION_DB, ArtisanSkillItemReaction.class);
    }
}
