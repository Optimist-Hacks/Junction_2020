import com.soywiz.klock.DateTime
import com.soywiz.klock.seconds
import com.soywiz.korau.sound.PlaybackTimes
import com.soywiz.korge.view.*


class Item(relX: Number, relY: Number, scale: Double) : GameObject(relX, relY, 14, 14, scale) {

    /**
     * An [Item] can be [collectable] and [collected]. The Updater is executed once per frame and checks if the item
     * is collectable(depending on the chest, which belongs to it) or even collected(setting the [Player.hasItem] to true).
     * An [Item] should only be used together with a [Chest] and should be declared there.
     */
    var collectable = false
    var collected = false
    val itemDuration = 10.seconds
    var firstTime = true

    override var animationFile = Resources.bitmapItem
    val animation = animationFile.getSpriteAnimation("animation")

    override var sprite: Sprite = Sprite(animation).apply {
        scale(scale)
        xy(0, 0)
    }

    init {
        items inject this
        addChild(sprite)
    }


    override fun update() {
        if (!collectable) sprite.alpha = 0.0
        if (collectable) {
            sprite.alpha = 1.0
            if (collidesWith(Player)) {
                collected = true
            }
        }
        if (collected) {
            collected = false
            collectable = false
            sprite.alpha = 0.0
            Player.hasItem = true
            Player.lastItemget = DateTime.nowUnixLong()
            Player.hatteItem = true
            Resources.getSwordSound.play(PlaybackTimes.ONE).apply { volume = 0.5 }
        }
    }

}




