import GameModule.level2Abgeschlossen
import com.soywiz.korge.annotations.KorgeExperimental


/** The LevelScenes have the same structure because they all extend the superclass [LevelTemplate].
 * Each Level has to intialize all its objects in its [loadElements]-Function.
 * The [whenFinished]-method is executed after the Levelgoal is reached.
 */

//Level 2

@KorgeExperimental
class Level_2()
    : LevelTemplate(Map.LEVEL_2, 2.0, 1050, 1730, Resources.level_2Sound) {

    override var killGoal: Int = 7
    override var neededCollectables: Int = 10


    override fun loadElements() {

        //getWalls
        wall(960, 1600, 190, 60)
        wall(960, 1660, 60, 200)
        wall(960, 1860, 30, 570)
        wall(960, 2400, 260, 30)
        wall(1090, 2400, 30, 260)
        wall(1090, 2620, 200, 30)
        wall(1250, 2520, 30, 100)
        wall(1250, 2520, 100, 30)
        wall(1310, 2400, 30, 130)
        wall(1310, 2400, 450, 30)
        wall(1730, 2400, 30, 220)
        wall(1760, 2590, 30, 160)
        wall(1760, 2720, 250, 30)
        wall(2000, 2720, 330, 30)
        wall(2300, 2370, 30, 350)
        wall(2080, 2370, 230, 30)
        wall(2210, 1535, 30, 835)
        wall(2045, 1535, 165, 30)
        wall(2110, 1215, 30, 325)
        wall(1920, 1215, 190, 30)
        wall(1920, 1215, 30, 60)
        wall(1695, 1255, 220, 30)
        wall(1700, 1255, 30, 250)
        wall(1730, 1440, 30, 60)
        wall(1670, 1440, 30, 610)
        wall(1824, 1440, 30, 120)
        wall(1824, 1535, 130, 30)
        wall(1855, 1535, 30, 255)
        wall(1880, 1730, 230, 30)
        wall(1760, 1760, 100, 30)
        wall(1760, 1760, 30, 220)
        wall(1690, 1950, 100, 30)
        wall(2140, 1885, 70, 30)
        wall(1920, 1885, 100, 30)
        wall(2080, 2045, 30, 325)
        wall(1920, 1885, 30, 765)
        wall(2110, 2400, 30, 255)
        wall(1660, 2270, 30, 30)
        wall(1695, 2270, 30, 95)
        wall(1730, 2300, 30, 100)
        wall(1760, 2300, 160, 30)
        wall(1790, 2330, 100, 40)
        wall(1820, 2430, 40, 100)
        wall(1800, 2500, 80, 20)
        wall(1800, 2565, 80, 20)
        wall(1820, 2560, 40, 60)
        wall(1630, 2015, 30, 95)
        wall(1470, 2110, 350, 30)
        wall(1537, 2140, 30, 160)
        wall(1567, 2270, 33, 30)
        wall(990, 2050, 65, 30)
        wall(1120, 2050, 100, 60)
        wall(1245, 2110, 95, 60)
        wall(1250, 1920, 30, 130)
        wall(1215, 2015, 30, 225)
        wall(1180, 2175, 30, 95)
        wall(1150, 2205, 30, 195)
        wall(1120, 1660, 130, 35)
        wall(1055, 1820, 525, 30)
        wall(1250, 1630, 30, 190)
        wall(1280, 1630, 390, 30)
        wall(1630, 1820, 30, 30)

        //getChests
        chest(1217, 1696, 1.0, 10, 40)
        chest(1000, 2190, 1.0, 40, 10)
        chest(1760, 2145, 1.0, 40, 20)

        //getCollectables
        collectable(1203, 1790)
        collectable(1289, 1792)
        collectable(1185, 2120)
        collectable(1255, 2185)
        collectable(1160, 2590)
        collectable(1735, 2275)
        collectable(1727, 1921)
        collectable(1895, 1695)
        collectable(2270, 2536)
        collectable(1786, 2389)

        //getEnemies
        enemy(1040, 1900, path(arrayOf(PathNode(1170, 1920), PathNode(1180, 2020), PathNode(1040, 2020), PathNode(1040, 1900))))
        enemy(1310, 1730, path(arrayOf(PathNode(1390, 1730), PathNode(1310, 1730))))
        enemy(1250, 2295, path(arrayOf(PathNode(1250, 2360), PathNode(1250, 2295))))
        enemy(2070, 1650, path(arrayOf(PathNode(2160, 1650), PathNode(2070, 1650))))
        enemy(2210, 2580, path(arrayOf(PathNode(2210, 2670), PathNode(2210, 2580))))
        enemy(2000, 1330, path(arrayOf(PathNode(2060, 1330), PathNode(2060, 1400), PathNode(2000, 1400), PathNode(2000, 1330))))
        enemy(1490, 2000, path(arrayOf(PathNode(1560, 2000), PathNode(1560, 2080), PathNode(1490, 2080), PathNode(1490, 2000))))
    }

    override fun whenFinished() {
        level2Abgeschlossen = true
    }


}