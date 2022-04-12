package id.shaderboi.koffie.util.firebase.auth

import androidx.annotation.StringRes
import com.google.firebase.auth.FirebaseAuthException
import id.shaderboi.koffie.R

val FirebaseAuthException.errorCodeResId: Int?
    @StringRes
    get() = errorMap[errorCode]

internal val errorMap = mapOf(
    // FirebaseAuthInvalidCredentialsException
    "ERROR_INVALID_PHONE_NUMBER" to R.string.error_invalid_phone_number,
    "ERROR_SESSION_EXPIRED" to R.string.error_session_expired,
    "ERROR_INVALID_VERIFICATION_ID" to R.string.error_invalid_verification_id,
    "ERROR_INVALID_VERIFICATION_CODE" to R.string.error_invalid_verification_code,
    "ERROR_MISSING_VERIFICATION_CODE" to R.string.error_missing_verification_code,
    "ERROR_MISSING_PHONE_NUMBER" to R.string.error_missing_phone_number,
    "ERROR_EMAIL_CHANGE_NEEDS_VERIFICATION" to R.string.error_email_change_needs_verification,
    "ERROR_UNSUPPORTED_FIRST_FACTOR" to R.string.error_unsupported_first_factor,
    "ERROR_MAXIMUM_SECOND_FACTOR_COUNT_EXCEEDED" to R.string.error_maximum_second_factor_count_exceeded,
    "ERROR_SECOND_FACTOR_ALREADY_ENROLLED" to R.string.error_second_factor_already_enrolled,
    "ERROR_SECOND_FACTOR_REQUIRED" to R.string.error_second_factor_required,
    "ERROR_MULTI_FACTOR_INFO_NOT_FOUND" to R.string.error_multi_factor_info_not_found,
    "ERROR_INVALID_MULTI_FACTOR_SESSION" to R.string.error_invalid_multi_factor_session,
    "ERROR_MISSING_MULTI_FACTOR_INFO" to R.string.error_missing_multi_factor_info,
    "ERROR_MISSING_MULTI_FACTOR_SESSION" to R.string.error_missing_multi_factor_session,
    "ERROR_REQUIRES_RECENT_LOGIN" to R.string.error_requires_recent_login,
    "ERROR_APP_NOT_AUTHORIZED" to null,
    "ERROR_QUOTA_EXCEEDED" to null,
    "ERROR_APP_NOT_VERIFIED" to null,
    // FirebaseAuthInvalidUserException
    "ERROR_USER_DISABLED" to R.string.error_user_disabled,
    "ERROR_USER_NOT_FOUND" to R.string.error_user_not_found,
    "ERROR_USER_TOKEN_EXPIRED" to R.string.error_token_expired,
    "ERROR_INVALID_USER_TOKEN" to R.string.error_invalid_user_token,
    // FirebaseAuthException
    "ERROR_EMAIL_ALREADY_IN_USE" to R.string.error_email_already_in_use,
    "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" to R.string.error_account_exists_with_different_credential,
    "ERROR_CREDENTIAL_ALREADY_IN_USE" to R.string.error_credential_already_in_use,
)