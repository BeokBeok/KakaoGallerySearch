package com.beok.kakaogallerysearch.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beok.kakaogallerysearch.domain.usecase.SearchGalleryUseCase
import com.beok.kakaogallerysearch.presentation.model.Gallery
import com.beok.kakaogallerysearch.presentation.model.Loading
import com.beok.kakaogallerysearch.presentation.model.PageInfo
import com.beok.kakaogallerysearch.presentation.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
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

    private val _error = MutableLiveData<Event<Throwable>>()
    val error: LiveData<Event<Throwable>> get() = _error

    private val pageInfo = PageInfo()
    val loading = Loading()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        loading.hide()
        _error.value = Event(content = throwable)
    }

    fun searchGallery(isNext: Boolean, query: String) =
        viewModelScope.launch(coroutineExceptionHandler) {
            pageInfo.setup(isNext = isNext)

            if (pageInfo.isEnd) return@launch

            if (!isNext) _galleryGroup.value = emptyList()
            searchGalleryUseCase
                .execute(query = query, page = pageInfo.value)
                .onStart { loading.show() }
                .onCompletion { loading.hide() }
                .collect {
                    _galleryGroup.value = (_galleryGroup.value?.toList() ?: emptyList())
                        .plus(it.imageChunk.imageGroup.map(Gallery::fromDomain))
                        .plus(it.videoChunk.videoGroup.map(Gallery::fromDomain))
                        .sortedByDescending(Gallery::datetime)
                    pageInfo.update(isEnd = it.imageChunk.isEnd && it.videoChunk.isEnd)
                }
        }

    fun onClickForSave(item: Gallery) {
        _boxGroup.value = (_boxGroup.value?.toList() ?: emptyList())
            .plus(item)
            .distinct()
    }
}
