package com.beok.kakaogallerysearch.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beok.kakaogallerysearch.domain.usecase.SearchGalleryUseCase
import com.beok.kakaogallerysearch.presentation.model.Gallery
import com.beok.kakaogallerysearch.presentation.model.Loading
import com.beok.kakaogallerysearch.presentation.model.PageInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchGalleryUseCase: SearchGalleryUseCase
) : ViewModel() {

    private val _galleryGroup = MutableLiveData<List<Gallery>>()
    val galleryGroup: LiveData<List<Gallery>> get() = _galleryGroup

    private val _boxGroup = MutableLiveData<List<Gallery>>()
    val boxGroup: LiveData<List<Gallery>> get() = _boxGroup

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> get() = _error

    var pageInfo = PageInfo()
        private set
    val loading = Loading()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _error.value = throwable
    }

    // searchGallery, setupPageInfo 이 public으로 나뉘어져 있을 필요가 있나 싶긴하네요.
    // 테스트를 위해 나누신거면 외부에서 호출하지 않고 @VisibleForTesting 붙이는것도 가능해요.
    @VisibleForTesting
    fun setupPageInfo(isNext: Boolean = false) {
        if (!isNext) {
            pageInfo = PageInfo()
            _galleryGroup.value = emptyList()
        } else {
            pageInfo = pageInfo.copy(value = pageInfo.value + 1)
        }
    }

    fun searchGallery(
        isNext: Boolean,
        query: String
    ) = viewModelScope.launch(coroutineExceptionHandler) {
        setupPageInfo(isNext)

        if (pageInfo.isEnd) {
            return@launch
        }

        searchGalleryUseCase
            .execute(query = query, page = pageInfo.value)
            .catch {
                loading.hide() // catch 블럭을 coroutineExceptionHandler에서 처리 가능할까요? 가능하면 더 깔끔해질것 같아요.
                _error.value = it
            }
            .onStart { loading.show() }
            .onCompletion { loading.hide() }
            .collect {
                val old = _galleryGroup.value?.toMutableList().orEmpty()
                val new = mutableListOf<Gallery>()
                    .plus(it.imageChunk.imageGroup.map(Gallery::fromDomain))
                    .plus(it.videoChunk.videoGroup.map(Gallery::fromDomain))
                    .sortedByDescending(Gallery::datetime)
                _galleryGroup.value = old + new
                pageInfo = pageInfo.copy(isEnd = it.imageChunk.isEnd && it.videoChunk.isEnd)
            }
    }

    fun onClickForSave(item: Gallery) {
        _boxGroup.value = (_boxGroup.value ?: emptyList())
            .plus(item)
            .distinct()
    }
}
