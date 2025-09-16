package com.example.vcompass.util

import com.vcompass.presentation.viewmodel.BaseViewModel


fun handleErrorCode(errorCode: Int?, viewModel: BaseViewModel) {
    when {
        errorCode?.isExpiredToken() == true -> {
            viewModel.navigateToLoginScreen()
        }

        errorCode?.isForbidden() == true -> {
            viewModel.addError403()
        }

        else -> {}
    }
}