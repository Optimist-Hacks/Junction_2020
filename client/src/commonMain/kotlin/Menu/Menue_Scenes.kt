import GameModule.level1Abgeschlossen
import GameModule.level2Abgeschlossen
import GameModule.level3Abgeschlossen
import com.soywiz.klock.milliseconds
import com.soywiz.klock.seconds
import com.soywiz.korau.sound.NativeSoundChannel
import com.soywiz.korau.sound.PlaybackTimes
import com.soywiz.korge.html.Html
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.time.delay
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.ui.UISkin
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.font.readBitmapFont
import com.soywiz.korim.format.readBitmap
import com.soywiz.korim.format.readBitmapSlice
import com.soywiz.korio.async.delay
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.interpolation.Easing
import me.emig.libEmi.addChildren
import me.emig.libEmi.audio.Music
import me.emig.libEmi.graphics.bilder.Bild
import me.emig.libEmi.graphics.text.Text
import me.emig.libEmi.graphics.text.TextButton

//Loading the Intro scene, but playing videos isn't possible yet -> TODO()
class IntroScene : Scene() {

    private lateinit var image: Image
    private lateinit var sound: NativeSoundChannel

    override suspend fun Container.sceneInit() {
        Resources.loadBitmaps()
        sound = Resources.introSound.play().apply { volume = 0.4 }
        image = image(resourcesVfs["Logo.png"].readBitmap()) {
            smoothing = false
            alpha = 0.0
            scale = 0.6
        }
        image.xy(GameModule.size.width / 2 - (image.width / 2) * image.scale, GameModule.size.height / 2 - (image.height / 2) * image.scale)
    }

    override suspend fun sceneAfterInit() {
        image.tween(image::alpha[1.0], time = 2.5.seconds)
        delay(1.5.seconds)
        image.tween(image::alpha[0.0], time = 1.seconds)
        delay(1.seconds)
        sceneContainer.changeTo<MainMenue>()
    }
}


//Main Menu
class MainMenue(var bgmusic: Music) : Scene() {
    /** Declaration of buttons     */
    val buttonsX = GameModule.size.width / 2 - 120
    val buttonsY = 180
    lateinit var startButton: TextButton
    lateinit var creditsButton: TextButton
    lateinit var exitButton: TextButton
    lateinit var level1Button: TextButton
    lateinit var level2Button: TextButton
    lateinit var level3Button: TextButton
    lateinit var backButton: TextButton
    lateinit var creditsBackButton: TextButton

    val codeText = Text(250, 200, "CODE: MICHAEL SIGL & BENEDIKT HOFMANN", 40.0, Colors.ORANGE).apply { alpha = 0.0 }
    val levelDesignText = Text(50, 250, "LEVELDESIGN: BENEDIKT HOFMANN & JONAS GERL & MICHAEL SIGL", 40.0, Colors.ORANGE).apply { alpha = 0.0 }
    val mapDesignText = Text(215, 300, "MAPDESIGN: JONAS GERL & BENEDIKT HOFMANN", 40.0, Colors.ORANGE).apply { alpha = 0.0 }
    val spriteDesignText = Text(200, 350, "SPRITEDESIGN: KONSTANTIN ENGELHARDT", 40.0, Colors.ORANGE).apply { alpha = 0.0 }
    val graphicsText = Text(450, 400, "GRAPHICS: EVERYBODY", 40.0, Colors.ORANGE).apply { alpha = 0.0 }
    val soundText = Text(400, 450, "MUSIC/SOUNDS: EVERYONE", 40.0, Colors.ORANGE).apply { alpha = 0.0 }

    //Main function
    override suspend fun Container.sceneMain() {
        bgmusic.play(PlaybackTimes.INFINITE)
        /** loading buttons, backgroundmusic and backgroundimage     */
        val buttonFont = Html.FontFace.Bitmap(resourcesVfs[Resources.font].readBitmapFont())
        registerButtons(buttonFont)
        val spriteMapHintergrund = resourcesVfs[Resources.menueHintergrund].readBitmap()
        val skalierungBackground = GameModule.gameSizeX / 640.0
        val hintergrund = SpriteAnimation(
                spriteMapHintergrund,
                640,
                360,
                0,
                0,
                8,
                1,
                0,
                0
        )
        val hintergrundSprite = Sprite(hintergrund).apply {
            scale(skalierungBackground)
            xy(0, 0)
        }

        //make background moving
        hintergrundSprite.playAnimationLooped(hintergrund, 100.milliseconds)
        val titleX = GameModule.size.width / 2 - 349
        val titleY = 50
        val title = Bild(titleX, titleY, bitmap = Resources.title, scale = 1.0)

        /**Adding Buttons and the backgroundImage       */
        addChild(hintergrundSprite)
        addChild(title)
        /** Adding Credits-Texts        */
        addChildren(codeText, levelDesignText, mapDesignText, spriteDesignText, graphicsText, soundText)
        //Buttons
        addChildren(startButton, creditsButton, exitButton, level1Button, level2Button, level3Button, backButton, creditsBackButton)

        while (true) {
            title.tween(title::y[titleY + 8], time = 1.6.seconds, easing = Easing.EASE_IN_OUT)
            title.tween(title::y[titleY - 8], time = 1.6.seconds, easing = Easing.EASE_IN_OUT)
        }
    }

