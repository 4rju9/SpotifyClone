package app.netlify.dev4rju9.spotifyclone.di

import android.content.Context
import app.netlify.dev4rju9.spotifyclone.R
import app.netlify.dev4rju9.spotifyclone.exoplayer.MusicServiceConnection
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGlideInstance (
        @ApplicationContext context: Context
    ) = Glide.with(context).applyDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )

    @Singleton
    @Provides
    fun provideMusicServiceConnection (
        @ApplicationContext context: Context
    ) = MusicServiceConnection(context)

}