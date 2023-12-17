package com.ronit.cosmic.feature_auth.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.ronit.cosmic.feature_auth.presentation.sign_in.GoogleAuthUiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthFeatureModule {

    @Provides
    @Singleton
    fun provideGoogleAuthUiClient(@ApplicationContext context: Context):GoogleAuthUiClient{
        return GoogleAuthUiClient(context,Identity.getSignInClient(context))
    }

}
