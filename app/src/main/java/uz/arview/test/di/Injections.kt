package uz.arview.test.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.arview.test.data.local.GameDatabase
import uz.arview.test.data.remote.ApiInterface
import uz.arview.test.ui.main.TopGameViewModel
import java.util.concurrent.TimeUnit

private const val BASE_URL: String = "https://api.twitch.tv/kraken/"

val localDataModule = module {
    single {
        Room
            .databaseBuilder(
                androidContext(),
                GameDatabase::class.java,
                "base.db"
            )
            .build()
    }
    single { get<GameDatabase>().topGameDao() }
}

val networkModule = module {
    single {
        GsonBuilder().setLenient().create()
    }
    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build()

    }
    single {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(get())
            .build()
    }
    single { get<Retrofit>().create(ApiInterface::class.java) }
}

val viewModelModule = module {
    viewModel { TopGameViewModel(get(), get()) }
}