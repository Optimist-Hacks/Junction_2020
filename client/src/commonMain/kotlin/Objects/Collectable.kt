import com.soywiz.korge.view.*

class Collectable(x: Number, y: Number, scale: Double)
    : GameObject(x, y, 552, 536, scale) {

    var collectable = true
    var collected = false
    override var animationFile = Resources.bitmapCollectable
    val animation = animationFile.getSpriteAnimation("animation")

    override var sprite: Sprite = Sprite(animation).apply {
        scale(scale)
        xy(0, 0)
    }

    init {
        collectables inject this
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
            Player.getCollectable()
        }
    }

}