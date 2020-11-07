import com.soywiz.korge.annotations.KorgeExperimental
import com.soywiz.korge.view.Image
import com.soywiz.korge.view.Text
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.xy
import com.soywiz.korim.color.Colors
import me.emig.libEmi.graphics.bilder.Bild

@KorgeExperimental
class HUD(val numberOfEnemies: Int, val numberOfCollectables: Int) {

    val playerLives = Image(Resources.bitmapHUDLives[Player.health]).apply {
        scale = 2.0
        xy(10, 10)
    }
    val collectableIcon = Bild(10, 70, Resources.bitmapCollectableHUD, scale = 0.1)
    val textFont = Resources.bitmapFont
    val numCollText = Text(Player.collectedCollectables.toString(), 30.0, Colors.WHITE, textFont).apply { xy(75, 95) }
    val enemyFaceIcon = Bild(10, 140, Resources.enemyFaceHUD, scale = 2.0)
    val numEnemiesText = Text(Player.killedEnemies.toString(), 30.0, Colors.WHITE, textFont).apply { xy(75, 155) }

    init {
        playerLives.addUpdater {
            this.bitmap = Resources.bitmapHUDLives[Player.health]
        }
        numCollText.addUpdater {
            text = Player.collectedCollectables.toString() + "/" + numberOfCollectables.toString()
        }
        numEnemiesText.addUpdater {
            text = Player.killedEnemies.toString() + "/" + numberOfEnemies.toString()
        }
    }

}