package app.marmalade.stt.system

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

/** Only changes the launcher alias; speech services and SettingsActivity stay enabled. */
object LauncherIcon {
    private fun component(context: Context) =
        ComponentName(context.packageName, "app.marmalade.stt.LauncherActivity")

    /**
     * The alias starts disabled in the manifest. Make it visible on the first
     * UI launch, but never overwrite a user's explicit hidden or visible choice.
     * PackageManager persists the component state for each Android user.
     */
    fun initialize(context: Context) {
        if (context.packageManager.getComponentEnabledSetting(component(context)) ==
            PackageManager.COMPONENT_ENABLED_STATE_DEFAULT
        ) {
            setHidden(context, false)
        }
    }

    fun isHidden(context: Context): Boolean =
        context.packageManager.getComponentEnabledSetting(component(context)) !=
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED

    fun setHidden(context: Context, hidden: Boolean) {
        context.packageManager.setComponentEnabledSetting(
            component(context),
            if (hidden) PackageManager.COMPONENT_ENABLED_STATE_DISABLED
            else PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP,
        )
    }
}
