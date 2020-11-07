import com.soywiz.korev.Key
import com.soywiz.korge.component.UpdateComponent
import com.soywiz.korge.internal.KorgeInternal

/** this class handles the input for the [view] on the [cam]
 * It has to be defined in each GameObject which uses it:
 *         addComponent(Input(this, GameModule.worldCamera))
 */
class Input(override val view: Entity, val cam: WorldCamera) : UpdateComponent {

    //Booleans to check if the keys are pressed
    var left_pressed = false
    var right_pressed = false
    var up_pressed = false
    var down_pressed = false

    var firstTimeStep = true
    var speed = view.movingSpeed / 15

    var actualX = view.x
    var actualY = view.y

    //Adding a CollisionSystem
    val collisionSystem = CollisionSystem(view, cam)

    //Called every Frame
    @KorgeInternal
    override fun update(ms: Double) {
        if (!view.dead) {
            if (isKeyPressed()) {
                getDistance(ms)
                makemove()
                collisionSystem.checkCollision()
            }
            updateView()
        }
    }

    //checks if a key is pressed or released
    @KorgeInternal
    private fun isKeyPressed(): Boolean {
        cam.keyPressed {
            when (it) {
                Key.LEFT -> {
                    left_pressed = true
                    view.movementDirection = Direction.LEFT
                }
                Key.RIGHT -> {
                    right_pressed = true
                    view.movementDirection = Direction.RIGHT
                }
                Key.UP -> {
                    up_pressed = true
                    view.movementDirection = Direction.UP
                }
                Key.DOWN -> {
                    down_pressed = true
                    view.movementDirection = Direction.DOWN
                }
                Key.SPACE -> {
                    if (view.hasItem) {
                        view.isHitting = true
                        collisionSystem.setAllBlocks(true)
                    }
                }
                else -> {
                }
            }
        }
        cam.keyReleased {
            when (it) {
                Key.LEFT -> {
                    left_pressed = false
                }
                Key.RIGHT -> {
                    right_pressed = false
                }
                Key.UP -> {
                    up_pressed = false
                }
                Key.DOWN -> {
                    down_pressed = false
                }
                Key.SPACE -> {
                    if (view.isHitting) {
                        view.isHitting = false
                        collisionSystem.setAllBlocks(false)
                        collisionSystem.checkCollision()
                    }
                }
                else -> {
                }
            }
        }
        //blocks the keys if a collision is detected by the collisionSystem
        block()
        if (up_pressed || left_pressed || down_pressed || right_pressed) return true
        return false
    }

    private fun block() {
        if (collisionSystem.block_up) up_pressed = false
        if (collisionSystem.block_left) left_pressed = false
        if (collisionSystem.block_down) down_pressed = false
        if (collisionSystem.block_right) right_pressed = false

        if (down_pressed || left_pressed || right_pressed) collisionSystem.block_up = false
        if (up_pressed || left_pressed || right_pressed) collisionSystem.block_down = false
        if (left_pressed || up_pressed || down_pressed) collisionSystem.block_right = false
        if (right_pressed || up_pressed || down_pressed) collisionSystem.block_left = false
    }

    //updates the view -> needed for right animation
    private fun updateView() {
        view.leftActive = left_pressed
        view.rightActive = right_pressed
        view.upActive = up_pressed
        view.downActive = down_pressed
    }

    private fun getDistance(ms: Double) {
        if (up_pressed) {
            actualY -= speed * ms
        }
        if (down_pressed) {
            actualY += speed * ms
        }
        if (left_pressed) {
            actualX -= speed * ms
        }
        if (right_pressed) {
            actualX += speed * ms
        }
    }

    //new position
    private fun makemove() {
        view.x = actualX
        view.y = actualY
    }


}

