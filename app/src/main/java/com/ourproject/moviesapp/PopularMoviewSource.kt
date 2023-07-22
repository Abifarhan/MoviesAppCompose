package com.ourproject.moviesapp

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ourproject.moviesapp.service.MovieApi
import com.ourproject.moviesapp.service.result.MovieResultEntity
import retrofit2.HttpException
import java.io.IOException

//class PopularMoviewSource(private val api: MovieApi) :
//    PagingSource<Int, MovieResultEntity.MovieEntity>() {
//    override fun getRefreshKey(state: PagingState<Int, MovieResultEntity.MovieEntity>): Int? {
//        return state.anchorPosition
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResultEntity.MovieEntity> {
//        return try {
//            val nextPage = params.key ?: 1
//            val popularMovies = api.getPopularMovies(nextPage)
//            LoadResult.Page(
//                data = popularMovies.searches,
//                prevKey = if (nextPage == 1) null else nextPage - 1,
//                nextKey = if (popularMovies.searches.isEmpty()) null else popularMovies.page + 1
//            )
//        } catch (exception: IOException) {
//            return LoadResult.Error(exception)
//        } catch (exception: HttpException) {
//            return LoadResult.Error(exception)
//        }
//    }
//
//}