import com.soywiz.klock.DateTime
import com.soywiz.klock.milliseconds
import com.soywiz.klock.seconds
import com.soywiz.korau.sound.PlaybackTimes
import com.soywiz.korge.annotations.KorgeExperimental
import com.soywiz.korge.atlas.Atlas
import com.soywiz.korge.time.timer
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors

/**
 * Player, playable character
 */
object Player : Entity(100, 100, 14, 11, 1.5) {

    override var animationFile: Atlas = Resources.bitmapPlayer

    val animationDown = animationFile.getSpriteAnimation("go-down")
    val animationUp = animationFile.getSpriteAnimation("go-up")
    val animationLeft = animationFile.getSpriteAnimation("go-left")
    val animationRight = animationFile.getSpriteAnimation("go-right")
    val animationDownSword = animationFile.getSpriteAnimation("sword-down")
    val animationUpSword = animationFile.getSpriteAnimation("sword-up")
    val animationLeftSword = animationFile.getSpriteAnimation("sword-left")
    val animationRightSword = animationFile.getSpriteAnimation("sword-right")
    val animationHitDown = animationFile.getSpriteAnimation("hit-down")
    val animationHitUp = animationFile.getSpriteAnimation("hit-up")
    val animationHitLeft = animationFile.getSpriteAnimation("hit-left")
    val animationHitRight = animationFile.getSpriteAnimation("hit-right")
    val animationDeadLeft = animationFile.getSpriteAnimation("dead-left")
    val animationDeadRight = animationFile.getSpriteAnimation("dead-right")

    //initializing the sprite to show animations on screen
    override var sprite: Sprite = Sprite(animationDown).apply {
        scale(skalierung)
        xy(-13 * skalierung, -28 * skalierung)
    }

    override var movementDirection = Direction.DOWN
    override var movingSpeed = 2.0
    override var health: Int = 5

    var collectedCollectables: Int = 0
    var killedEnemies: Int = 0

    var firstTime = true
    var itemDuration: Long = 30000
    var lastItemget: Long = 0
    var hatteItem = false

    /** Main method of [Player]. Here you can configure the x and y Coordinate of the Player.
     */
    @KorgeExperimental
    operator fun invoke(playerX: Number, playerY: Number): Entity {
        this.x = playerX.toDouble()
        this.y = playerY.toDouble()
        addChild(sprite)

        //Add an Input and a CollisionSystem to the Player
        addComponent(Input(this, GameModule.worldCamera))
        return this
    }

    //update-method from Entity -> called every frame
    override fun update() {
        if (!dead) {
            animateSprite()
            checkDamage()
            restartTimer()
            enemies.forEach {
                if (distanceToPoint(vec(this.x, this.y), vec(it.x, it.y)) <= 30 && this.isHitting) attack(it)
            }
        } else {
            if (firstTime) {
                val rnd = (1..2).random()
                when (rnd) {
                    1 -> sprite.playAnimationLooped(animationDeadLeft)
                    2 -> sprite.playAnimationLooped(animationDeadRight)
                }
                firstTime = false
                timer(2.seconds) { GameModule.gameOver = true }
            }
        }
    }


    /** Animates the [sprite] by checking [rightActive], [leftActive], [downActive] and [upActive].
     * Checks also if the Player has a weapon and if he uses it for hitting
     */
    fun animateSprite() {
        if (hasItem) {
            when {
                isStanding() -> {
                    sprite.stopAnimation()
                }
                rightActive -> {
                    sprite.playAnimationLooped(animationRightSword, 170.milliseconds)
                }
                leftActive -> {
                    sprite.playAnimationLooped(animationLeftSword, 170.milliseconds)
                }
                downActive -> {
                    sprite.playAnimationLooped(animationDownSword, 170.milliseconds)
                }
                upActive -> {
                    sprite.playAnimationLooped(animationUpSword, 170.milliseconds)
                }
            }
        } else {
            when {
                isStanding() -> {
                    sprite.stopAnimation()
                }
                rightActive -> {
                    sprite.playAnimationLooped(animationRight, 170.milliseconds)
                }
                leftActive -> {
                    sprite.playAnimationLooped(animationLeft, 170.milliseconds)
                }
                downActive -> {
                    sprite.playAnimationLooped(animationDown, 170.milliseconds)
                }
                upActive -> {
                    sprite.playAnimationLooped(animationUp, 170.milliseconds)
                }
            }
        }
        when {
            movementDirection == Direction.RIGHT && isHitting -> {
                sprite.playAnimationForDuration(200.milliseconds, animationHitRight, 50.milliseconds)
            }
            movementDirection == Direction.LEFT && isHitting -> {
                sprite.playAnimationForDuration(200.milliseconds, animationHitLeft, 50.milliseconds)
            }
            movementDirection == Direction.UP && isHitting -> {
                sprite.playAnimationForDuration(200.milliseconds, animationHitUp, 50.milliseconds)
            }
            movementDirection == Direction.DOWN && isHitting -> {
                sprite.playAnimationForDuration(200.milliseconds, animationHitDown, 50.milliseconds)
            }
        }
    }

    fun getCollectable() {
        Resources.getCollectableSound.play(PlaybackTimes.ONE).apply { volume = 0.5 }
        collectedCollectables += 1
        if (health < 5) health += 1
    }

    override fun checkDamage() {
        if (getsHit) {
            health -= 1
            if (health > 0) Resources.playerGetsAttackedSound.play(PlaybackTimes.ONE).apply { volume = 0.5 }
            this.colorMul = Colors.RED
            timer(0.25.seconds) { this.colorMul = Colors.WHITE }
            getsHit = false
            if (health <= 0) {
                die()
            }
        }
    }

    override fun die() {
        //GameOver!!
        Resources.playerDiesSound.play(PlaybackTimes.ONE).apply { volume = 0.5 }
        dead = true
    }

    override fun attack(view: Entity) {
        if (lastHitTime + timeBetweenHit <= DateTime.nowUnixLong()) {
            Resources.playerTrifftSound.play(PlaybackTimes.ONE).apply { volume = 0.5 }
            view.getsHit = true
            when (movementDirection) {
                Direction.UP -> view.y -= 50
                Direction.DOWN -> view.y += 50
                Direction.LEFT -> view.x -= 50
                Direction.RIGHT -> view.x += 50
            }
            lastHitTime = DateTime.nowUnixLong()
        }
    }

    fun restartTimer() {
        if (DateTime.nowUnixLong() > itemDuration + lastItemget) {
            if (hatteItem) {
                hatteItem = false
                Resources.itemBreaksSound.play(PlaybackTimes.ONE).apply { volume = 0.5 }
                hasItem = false
            }
        }
    }

}
