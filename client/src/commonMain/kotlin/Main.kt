import com.soywiz.korge.Korge
import com.soywiz.korge.internal.KorgeInternal
import com.soywiz.korio.async.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


/** basic mutable lists, can be used to execute functions to all Objects of a special type -> Used in [gameCamera]
 */
val gameObjects = mutableListOf<GameObject>()
val items = mutableListOf<Item>()
val chests = mutableListOf<Chest>()
val walls = mutableListOf<Wall>()
val enemies = mutableListOf<Enemy>()
val collectables = mutableListOf<Collectable>()


/** Main method, which starts the game and loads the [GameModule]
 */
@KorgeInternal
suspend fun main() = Korge(Korge.Config(GameModule))


//Creates a Coroutine -> needed for playing music and sounds
fun runInScope(code: suspend () -> Unit) {
    CoroutineScope(Dispatchers.Default).launch {
        code()
    }
}

//Simple functions to create objects on screen -> used in LevelScenes.kt to load the elements in the current level
fun wall(x: Number, y: Number, width: Number, height: Number) = Wall(x, y, width, height)

fun item(relX: Number, relY: Number, scale: Double = 1.0) = Item(relX, relY, scale)

fun chest(x: Number, y: Number, skalierung: Double = 1.0, itemRelX: Number, itemRelY: Number) = Chest(x, y, skalierung, itemRelX, itemRelY)

fun enemy(x: Number, y: Number, path: Path) = Enemy(x, y, path)

fun path(nodes: Array<PathNode>) = Path(nodes)

fun collectable(x: Number, y: Number, scale: Double = 0.05) = Collectable(x, y, scale)


//function which makes adding an object to a list more convenient. Uses a generic Type which has to be a GameObject
infix fun <T : GameObject> MutableList<T>.inject(element: T) {
    this.add(element)
    gameObjects.add(element)
}





