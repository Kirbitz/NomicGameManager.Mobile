package nomic.ui.viewmodels


import androidx.lifecycle.*



class GameConfigurationViewModel : ViewModel(){
    private val _gameTitle = MutableLiveData<String>()
    val gameTitle :LiveData<String>
        get() = _gameTitle
    fun submitGameTitle(title: String) {
        _gameTitle.value = title
    }

}
