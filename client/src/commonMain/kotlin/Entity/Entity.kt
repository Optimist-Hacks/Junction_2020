import com.soywiz.korge.atlas.Atlas
import com.soywiz.korge.view.Sprite


/** Basic structure for the [Player] and the [Enemy]. Because it's not necessary to have objects of type
 * [Entity] but only of the subclasses [Enemy] and [Player], this class is abstract
 *
 */
abstract class Entity(x: Number, y: Number, width: Number, height: Number, val skalierung: Double)
    : GameObject(x = x, y = y, width = width, height = height, skalierung = skalierung) {

    // Direction of Movement(Up, down, left, right) and speed/velocity
    abstract var movementDirection: Direction
    abstract var movingSpeed: Double
    abstract var health: Int

    /**Variables containing some states of the Entity:
     *  [dead] is self-explanatory, [hasItem] checks if the [Entity] has a weapon,
     *  [isHitting] checks if the Entity hits something with its weapon
     *  (Player hits with SPACE)
     */
    var dead: Boolean = false
    var hasItem: Boolean = false
    var isHitting: Boolean = false
    var getsHit: Boolean = false
    var timeBetweenHit: Long = 1000
    var lastHitTime: Long = 0


    //Booleans for pressed Buttons, used by the animations -> Playing right animation depending on the key
    var leftActive = false
    var rightActive = false
    var upActive = false
    var downActive = false

    //Checks whether no key is pressed/no movement active -> no movement. Used by animations to stop the current animation
    fun isStanding(): Boolean {
        if (leftActive || rightActive || upActive || downActive) return false
        return true
    }


    //Sprite and animationFile for SpriteAnimations
    abstract override var sprite: Sprite
    abstract override var animationFile: Atlas


    /** Main functions of every [Entity].
     * [update] is called every frame after each [init] is finished. It should contain
     * the movement of the entity and the sprite-animation based on the movement.
     * [animationTemplate] is a simple template for all animations of the Entity. Because all sprites have the same size, offset,
     * margin and bitmap it is comfortable only to give the column and row of the starting sprite for each animation
     */

    abstract override fun update()
    abstract fun attack(view: Entity)
    abstract fun checkDamage()
    abstract fun die()
}