import com.soywiz.korge.internal.KorgeInternal
import com.soywiz.korge.view.View
import com.soywiz.korge.view.collidesWith

/** This class checks the collision of a [view] and blocks the movement implemented in [Input]
 */
class CollisionSystem(val view: Entity, val cam: WorldCamera) {

    private val tolerance = 15

    var block_left = false
    var block_right = false
    var block_up = false
    var block_down = false

    //All objects the view can collide with

    @KorgeInternal
    fun checkCollision() {

        enemies.onEach {
            if (view.collidesWith(it)) {
                disableDirection(it)
            }
        }

        chests.onEach {
            if (view.collidesWith(it)) {
                disableDirection(it)
            }
        }

        walls.onEach {
            if (view.collidesWith(it)) {
                disableDirection(it)
            }
        }

    }

    //resolve the collision
    private fun disableDirection(other: View) {
        val viewRect = view.globalBounds
        val otherRect = other.globalBounds

        if (viewRect.right - tolerance < otherRect.left) {
            block_right = true;
        }//ALeftB
        if (viewRect.left > otherRect.right - tolerance) {
            block_left = true;
        }//ARightB
        if (viewRect.top > otherRect.bottom - tolerance) {
            block_up = true;
        }//ADownB
        if (viewRect.bottom - tolerance < otherRect.top) {
            block_down = true;
        }//AUpB

    }

    fun setAllBlocks(value: Boolean) {
        block_down = value
        block_up = value
        block_left = value
        block_right = value
    }
}
