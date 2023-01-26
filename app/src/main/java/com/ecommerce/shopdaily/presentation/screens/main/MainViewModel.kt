package com.ecommerce.shopdaily.presentation.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecommerce.shopdaily.common.Resource
import com.ecommerce.shopdaily.data.remote.FakeApi
import com.ecommerce.shopdaily.domain.model.login.User
import com.ecommerce.shopdaily.domain.model.product.Product
import com.ecommerce.shopdaily.domain.use_cases.local.DeleteFromFavoritesUseCase
import com.ecommerce.shopdaily.domain.use_cases.local.GetFavoritesUseCase
import com.ecommerce.shopdaily.domain.use_cases.local.GetSavedUserUseCase
import com.ecommerce.shopdaily.domain.use_cases.local.SaveToFavoritesUseCase
import com.ecommerce.shopdaily.domain.use_cases.remote.categories.GetCategoriesUseCase
import com.ecommerce.shopdaily.domain.use_cases.remote.categories.GetCategoryUseCase
import com.ecommerce.shopdaily.presentation.screens.main.util.category.CategoryEvent
import com.ecommerce.shopdaily.presentation.screens.main.util.category.CategoryState
import com.ecommerce.shopdaily.presentation.screens.main.util.category.ShopCategoriesState
import com.ecommerce.shopdaily.presentation.screens.main.util.favorites.FavoritesState
import com.ecommerce.shopdaily.presentation.screens.main.util.product.ProductEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSavedUserUseCase: GetSavedUserUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val saveToFavoritesUseCase: SaveToFavoritesUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val deleteFromFavoritesUseCase: DeleteFromFavoritesUseCase
) : ViewModel() {
    var loggedUser: User? = null
        private set
    var screenLoadingState by mutableStateOf(true)
        private set
    var shopCategoriesState by mutableStateOf(ShopCategoriesState())
        private set
    var categoryState by mutableStateOf(CategoryState())
        private set
    var favoritesState by mutableStateOf(FavoritesState())
        private set

    init {
        getSavedUser()
    }

    private val fakeApi = FakeApi()

    private val _cartProducts = mutableListOf<Product>()
    val cartProducts: List<Product> = _cartProducts

    private val _dummyProducts = fakeApi.getDummyProducts()
    val dummyProducts: List<Product> = _dummyProducts

    fun addProduct(product: Product) {
        _cartProducts.add(product)
    }

    fun onCategoriesEvent(categoryEvent: CategoryEvent) {
        when (categoryEvent) {
            is CategoryEvent.GetCategory -> getCategory(
                categoryEvent.token,
                categoryEvent.categoryId,
                categoryEvent.name
            )
            is CategoryEvent.CloseCategory -> closeCategory()
        }
    }

    private fun getCategories(token: String) {
        viewModelScope.launch {
            getCategoriesUseCase(token).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        shopCategoriesState = shopCategoriesState.copy(
                            isLoading = true,
                            shopCategories = null,
                            error = null
                        )
                    }
                    is Resource.Success -> {
                        result.data?.let { categories ->
                            shopCategoriesState = shopCategoriesState.copy(
                                isLoading = false,
                                shopCategories = categories,
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        result.message?.let { errorMsg ->
                            shopCategoriesState = shopCategoriesState.copy(
                                isLoading = false,
                                shopCategories = null,
                                error = errorMsg
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getCategory(token: String, categoryId: String, name: String) {
        viewModelScope.launch {
            getCategoryUseCase(token, categoryId, name).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        categoryState = categoryState.copy(
                            isCategoryVisible = true,
                            isLoading = true,
                            category = null,
                            error = null
                        )
                    }
                    is Resource.Success -> {
                        result.data?.let { category ->
                            categoryState = categoryState.copy(
                                isCategoryVisible = true,
                                isLoading = false,
                                category = category,
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        result.message?.let { errorMsg ->
                            categoryState = categoryState.copy(
                                isCategoryVisible = true,
                                isLoading = false,
                                category = null,
                                error = errorMsg
                            )
                        }
                    }
                }
            }
        }
    }

    private fun closeCategory() {
        categoryState = categoryState.copy(
            isLoading = false,
            category = null,
            error = null,
            isCategoryVisible = false
        )
    }

    private fun getSavedUser() {
        viewModelScope.launch {
            getSavedUserUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        screenLoadingState = true
                    }
                    is Resource.Success -> {
                        screenLoadingState = false
                        result.data?.let { user ->
                            loggedUser = user
                            getCategories(loggedUser?.token!!)
                            onProductEvent(ProductEvent.GetFavorites)
                        }
                    }
                    is Resource.Error -> {
                        screenLoadingState = false
                    }
                }
            }
        }
    }

    fun onProductEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.SaveToFavorites -> saveToFavorites(event.product)
            is ProductEvent.GetFavorites -> getFavorites()
            is ProductEvent.DeleteFromFavorites -> deleteFromFavorites(event.product)
        }
    }

    private fun saveToFavorites(product: Product) {
        viewModelScope.launch {
            saveToFavoritesUseCase(product).collect { result ->
                screenLoadingState = when (result) {
                    is Resource.Loading -> true
                    is Resource.Success -> {
                        onProductEvent(ProductEvent.GetFavorites)
                        false
                    }
                    is Resource.Error -> false
                }
            }
        }
    }

    private fun getFavorites() {
        viewModelScope.launch {
            getFavoritesUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        favoritesState = favoritesState.copy(
                            isLoading = true,
                            favorites = null,
                            error = null
                        )
                    }
                    is Resource.Success -> {
                        result.data?.let { favorites ->
                            favoritesState = favoritesState.copy(
                                isLoading = false,
                                favorites = favorites,
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        result.message?.let { errorMsg ->
                            favoritesState = favoritesState.copy(
                                isLoading = false,
                                favorites = null,
                                error = errorMsg
                            )
                        }
                    }
                }
            }
        }
    }

    private fun deleteFromFavorites(product: Product) {
        viewModelScope.launch {
            deleteFromFavoritesUseCase(product).collect { result ->
                screenLoadingState = when (result) {
                    is Resource.Loading -> true
                    is Resource.Success -> {
                        onProductEvent(ProductEvent.GetFavorites)
                        false
                    }
                    is Resource.Error -> false
                }
            }
        }
    }
}