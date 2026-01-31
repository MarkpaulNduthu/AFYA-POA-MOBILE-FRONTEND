package com.mwema.afyapoa.ui.login

fun validate(
    registrationNumber: String,
    password : String
): Map<String,String>{
    val errors = mutableMapOf<String, String>()
    val regNumberRegex = Regex("^[A-Z]+/\\d{4}/\\d{4}$")

    if (registrationNumber.isBlank()) {
        errors["registrationNumber"] = "Registration Number is required"
    } else if (!registrationNumber.matches(regNumberRegex)) {
        errors["registrationNumber"] = "Invalid registration number format"
    }

    if (password.isBlank()) {
        errors["password"] = "Password is required"
    }

    return errors
}