import com.soywiz.korge.annotations.KorgeExperimental
import com.soywiz.korge.scene.Module
import com.soywiz.korge.scene.Scene
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.SizeInt
import kotlin.reflect.KClass

/** The GameModule defines all parameters of the GameWindow and loads the Level Scenes
 * It manages the whole game (all Scenes, Window-parameters etc)
 */
object GameModule : Module() {
    override val icon: String? = Resources.gameIcon

    //Starting Scene loaded by the GameModule
    override val mainScene: KClass<out Scene> = IntroScene::class
    override val bgcolor: RGBA = Colors.WHITE
    override val targetFps: Double = 30.0
    override val title = "Joko's Adventure"

    //Booleans to check if a level is completed -> still in progress
    var level1Abgeschlossen = false
    var level2Abgeschlossen = false
    var level3Abgeschlossen = false
    var gameOver = false

    //very important variable -> The worldCamera manages all visible on the screen
    lateinit var worldCamera: WorldCamera

    /**Width of the [Engine]. The View will be created in 16:9. (Objects have no relative Size yet!!!)    */
    val gameSizeX: Double = 1280.0

    override var size = SizeInt(gameSizeX.toInt(), (gameSizeX / (16.0 / 9.0)).toInt())

    /** Declaration of all [LevelScenes]
     */
    @KorgeExperimental
    override suspend fun AsyncInjector.configure() {
        mapPrototype { IntroScene() }
        mapPrototype { MainMenue(Resources.background_music) }
        mapPrototype { GameOverScene() }
        mapPrototype { LevelGoalScene() }
        mapPrototype { Level_1() }
        mapPrototype { Level_2() }
        mapPrototype { Level_3() }
    }
}