package tech.medo.shared.security

interface CurrentUserProvider {
    fun currentUser(): CurrentUser
}
