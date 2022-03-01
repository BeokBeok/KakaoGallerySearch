package com.beok.kakaogallerysearch.presentation

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

    fun setupPageInfo(isNext: Boolean = false) {
        if (!isNext) {
            pageInfo = PageInfo()
            _galleryGroup.value = emptyList()
        } else {
            pageInfo = pageInfo.copy(value = pageInfo.value + 1)
        }
    }

    fun searchGallery(query: String) = viewModelScope.launch(coroutineExceptionHandler) {
        if (pageInfo.isEnd) return@launch
        searchGalleryUseCase
            .execute(query = query, page = pageInfo.value)
            .catch {
                loading.hide()
                _error.value = it
            }
            .onStart { loading.show() }
            .onCompletion { loading.hide() }
            .collect {
                _galleryGroup.value = (_galleryGroup.value?.toList() ?: emptyList())
                    .plus(it.imageChunk.imageGroup.map(Gallery::fromDomain))
                    .plus(it.videoChunk.videoGroup.map(Gallery::fromDomain))
                    .sortedByDescending(Gallery::datetime)
                pageInfo = pageInfo.copy(isEnd = it.imageChunk.isEnd && it.videoChunk.isEnd)
            }
    }

    fun onClickForSave(item: Gallery) {
        _boxGroup.value = (_boxGroup.value ?: emptyList())
            .plus(item)
            .distinct()
    }
}
