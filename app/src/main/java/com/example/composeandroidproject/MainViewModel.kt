package com.example.composeandroidproject

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

interface MainViewModelInterface {

    val selectedCar: MutableState<ChipCategories?>
    val selectedCars: MutableState<List<ChipCategories?>>
}

class MainViewModel: ViewModel(), MainViewModelInterface {

    companion object {

        val composeViewModel by lazy {
            object : MainViewModelInterface {
                override val selectedCar: MutableState<ChipCategories?> = mutableStateOf(ChipCategories.All)
                override val selectedCars: MutableState<List<ChipCategories?>> = mutableStateOf(listOf(*ChipCategories.values()))
            }
        }
    }

    override val selectedCar: MutableState<ChipCategories?> = mutableStateOf(ChipCategories.All)
    override val selectedCars: MutableState<List<ChipCategories?>> = mutableStateOf(listOf(ChipCategories.All))
}