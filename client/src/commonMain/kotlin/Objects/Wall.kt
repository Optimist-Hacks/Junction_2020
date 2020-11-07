import com.soywiz.korim.color.Colors
import me.emig.libEmi.graphics.shapes.Rechteck


/** Simple class to represent barriers where the Player can't move through(e.g. Edges of the map, houses, getChests etc.)
 * A [wall] is a simple [Rechteck] which a transparent color
 */

class Wall(x: Number, y: Number, width: Number, height: Number)
    : Rechteck(x, y, height, width, Colors.TRANSPARENT_WHITE, Colors.TRANSPARENT_WHITE, 0) {

    init {
        alpha = 0.4
        walls.add(this)
    }
}

