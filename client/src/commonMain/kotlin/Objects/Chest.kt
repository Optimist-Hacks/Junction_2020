import com.soywiz.klock.seconds
import com.soywiz.korau.sound.PlaybackTimes
import com.soywiz.korev.Key
import com.soywiz.korge.input.keys
import com.soywiz.korge.time.timer
import com.soywiz.korge.view.*

/**
 * Chests can include useful [Item]s the Player can use to fight against his getEnemies. They can be found
 * on the whole map and respawn after 30s -> TODO()
 */
class Chest(x: Number, y: Number, skalierung: Double, itemRelX: Number, itemRelY: Number) : GameObject(x, y, 30, 30, skalierung) {

    //basic values
    var isChestOpen = false
    var canOpen = true
    var scaleItem = 1.6
    var respawnTime = 40.seconds

    override var animationFile = Resources.bitmapChest


    //val animationGetOpen = SpriteAnimation(animationBitmap, 30, 43, 0, 0, 1, 4, 0, 0)
    val animationClosed = animationFile.getSpriteAnimation("close")
    val animationOpen = animationFile.getSpriteAnimation("open")

    var item = Item(this.x + itemRelX.toDouble(), this.y + itemRelY.toDouble(), scaleItem)

    override var sprite = Sprite(animationClosed).apply {
        xy(0, -13)
        scale(skalierung)
    }

    init {
        addChild(sprite)
        chests inject this

        //checks if the Player opens the Chest
        keys.onKeyDown {
            if (it.key == Key.E && this@Chest.collidesWith(Player) && canOpen) {
                Resources.chestSound.play(PlaybackTimes.ONE).apply { volume = 0.5 }
                Resources.itemSpanwtSound.play(PlaybackTimes.ONE).apply { volume = 0.5 }
                isChestOpen = true
                canOpen = false
            }
        }
    }


    override fun update() {
        if (isChestOpen) {
            sprite.playAnimation(animationOpen)
            item.collectable = true
            isChestOpen = false
            timer(respawnTime) {
                if (distanceToPoint(vec(this.x, this.y), vec(Player.x, Player.y)) < 400) {
                    Resources.chestSound.play(PlaybackTimes.ONE).apply { volume = 0.5 }
                }
                sprite.playAnimation(animationClosed)
                item.collectable = false
                canOpen = true
            }
        }
    }
}


