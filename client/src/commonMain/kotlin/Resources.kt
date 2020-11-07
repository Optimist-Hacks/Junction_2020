import com.soywiz.korau.sound.NativeSound
import com.soywiz.korau.sound.readSound
import com.soywiz.korge.atlas.Atlas
import com.soywiz.korge.atlas.readAtlas
import com.soywiz.korge.tiled.TiledMap
import com.soywiz.korge.tiled.readTiledMap
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.bitmap.BmpSlice
import com.soywiz.korim.font.BitmapFont
import com.soywiz.korim.font.readBitmapFont
import com.soywiz.korim.format.readBitmap
import com.soywiz.korim.format.readBitmapSlice
import com.soywiz.korio.file.std.resourcesVfs
import me.emig.libEmi.audio.Music

/**
 * All the resources from the resources-folder are listed here to have comfortable access to them
 */

object Resources {
    const val menueHintergrund: String = "Hintergrund_Menue.png"
    const val gameIcon: String = "Icon.png"
    const val sword: String = "sword.png"
    const val button_green_gold_over = "buttonsMenu/green_gold_over.png"
    const val button_green_gold_normal = "buttonsMenu/green_gold_normal.png"
    const val button_green_gold_down = "buttonsMenu/green_gold_down.png"
    const val button_orange_blue_over = "buttonsMenu/orange_blue_over.png"
    const val button_orange_blue_normal = "buttonsMenu/orange_blue_normal.png"
    const val button_orange_blue_down = "buttonsMenu/orange_blue_down.png"
    const val button_green_blue_over = "buttonsMenu/green_blue_over.png"
    const val button_green_blue_normal = "buttonsMenu/green_blue_normal.png"
    const val button_green_blue_down = "buttonsMenu/green_blue_down.png"
    const val button_green_blue_disabled = "buttonsMenu/green_blue_disabled.png"

    const val player = "player/player.xml"
    const val enemy = "enemy/enemy.xml"
    const val item = "chest/sword.xml"
    const val chest = "chest/chest.xml"
    const val title = "font/title.png"
    const val font = "font/Test.fnt"
    const val collectableAtlas = "collectable/collectable.xml"
    const val collectableImage = "collectable/animation-collectable.png"

    const val level1 = "map/level_1/level_1.tmx"
    const val level2 = "map/level_2/level_2.tmx"
    const val level3 = "map/level_3/level_3.tmx"
    const val level1_old = "map/old/level_1_old.tmx"
    const val level2_old = "map/old/level_2_old.tmx"
    const val level3_old = "map/old/level_3_old.tmx"

    val HUDLives = arrayOf("HUD/HUD-05.png", "HUD/HUD-04.png", "HUD/HUD-03.png", "HUD/HUD-02.png", "HUD/HUD-01.png", "HUD/HUD-00.png")

    //bitmaps for all objects on the screen -> initialized later
    lateinit var bitmapEnemy: Atlas
    lateinit var bitmapChest: Atlas
    lateinit var bitmapPlayer: Atlas
    lateinit var bitmapItem: Atlas
    lateinit var bitmapCollectable: Atlas

    lateinit var bitmapFont: BitmapFont
    lateinit var bitmapCollectableHUD: Bitmap
    lateinit var bitmapHUDLives: Array<BmpSlice>
    lateinit var enemyFaceHUD: Bitmap

    lateinit var gameOver: Bitmap
    lateinit var levelGoal: Bitmap

    lateinit var mapLevel1: TiledMap
    lateinit var mapLevel2: TiledMap
    lateinit var mapLevel3: TiledMap


    //Sounds and music
    lateinit var buttonClick: NativeSound
    lateinit var introSound: NativeSound
    lateinit var chestSound: NativeSound
    lateinit var enemyGetsHitSound: NativeSound
    lateinit var enemyWalkSound: NativeSound
    lateinit var gameOverSound: NativeSound
    lateinit var getCollectableSound: NativeSound
    lateinit var getSwordSound: NativeSound
    lateinit var itemBreaksSound: NativeSound
    lateinit var itemSpanwtSound: NativeSound
    lateinit var levelFinishSound: NativeSound
    lateinit var playerDiesSound: NativeSound
    lateinit var playerGetsAttackedSound: NativeSound
    lateinit var playerTrifftSound: NativeSound

    var level_1Sound = Music("music/level_1.wav")
    var level_2Sound = Music("music/level_2.wav")
    var level_3Sound = Music("music/level_1.wav")
    var background_music = Music("music/background_music.mp3")

    /** Loading of all resources which need to be loaded in suspend-functions -> initializing of lateinit-values
     */
    suspend fun loadBitmaps() {
        bitmapEnemy = resourcesVfs[Resources.enemy].readAtlas()
        bitmapChest = resourcesVfs[Resources.chest].readAtlas()
        bitmapPlayer = resourcesVfs[Resources.player].readAtlas()
        bitmapItem = resourcesVfs[Resources.item].readAtlas()
        bitmapCollectable = resourcesVfs[Resources.collectableAtlas].readAtlas()

        bitmapHUDLives = Array<BmpSlice>(6) {
            resourcesVfs[HUDLives[it]].readBitmapSlice()
        }
        enemyFaceHUD = resourcesVfs["HUD/enemyFace.png"].readBitmap()

        gameOver = resourcesVfs["gameOver.png"].readBitmap()
        levelGoal = resourcesVfs["levelGoalImage.png"].readBitmap()


        bitmapFont = resourcesVfs[Resources.font].readBitmapFont()
        bitmapCollectableHUD = resourcesVfs[Resources.collectableImage].readBitmap()

        mapLevel1 = resourcesVfs[Resources.level1].readTiledMap()
        mapLevel2 = resourcesVfs[Resources.level2].readTiledMap()
        mapLevel3 = resourcesVfs[Resources.level3].readTiledMap()

        //Load music and sounds
        buttonClick = resourcesVfs["music/Menu_Selection_Click.wav"].readSound()
        introSound = resourcesVfs["music/intro-sound.mp3"].readSound()
        chestSound = resourcesVfs["music/chest.wav"].readSound()
        enemyGetsHitSound = resourcesVfs["music/enemy_gets_hit.wav"].readSound()
        enemyWalkSound = resourcesVfs["music/enemy_walk.mp3"].readSound()
        gameOverSound = resourcesVfs["music/gameover.mp3"].readSound()
        getCollectableSound = resourcesVfs["music/get_collectable.mp3"].readSound()
        getSwordSound = resourcesVfs["music/get_sword.wav"].readSound()
        itemBreaksSound = resourcesVfs["music/item_brakes.mp3"].readSound()
        itemSpanwtSound = resourcesVfs["music/item_spawnt.mp3"].readSound()
        levelFinishSound = resourcesVfs["music/level_finish.mp3"].readSound()
        playerDiesSound = resourcesVfs["music/player_dies.mp3"].readSound()
        playerGetsAttackedSound = resourcesVfs["music/player_gets_attacked.mp3"].readSound()
        playerTrifftSound = resourcesVfs["music/player_hit.mp3"].readSound()
    }


}
