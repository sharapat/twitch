package uz.arview.test.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import uz.arview.test.core.Resource
import uz.arview.test.data.local.TopGameDao
import uz.arview.test.data.local.model.GameDbModel
import uz.arview.test.data.remote.ApiInterface
import uz.arview.test.data.remote.C

class TopGameViewModel(private val dao: TopGameDao, private val api: ApiInterface) : ViewModel() {
    private var mutableTopGameListLocal: MutableLiveData<Resource<List<GameDbModel>>> = MutableLiveData()
    val topGameListLocal: LiveData<Resource<List<GameDbModel>>>
    get() = mutableTopGameListLocal

    private var mutableTopGameListNetwork: MutableLiveData<Resource<List<GameDbModel>>> = MutableLiveData()
    val topGameListNetwork: LiveData<Resource<List<GameDbModel>>>
        get() = mutableTopGameListNetwork

    private val compositeDisposable = CompositeDisposable()

    fun getTopGamesFromLocal() {
        mutableTopGameListLocal.value = Resource.loading()
        compositeDisposable.add(
            dao.getTopGames()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        mutableTopGameListLocal.value = Resource.success(result)
                    },
                    {
                        mutableTopGameListLocal.value = Resource.error(it.localizedMessage)
                    }
                )
        )
    }

    fun getTopGamesFromNetwork(limit: Int, offset: Int) {
        compositeDisposable.add(
            api.getTopGames(C.clientId, C.accept, limit, offset)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        val list = result.top.map { GameDbModel.convertToDbModel(it.game) }
                        mutableTopGameListNetwork.value = Resource.success(list)
                    },
                    {
                        mutableTopGameListNetwork.value = Resource.error(it.localizedMessage)
                    }
                )
        )
    }

    fun insertToDatabase(games: List<GameDbModel>) {
        compositeDisposable.add(
            dao.insertGames(games).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        )
    }
}