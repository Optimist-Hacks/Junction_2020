import com.soywiz.korev.Key
import com.soywiz.korge.annotations.KorgeExperimental
import com.soywiz.korge.component.docking.sortChildrenBy
import com.soywiz.korge.input.keys
import com.soywiz.korge.internal.KorgeInternal
import com.soywiz.korge.tiled.TiledMap
import com.soywiz.korge.tiled.tiledMapView
import com.soywiz.korge.view.*
import com.soywiz.korma.geom.SizeInt

/** Class which controlls the screen and every element on it. Called at beginning of each level
 * @param map the map which needs to be shown in the current level
 * @param mapScale the scale of the current map
 * @param playerX x-Coordinate of the Player
 * @param playerY y-Coordinate of the Player
 * @param initGameObjects function which is overwritten in each levelScene -> loads all Objects needed for the level
 */
class WorldCamera(val map: Map, val mapScale: Double, val playerX: Number, val playerY: Number, val initGameObjects: () -> Unit) {

    //Camera which gets modified and returned at the end
    @KorgeExperimental
    @KorgeInternal
    val configuredCam = Camera()

    //basic values to load the correct map and set up the camera movement(following Player)
    val tiledMap: TiledMap = when (map) {
        Map.LEVEL_1 -> Resources.mapLevel1
        Map.LEVEL_2 -> Resources.mapLevel2
        else -> Resources.mapLevel3
    }

    //Controls the camera following the Player
    val cameraSize: SizeInt = SizeInt(GameModule.size.width, GameModule.size.height)
    val worldSize = SizeInt((tiledMap.pixelWidth * mapScale).toInt(), (tiledMap.pixelHeight * mapScale).toInt())
    val offsetMaxX = (worldSize.width - cameraSize.width).toDouble()
    val offsetMaxY = (worldSize.height - cameraSize.height).toDouble()
    val offsetMinX = 0.0
    val offsetMinY = 0.0
    var actualX = 0.0
    var actualY = 0.0

    //Initializing of the configuredCam
    @KorgeInternal
    @KorgeExperimental
    fun loadconfiguredCam(): Camera {
        configuredCam.apply {
            initGameObjects()
            tiledMapView(tiledMap) {
                scale = mapScale
            }
            walls.forEach { addChild(it) }
            chests.forEach { addChild(it) }
            enemies.forEach { addChild(it) }
            collectables.forEach { addChild(it) }
            items.forEach { addChild(it) }
            addChild(Player(playerX, playerY))
            gameObjects.add(Player)

            //Called every Frame -> Follows the Player until he reaches the edge of the map
            //Updates all Objects

            //Main loop of the Game:
            addUpdater {
                if (!GameModule.gameOver) {
                    gameObjects.forEach { it.update() }
                    sortChildrenBy { it.y }
                    followPlayer()
                }
            }
        }
        return configuredCam
    }

    //Simple keyEvent-methods -> used in Input()

    @KorgeExperimental
    fun keyPressed(body: (Key) -> Unit) {
        configuredCam.keys.down {
            body(it)
        }
    }

    fun keyReleased(body: (Key) -> Unit) {
        configuredCam.keys.up {
            body(it)
        }
    }


    fun followPlayer() {
        actualX = Player.x - cameraSize.width / 2
        actualY = Player.y - cameraSize.height / 2
        if (actualX >= offsetMaxX) actualX = offsetMaxX
        else if (actualX <= offsetMinX) actualX = offsetMinX
        if (actualY >= offsetMaxY) actualY = offsetMaxY
        else if (actualY <= offsetMinY) actualY = offsetMinY

        configuredCam.position(-actualX, -actualY)
    }

}


/** Simple function which adds the worldCamera to the Container and makes it executable
 * This method has to be called in the [LevelTemplate] to load the Camera correctl
 * (each Level uses this one Camera -> implemented in [LevelTemplate]
 */
@KorgeInternal
fun Container.createWorldCamera() = GameModule.worldCamera.loadconfiguredCam().addTo(this)