    /** Registering [menuButtons]                            */
    private suspend fun registerButtons(font: Html.FontFace.Bitmap) {
        startButton = TextButton(buttonsX, buttonsY, 240, 70, "Start", true, skin = UISkin(resourcesVfs[Resources.button_green_gold_normal].readBitmapSlice(), resourcesVfs[Resources.button_green_gold_over].readBitmapSlice(), resourcesVfs[Resources.button_green_gold_down].readBitmapSlice()), font = font) {
            Resources.buttonClick.play().await()
            loadLevelButtons()
        }.apply {
            textSize = 30
            shadowVisible = false
        }

        creditsButton = TextButton(buttonsX, buttonsY + 130, 240, 70, "Credits", true, skin = UISkin(resourcesVfs[Resources.button_green_gold_normal].readBitmapSlice(), resourcesVfs[Resources.button_green_gold_over].readBitmapSlice(), resourcesVfs[Resources.button_green_gold_down].readBitmapSlice()), font = font) {
            Resources.buttonClick.play().await()
            loadCredits()
        }.apply { textSize = 30; shadowVisible = false }

        exitButton = TextButton(buttonsX, buttonsY + 260, 240, 70, "Exit", true, skin = UISkin(resourcesVfs[Resources.button_orange_blue_normal].readBitmapSlice(), resourcesVfs[Resources.button_orange_blue_over].readBitmapSlice(), resourcesVfs[Resources.button_orange_blue_down].readBitmapSlice()), font = font) {
            Resources.buttonClick.play().await()
            views.gameWindow.close()
        }.apply { textSize = 30; shadowVisible = false }

        level1Button = TextButton(GameModule.size.width + 1000, GameModule.size.height + 1000, 240, 70, "Level 1", skin = UISkin(resourcesVfs[Resources.button_green_blue_normal].readBitmapSlice(), resourcesVfs[Resources.button_green_blue_over].readBitmapSlice(), resourcesVfs[Resources.button_green_blue_down].readBitmapSlice()), font = font) {
            Resources.buttonClick.play().await()
            bgmusic.stop()
            sceneContainer.changeTo<Level_1>()
        }.apply { textSize = 30; shadowVisible = false }

        level2Button = TextButton(GameModule.size.width + 1000, GameModule.size.height + 1000, 240, 70, "Level 2", skin = UISkin(resourcesVfs[Resources.button_green_blue_normal].readBitmapSlice(), resourcesVfs[Resources.button_green_blue_over].readBitmapSlice(), resourcesVfs[Resources.button_green_blue_down].readBitmapSlice(), resourcesVfs[Resources.button_green_blue_disabled].readBitmapSlice()), font = font) {
            Resources.buttonClick.play().await()
            bgmusic.stop()
            sceneContainer.changeTo<Level_2>()
        }.apply {
            enable(level1Abgeschlossen)
            textSize = 30
            shadowVisible = false
        }

        level3Button = TextButton(GameModule.size.width + 1000, GameModule.size.height + 1000, 240, 70, "Level 3", skin = UISkin(resourcesVfs[Resources.button_green_blue_normal].readBitmapSlice(), resourcesVfs[Resources.button_green_blue_over].readBitmapSlice(), resourcesVfs[Resources.button_green_blue_down].readBitmapSlice(), resourcesVfs[Resources.button_green_blue_disabled].readBitmapSlice()), font = font) {
            Resources.buttonClick.play().await()
            bgmusic.stop()
            sceneContainer.changeTo<Level_3>()
        }.apply {
            enable(level2Abgeschlossen)
            textSize = 30
            shadowVisible = false
        }

        backButton = TextButton(GameModule.size.width + 1000, GameModule.size.height + 1000, 240, 70, "Back", skin = UISkin(resourcesVfs[Resources.button_orange_blue_normal].readBitmapSlice(), resourcesVfs[Resources.button_orange_blue_over].readBitmapSlice(), resourcesVfs[Resources.button_orange_blue_down].readBitmapSlice()), font = font) {
            Resources.buttonClick.play().await()
            loadStartButtons()
        }.apply { textSize = 30; shadowVisible = false }

        creditsBackButton = TextButton(GameModule.size.width + 1000, GameModule.size.height * 1000, 240, 70, "Back", skin = UISkin(resourcesVfs[Resources.button_orange_blue_normal].readBitmapSlice(), resourcesVfs[Resources.button_orange_blue_over].readBitmapSlice(), resourcesVfs[Resources.button_orange_blue_down].readBitmapSlice()), font = font) {
            Resources.buttonClick.play().await()
            loadStartButtons()
        }.apply { textSize = 30; shadowVisible = false }
    }


