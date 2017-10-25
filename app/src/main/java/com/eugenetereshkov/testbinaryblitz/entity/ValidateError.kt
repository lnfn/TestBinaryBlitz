package com.eugenetereshkov.testbinaryblitz.entity

/**
 * Created by eugenetereshkov on 25.10.2017.
 */
sealed class ValidateError(val msg: String)

class FirstNameError(msg: String) : ValidateError(msg)
class LastNameError(msg: String) : ValidateError(msg)
class EmailError(msg: String) : ValidateError(msg)