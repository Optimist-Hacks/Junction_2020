import GameModule.level3Abgeschlossen
import com.soywiz.korge.annotations.KorgeExperimental


/** The LevelScenes have the same structure because they all extend the superclass [LevelTemplate].
 * Each Level has to intialize all its objects in its [loadElements]-Function.
 * The [whenFinished]-method is executed after the Levelgoal is reached, but this has to be fixed
 * -> TODO()
 */

//level 3

@KorgeExperimental
class Level_3()
    : LevelTemplate(Map.LEVEL_3, 2.0, 1330, 1000, Resources.level_3Sound) {

    override var killGoal: Int = 7
    override var neededCollectables = 4
    override var showCoords = true

    override fun loadElements() {
        //getWalls
        wall(0, 0, GameModule.worldCamera.worldSize.width, 20)
        wall(0, 0, 20, GameModule.worldCamera.worldSize.height)
        wall(0, 1580, GameModule.worldCamera.worldSize.width, 20)
        wall(1580, 0, 20, GameModule.worldCamera.worldSize.height)
        wall(0, 0, 128, 320)
        wall(128, 0, 95, 80)
        wall(223, 0, 209, 320)
        wall(0, 560, 433, 130)
        wall(50, 514, 235, 46)
        wall(80, 480, 207, 35)
        wall(590, 0, 546, 433)
        wall(590, 513, 354, 192)
        wall(1025, 656, 15, 32)
        wall(1025, 560, 15, 32)
        wall(1105, 560, 15, 32)
        wall(1105, 656, 15, 32)
        wall(1423, 33, 177, 273)
        wall(1440, 465, 160, 238)
        wall(0, 784, 192, 304)
        wall(288, 800, 302, 287)
        wall(590, 928, 146, 159)
        wall(544, 1218, 31, 63)
        wall(544, 1329, 31, 63)
        wall(544, 1440, 31, 63)
        wall(544, 1554, 31, 63)
        wall(641, 1217, 79, 383)
        wall(671, 1184, 79, 94)
        wall(720, 1280, 415, 320)
        wall(1295, 1185, 305, 415)
        wall(944, 862, 192, 226)
        wall(736, 876, 209, 212)
        wall(736, 848, 32, 29)
        wall(1393, 784, 207, 316)
        wall(1296, 945, 15, 31)
        wall(1296, 993, 15, 31)
        wall(1296, 1040, 15, 31)
        wall(1376, 945, 15, 31)
        wall(1376, 993, 15, 31)
        wall(1376, 1040, 15, 31)
        wall(847, 832, 16, 16)
        wall(895, 817, 16, 16)
        wall(895, 849, 16, 16)
        wall(0, 1216, 384, 384)
        wall(336, 1185, 49, 31)
        wall(750, 1219, 17, 45)
        wall(800, 1184, 47, 32)
        wall(881, 1184, 47, 32)
        wall(961, 1184, 47, 32)
        wall(1041, 1184, 47, 32)
        wall(800, 1250, 47, 32)
        wall(881, 1250, 47, 32)
        wall(961, 1250, 47, 32)
        wall(1041, 1250, 47, 32)

        //getChests
        chest(176, 85, 1.0, -40, 0)
        chest(1057, 609, 1.0, 50, 7)
        chest(1310, 22, 1.0, 40, 40)
        chest(930, 1219, 1.0, -40, 8)
        chest(20, 1185, 1.0, 5, -50)

        //getCollectables
        collectable(21, 533)
        collectable(1010, 525)
        collectable(780, 840)
        collectable(590, 1515)

        //getEnemies
        enemy(820, 1135, path(arrayOf(PathNode(780, 1135), PathNode(780, 1230), PathNode(860, 1230), PathNode(860, 1135), PathNode(1021, 1135), PathNode(1021, 1230), PathNode(1115, 1230), PathNode(1115, 1135))))
        enemy(600, 1390, path(arrayOf(PathNode(600, 1235), PathNode(600, 1550))))
        enemy(650, 790, path(arrayOf(PathNode(650, 900), PathNode(650, 790), PathNode(1030, 790))))
        enemy(965, 470, path(arrayOf(PathNode(965, 740), PathNode(1165, 740), PathNode(1165, 470), PathNode(965, 470))))
        enemy(1180, 90, path(arrayOf(PathNode(1400, 90), PathNode(1180, 90))))
        enemy(40, 730, path(arrayOf(PathNode(230, 730), PathNode(230, 1070), PathNode(230, 730), PathNode(400, 730), PathNode(40, 730))))
        enemy(185, 150, path(arrayOf(PathNode(185, 395), PathNode(500, 395), PathNode(500, 100), PathNode(500, 395), PathNode(185, 395), PathNode(185, 150))))
    }

    override fun whenFinished() {
        level3Abgeschlossen = true
    }

}