import com.soywiz.korge.atlas.Atlas
import com.soywiz.korge.view.Sprite
import com.soywiz.korim.color.Colors
import me.emig.libEmi.graphics.shapes.Rechteck

/** Basic class for all GameObjects which are shown on screen -> Every Object is a simple [Rechteck]
 */
abstract class GameObject(x: Number, y: Number, width: Number, height: Number, skalierung: Double)
    : Rechteck(x, y, height.toDouble() * skalierung, width.toDouble() * skalierung, Colors.TRANSPARENT_WHITE, Colors.TRANSPARENT_WHITE, 0) {


    /** Sprite and Sprite-animationFile is necessary for every object
     * The file is of type Xml-spritesheet -> Atlas-Object
     */
    abstract var sprite: Sprite


    /** Every Object has its own animationFile. In it there are all the animations the object has.
     * You can get an animation with
     *
     *     val exampleAnimation = animationFile.getSpriteAnimation(PREFIX)
     *
     * The value PREFIX is necessary to specify which animation you want to load.
     * Normal objects which are no entities have only one animation(z.B. item, collectable) or two animations (z.B. chest).
     * To load them you have to set the PREFIX to:
     *      "animation" -> for item and collectable which only have one animation
     *      "close" or "open" -> for chest animation open and close -> chest has 2 animations
     *
     * If you have an Entity you can set PREFIX to:
     *      "go-left" or "go-right" or "go-up" or "go-down" -> walking animations without sword (Enemy doesn't have these)
     *      "sword-left" or "sword-right" or "sword-up" or "sword-down" -> walking animations with sword
     *      "hit-left" or "hit-right" or "hit-up" or "hit-down" -> hitting animations
     *      "dead-left" or "dead-right" -> dying animations
     *
     * After you specified which animation to load you can create a sprite to show them on screen
     *
     *      override var sprite = Sprite()
     *
     * Then you can play an animation on the Sprite with
     *
     *      exampleSprite.playAnimation(exampleAnimation)
     */

    abstract var animationFile: Atlas


    //Called every Frame -> has to be overridden in subclasses
    abstract fun update()

}
