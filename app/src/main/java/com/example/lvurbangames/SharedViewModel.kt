import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _clues = MutableLiveData<List<String>>(emptyList())
    val clues: LiveData<List<String>> get() = _clues

    fun addClue(clue: String) {
        val currentClues = _clues.value ?: emptyList()
        _clues.value = currentClues + clue
    }

    // Clear the clues
    fun clearClues() {
        _clues.value = emptyList()
    }
}