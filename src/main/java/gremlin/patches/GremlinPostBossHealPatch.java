package gremlin.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gremlin.characters.GremlinCharacter;

@SpirePatch(
        clz= AbstractDungeon.class,
        method="dungeonTransitionSetup"
)
public class GremlinPostBossHealPatch {
    @SpireInsertPatch(
            rloc=27
    )
    public static SpireReturn Insert() {
        if (AbstractDungeon.player instanceof GremlinCharacter) {
            float multiplier = 1.0f;
            if (AbstractDungeon.ascensionLevel >= 5) {
                multiplier = .75f;
            }

            // Only rez 1
            // a4 and under, full rez
            // a5 and up, rez 2
            if (((GremlinCharacter) AbstractDungeon.player).canRez()) {
                if (AbstractDungeon.ascensionLevel < 5) {
                    ((GremlinCharacter) AbstractDungeon.player).resurrect(multiplier);
                    ((GremlinCharacter) AbstractDungeon.player).resurrect(multiplier);
                    ((GremlinCharacter) AbstractDungeon.player).resurrect(multiplier);
                }
                ((GremlinCharacter) AbstractDungeon.player).resurrect(multiplier);
                ((GremlinCharacter) AbstractDungeon.player).resurrect(multiplier);
            }

            // Heal all
            ((GremlinCharacter)(AbstractDungeon.player)).mobState.postBossHeal(AbstractDungeon.player.maxHealth, multiplier);
        }
        return SpireReturn.Continue();
    }
}
