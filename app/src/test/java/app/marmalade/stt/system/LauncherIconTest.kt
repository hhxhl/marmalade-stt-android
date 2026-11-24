package app.marmalade.stt.system

import android.app.Application
import android.content.ComponentName
import android.content.pm.PackageManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], application = Application::class)
class LauncherIconTest {
    private val context get() = RuntimeEnvironment.getApplication()
    private val launcher get() = ComponentName(context.packageName, "app.marmalade.stt.LauncherActivity")
    private val settings get() = ComponentName(context.packageName, "app.marmalade.stt.SettingsActivity")

    @Test
    fun initializesOnlyOnceAndKeepsTheSettingsEntry() {
        val pm = context.packageManager
        pm.setComponentEnabledSetting(
            launcher, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP,
        )
        assertTrue(LauncherIcon.isHidden(context))
        LauncherIcon.initialize(context)
        assertFalse(LauncherIcon.isHidden(context))
        LauncherIcon.setHidden(context, true)
        LauncherIcon.initialize(context)
        assertTrue(LauncherIcon.isHidden(context))
        assertEquals(PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, pm.getComponentEnabledSetting(settings))
        assertTrue(pm.getActivityInfo(settings, 0).enabled)
        LauncherIcon.setHidden(context, false)
        assertFalse(LauncherIcon.isHidden(context))
    }
}
