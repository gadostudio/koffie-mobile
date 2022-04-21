package id.shaderboi.koffie.di

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import id.shaderboi.koffie.R
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonConfiguration
import io.noties.markwon.core.CorePlugin

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    fun provideMarkwon(@ActivityContext context: Context): Markwon = Markwon.builder(context)
        .usePlugin(CorePlugin.create())
        .usePlugin(object : AbstractMarkwonPlugin() {
            override fun configureConfiguration(builder: MarkwonConfiguration.Builder) {
                super.configureConfiguration(builder)
                builder.linkResolver { view, link ->
                    val customTabIntent = CustomTabsIntent.Builder()
                        .setToolbarColor(context.getColor(R.color.primary))
                        .build()
                    customTabIntent.launchUrl(context, Uri.parse(link))
                }
            }
        })
        .build()
}