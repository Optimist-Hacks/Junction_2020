import org.jbox2d.common.Vec2
import kotlin.math.sqrt

class Path(val nodes: Array<PathNode>) {

    val nodeRadius: Int = 10

    init {

    }

}

data class PathNode(var x: Number = 0.0, var y: Number = 0.0)

fun distanceToPoint(a: Vec2, b: Vec2): Float {
    return sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y))
}

fun vec(x: Number, y: Number) = Vec2(x.toFloat(), y.toFloat())

