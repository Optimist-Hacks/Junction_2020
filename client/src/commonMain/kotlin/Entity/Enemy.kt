import com.soywiz.klock.DateTime
import com.soywiz.klock.milliseconds
import com.soywiz.klock.seconds
import com.soywiz.korau.sound.PlaybackTimes
import com.soywiz.korge.atlas.Atlas
import com.soywiz.korge.internal.KorgeInternal
import com.soywiz.korge.time.timer
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors

/** Basic Enemy which has to be killed by the Player to beat a level      */
class Enemy(x: Number, y: Number, val path: Path) : Entity(x, y, 14, 11, 1.5) {

    override var movementDirection: Direction = Direction.DOWN
    override var movingSpeed = 1.0
    private val bitMap = Resources.bitmapEnemy
    override var animationFile: Atlas = Resources.bitmapEnemy
    override var health: Int = 3
    var firstVar = true

    //initializing animations
    val down = animationFile.getSpriteAnimation("sword-down")
    val right = animationFile.getSpriteAnimation("sword-right")
    val up = animationFile.getSpriteAnimation("sword-up")
    val left = animationFile.getSpriteAnimation("sword-left")
    val hitDown = animationFile.getSpriteAnimation("hit-down")
    val hitRight = animationFile.getSpriteAnimation("hit-right")
    val hitUp = animationFile.getSpriteAnimation("hit-up")
    val hitLeft = animationFile.getSpriteAnimation("hit-left")
    val deadLeft = animationFile.getSpriteAnimation("dead-left")
    val deadRight = animationFile.getSpriteAnimation("dead-right")

    //Adding a sprite to see animations instead of just the rectangle of the Enemy
    override var sprite: Sprite = Sprite(down).apply {
        scale(skalierung)
        xy(-13 * skalierung, -28 * skalierung)
    }

    private var currentNode: Int = 0
    private var target = vec(path.nodes[currentNode].x, path.nodes[currentNode].y)

    init {
        enemies inject this
        addChild(sprite)
    }

    //Override method from Entity -> called every frame
    @KorgeInternal
    override fun update() {
        if (!dead) {
            check()
            checkDamage()
            animateSprite()
        } else {
            if (firstVar) {
                val rnd = (1..2).random()
                when (rnd) {
                    1 -> sprite.playAnimationLooped(deadLeft)
                    2 -> sprite.playAnimationLooped(deadRight)
                }
                firstVar = false
                timer(2.seconds) { GameModule.worldCamera.configuredCam.removeChild(this) }
            }
        }
    }

    fun check() {
        if (distanceToPoint(vec(this.x + this.width / 2, this.y + this.height / 2), vec(Player.x + Player.width / 2, Player.y + Player.height / 2)) < 150) {
            trackPlayer()
        } else {
            calculateNode()
            moveToNode()
        }
    }

    fun calculateNode() {
        target = vec(path.nodes[currentNode].x, path.nodes[currentNode].y)
        if (distanceToPoint(vec(this.x, this.y), target) <= path.nodeRadius) {
            currentNode++
            if (currentNode >= path.nodes.size) {
                currentNode = 0
            }
        }
    }

    fun moveToNode() {
        when {
            target.x > this.x -> {
                this.x += movingSpeed
                movementDirection = Direction.RIGHT
                rightActive = true
            }
            target.x < this.x -> {
                this.x -= movingSpeed
                movementDirection = Direction.LEFT
                leftActive = true
            }
            target.y > this.y -> {
                this.y += movingSpeed
                movementDirection = Direction.DOWN
                downActive = true
            }
            target.y < this.y -> {
                this.y -= movingSpeed
                movementDirection = Direction.UP
                upActive = true
            }
        }
    }

    /**
     * Animates the sprite, now deprecated stuff removed -> animation depends only on direction
     */
    fun animateSprite() {
        when {
            isStanding() -> sprite.stopAnimation()
            movementDirection == Direction.RIGHT -> {
                sprite.playAnimationLooped(right, 170.milliseconds)
            }
            movementDirection == Direction.LEFT -> {
                sprite.playAnimationLooped(left, 170.milliseconds)
            }
            movementDirection == Direction.DOWN -> {
                sprite.playAnimationLooped(down, 170.milliseconds)
            }
            movementDirection == Direction.UP -> {
                sprite.playAnimationLooped(up, 170.milliseconds)
            }
        }
        when {
            movementDirection == Direction.RIGHT && isHitting -> {
                sprite.playAnimation(1, hitRight, 85.milliseconds)
                //Player get damage
                if (lastHitTime + timeBetweenHit <= DateTime.nowUnixLong()) {
                    Player.getsHit = true
                    lastHitTime = DateTime.nowUnixLong()
                }
                isHitting = false
            }
            movementDirection == Direction.LEFT && isHitting -> {
                sprite.playAnimation(1, hitLeft, 85.milliseconds)
                //Player get damage
                if (lastHitTime + timeBetweenHit <= DateTime.nowUnixLong()) {
                    Player.getsHit = true
                    lastHitTime = DateTime.nowUnixLong()
                }
                isHitting = false
            }
            movementDirection == Direction.UP && isHitting -> {
                sprite.playAnimation(1, hitUp, 85.milliseconds)
                //Player get damage
                if (lastHitTime + timeBetweenHit <= DateTime.nowUnixLong()) {
                    Player.getsHit = true
                    lastHitTime = DateTime.nowUnixLong()
                }
                isHitting = false
            }
            movementDirection == Direction.DOWN && isHitting -> {
                sprite.playAnimation(1, hitDown, 85.milliseconds)
                //Player get damage
                if (lastHitTime + timeBetweenHit <= DateTime.nowUnixLong()) {
                    Player.getsHit = true
                    lastHitTime = DateTime.nowUnixLong()
                }
                isHitting = false
            }
        }
    }

    fun trackPlayer() {
        if (collidesWith(Player)) {
            downActive = false
            upActive = false
            leftActive = false
            rightActive = false

            //Attack Player
            attack(Player)
        } else {
            isHitting = false
            if (!(this.x > Player.x - 2 && this.x < Player.x + 2)) {
                when {
                    Player.x > this.x -> {
                        this.x += movingSpeed; movementDirection = Direction.RIGHT; rightActive = true
                    }
                    Player.x < this.x -> {
                        this.x -= movingSpeed; movementDirection = Direction.LEFT; leftActive = true
                    }
                }
            } else if (!(this.y > Player.y - 2 && this.y < Player.y + 2)) {
                when {
                    Player.y > this.y -> {
                        this.y += movingSpeed; movementDirection = Direction.DOWN; downActive = true
                    }
                    Player.y < this.y -> {
                        this.y -= movingSpeed; movementDirection = Direction.UP; upActive = true
                    }
                }

            }
        }
    }

    override fun checkDamage() {
        if (getsHit) {
            health -= 1
            Resources.enemyGetsHitSound.play(PlaybackTimes.ONE).apply { volume = 0.5 }
            this.colorMul = Colors.RED
            timer(0.25.seconds) { this.colorMul = Colors.WHITE }
            getsHit = false
            if (health <= 0) {
                die()
            }
        }
    }

    override fun attack(view: Entity) {
        isHitting = true
    }

    override fun die() {
        //Die and increase number of killed Enemies -> update Levelcondition
        //play sound
        Player.killedEnemies += 1
        dead = true
    }

}
