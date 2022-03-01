package com.beok.kakaogallerysearch.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beok.kakaogallerysearch.domain.model.ImageChunk
import com.beok.kakaogallerysearch.domain.model.VideoChunk
import com.beok.kakaogallerysearch.domain.usecase.SearchUseCase
import com.beok.kakaogallerysearch.presentation.model.Gallery
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchImageUseCase: SearchUseCase<ImageChunk>,
    private val searchVideoUseCase: SearchUseCase<VideoChunk>,
) : ViewModel() {

    private val _galleryGroup = MutableLiveData<List<Gallery>>()
    val galleryGroup: LiveData<List<Gallery>> get() = Transformations.map(_galleryGroup) {
        it.sortedByDescending(Gallery::datetime)
    }

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> get() = _error

    var imagePageInfo: PageInfo = PageInfo()
        private set
    var videoPageInfo: PageInfo = PageInfo()
        private set
    private val loading = Loading()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _error.value = throwable
    }

    fun setupPageInfo(isNext: Boolean = false) {
        if (!isNext) {
            imagePageInfo = PageInfo()
            videoPageInfo = PageInfo()
            _galleryGroup.value = emptyList()
        } else {
            imagePageInfo = imagePageInfo.copy(value = imagePageInfo.value + 1)
            videoPageInfo = videoPageInfo.copy(value = videoPageInfo.value + 1)
        }
    }

    fun searchByVideo(query: String) = viewModelScope.launch(coroutineExceptionHandler) {
        if (videoPageInfo.isEnd) return@launch
        searchVideoUseCase
            .execute(
                query = query,
                page = videoPageInfo.value
            )
            .catch {
                loading.hide()
                _error.value = it
            }
            .onStart { loading.show() }
            .onCompletion { loading.hide() }
            .collect {
                _galleryGroup.value = (_galleryGroup.value ?: emptyList())
                    .plus(it.videoGroup.map(Gallery::fromDomain))
                videoPageInfo = videoPageInfo.copy(isEnd = it.isEnd)
            }
    }

    fun searchByImage(query: String) = viewModelScope.launch(coroutineExceptionHandler) {
        if (imagePageInfo.isEnd) return@launch
            searchImageUseCase
                .execute(
                    query = query,
                    page = imagePageInfo.value
                )
                .catch {
                    loading.hide()
                    _error.value = it
                }
                .onStart { loading.show() }
                .onCompletion { loading.hide() }
                .collect {
                    _galleryGroup.value = (_galleryGroup.value ?: emptyList())
                        .plus(it.imageGroup.map(Gallery::fromDomain))
                    imagePageInfo = imagePageInfo.copy(isEnd = it.isEnd)
                }
    }
}
