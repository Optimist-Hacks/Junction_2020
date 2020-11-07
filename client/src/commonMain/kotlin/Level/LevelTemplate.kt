import GameModule.gameOver
import GameModule.worldCamera
import com.soywiz.klock.seconds
import com.soywiz.korau.sound.PlaybackTimes
import com.soywiz.korge.annotations.KorgeExperimental
import com.soywiz.korge.input.mouse
import com.soywiz.korge.internal.KorgeInternal
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.time.timer
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Text
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.xy
import com.soywiz.korim.color.Colors
import me.emig.libEmi.addChildren
import me.emig.libEmi.audio.Music

/** The abstract class [LevelTemplate] contains the basic structure of each Level.
 * @param map the map which should be used in this level
 * @param mapSkalierung the scale of the map
 * @param playerX the X-Coordinate of the Player (every Level MUST have a Player (automatically implemented))
 * @param playerY the Y-Coordinate of the Player
 *
 * @property loadElements must be overwritten in each LevelScene; every Object present in the level has to be created here
 * @property whenFinished is called when the Level is beaten
 */

abstract class LevelTemplate(val map: Map, val mapSkalierung: Double, val playerX: Number, val playerY: Number, val music: Music) : Scene() {

    /** Hier werden alle Objekte erstellt, die auf der Map in diesem Level erscheinen, also
     * [walls], [items], [enemies] und [sammelObjekte] -> [Player] ist bereits registriert
     */
    abstract fun loadElements()
    abstract fun whenFinished()
    abstract var killGoal: Int
    abstract var neededCollectables: Int
    open var showCoords: Boolean = false

    //main method of each Level
    @KorgeInternal
    @KorgeExperimental
    override suspend fun Container.sceneInit() {


        //reset all states -> Player has no weapon, full life etc at beginning of a Level
        resetStates()


        //initialize the camera depending on the elements loaded in loadElements() in each LevelScene
        worldCamera = WorldCamera(map, mapSkalierung, playerX, playerY) {
            loadElements()
        }

        //creates the Camera and shows all objects/map etc. on screen
        createWorldCamera()


        music.play(PlaybackTimes.INFINITE).apply { volume = 0.2 }

        //show coordinates of mouse in the top right corner
        if (showCoords) {
            val coords = Text("0", 25.0, Colors.RED).xy(GameModule.size.width - 250, 30)
            addChild(coords)
            addUpdater {
                coords.text = (mouse.currentPos.x.toInt() + worldCamera.actualX.toInt()).toString() + "  " + (mouse.currentPos.y.toInt() + GameModule.worldCamera.actualY.toInt()).toString()
            }
        }

        //adds a GUI/HUD to the Scene
        val hud = HUD(numberOfCollectables = neededCollectables, numberOfEnemies = killGoal)
        addChildren(hud.collectableIcon, hud.numCollText, hud.playerLives, hud.enemyFaceIcon, hud.numEnemiesText)

        //Ending of the scene -> TODO()
        addUpdater {
            if (GameModule.gameOver) {
                music.stop()
                timer(1.seconds) {
                    runInScope { sceneContainer.changeTo<GameOverScene>() }
                }
            }
            if (Player.collectedCollectables >= neededCollectables && Player.killedEnemies >= killGoal) {
                timer(1.seconds) {
                    whenFinished()
                    runInScope { sceneContainer.changeTo<LevelGoalScene>() }
                }
            }
        }

    }

    override suspend fun sceneBeforeLeaving() {
        music.stop()
        worldCamera.configuredCam.removeChildren()
    }

    private fun resetStates() {
        //Reset Player states, collected getItems, etc
        gameOver = false
        Player.hasItem = false
        Player.hatteItem = false
        Player.removeAllComponents()
        Player.health = 5
        Player.dead = false
        Player.collectedCollectables = 0
        Player.killedEnemies = 0
        Player.firstTime = true
        items.clear()
        enemies.clear()
        chests.clear()
        walls.clear()
    }
}