    /**Load LevelButtons and remove StartButtons     */
    private fun loadLevelButtons() {
        startButton.x = (GameModule.size.width + 1000).toDouble()
        startButton.y = (GameModule.size.height + 1000).toDouble()
        creditsButton.x = (GameModule.size.width + 1000).toDouble()
        creditsButton.y = (GameModule.size.height + 1000).toDouble()
        exitButton.x = (GameModule.size.width + 1000).toDouble()
        exitButton.y = (GameModule.size.height + 1000).toDouble()
        level1Button.x = buttonsX.toDouble()
        level1Button.y = buttonsY.toDouble()
        level2Button.x = buttonsX.toDouble()
        level2Button.y = buttonsY.toDouble() + 110
        level3Button.x = buttonsX.toDouble()
        level3Button.y = buttonsY.toDouble() + 220
        backButton.x = buttonsX.toDouble()
        backButton.y = buttonsY.toDouble() + 330
    }

    /**Load StartButtons and remove LevelButtons        */
    private fun loadStartButtons() {
        level1Button.x = (GameModule.size.width + 1000).toDouble()
        level1Button.y = (GameModule.size.height + 1000).toDouble()
        level2Button.x = (GameModule.size.width + 1000).toDouble()
        level2Button.y = (GameModule.size.height + 1000).toDouble()
        level3Button.x = (GameModule.size.width + 1000).toDouble()
        level3Button.y = (GameModule.size.height + 1000).toDouble()
        backButton.x = (GameModule.size.width + 1000).toDouble()
        backButton.y = (GameModule.size.height + 1000).toDouble()

        codeText.alpha = 0.0
        levelDesignText.alpha = 0.0
        mapDesignText.alpha = 0.0
        spriteDesignText.alpha = 0.0
        graphicsText.alpha = 0.0
        soundText.alpha = 0.0

        creditsBackButton.x = (GameModule.size.width + 1000).toDouble()
        creditsBackButton.y = (GameModule.size.height + 1000).toDouble()
        startButton.x = buttonsX.toDouble()
        startButton.y = buttonsY.toDouble()
        creditsButton.x = buttonsX.toDouble()
        creditsButton.y = buttonsY.toDouble() + 130
        exitButton.x = buttonsX.toDouble()
        exitButton.y = buttonsY.toDouble() + 260
    }

    /** Load Credits and remove Buttons
     */
    private fun loadCredits() {
        startButton.x = (GameModule.size.width + 1000).toDouble()
        startButton.y = (GameModule.size.height + 1000).toDouble()
        creditsButton.x = (GameModule.size.width + 1000).toDouble()
        creditsButton.y = (GameModule.size.height + 1000).toDouble()
        exitButton.x = (GameModule.size.width + 1000).toDouble()
        exitButton.y = (GameModule.size.height + 1000).toDouble()

        codeText.alpha = 1.0
        levelDesignText.alpha = 1.0
        mapDesignText.alpha = 1.0
        spriteDesignText.alpha = 1.0
        graphicsText.alpha = 1.0
        soundText.alpha = 1.0

        backButton.x = buttonsX.toDouble()
        backButton.y = buttonsY.toDouble() + 330
    }

}

class GameOverScene : Scene() {
    override suspend fun Container.sceneInit() {
        Resources.gameOverSound.play(PlaybackTimes.ONE).apply { volume = 0.6 }

        val gameOverImage = Bild(0, 0, Resources.gameOver).apply { scale = 1.3 }
        addChild(gameOverImage)
        level1Abgeschlossen = false
        level2Abgeschlossen = false
        level3Abgeschlossen = false
        delay(5.seconds)
        views.gameWindow.close()
    }
}

class LevelGoalScene : Scene() {
    override suspend fun Container.sceneMain() {

        Resources.levelFinishSound.play(PlaybackTimes.ONE).apply { volume = 0.7 }

        val goalImage = Bild(0, 0, Resources.levelGoal).apply { scale = 0.5 }
        addChild(goalImage)
        delay(5.seconds)
        sceneContainer.changeTo<MainMenue>()
    }
}

