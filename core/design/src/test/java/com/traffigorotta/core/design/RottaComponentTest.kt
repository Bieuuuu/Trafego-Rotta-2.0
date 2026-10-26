package com.traffigorotta.core.design

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RoborazziRule
import com.github.takahirom.roborazzi.captureRoboImage
import com.traffigorotta.core.design.component.RottaPrimaryButton
import com.traffigorotta.core.design.component.RottaSignalChip
import com.traffigorotta.core.design.component.SignalLevel
import com.traffigorotta.core.design.theme.RottaTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class RottaComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun captureSignalGood() {
        composeTestRule.setContent {
            RottaTheme {
                RottaSignalChip(level = SignalLevel.GOOD)
            }
        }
        composeTestRule.onRoot().captureRoboImage("build/outputs/roborazzi/signal_good.png")
    }

    @Test
    fun capturePrimaryButton() {
        composeTestRule.setContent {
            RottaTheme {
                RottaPrimaryButton(text = "Iniciar jornada", onClick = {})
            }
        }
        composeTestRule.onRoot().captureRoboImage("build/outputs/roborazzi/primary_button.png")
    }
}
