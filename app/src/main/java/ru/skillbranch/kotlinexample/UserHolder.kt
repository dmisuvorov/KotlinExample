package ru.skillbranch.kotlinexample

object UserHolder {
    private val map = mutableMapOf<String, User>()

    fun registerUser(
        fullName: String,
        email: String,
        password: String
    ): User {
        return User.makeUser(fullName, email = email, password = password)
            .also { user ->
                if (map.containsKey(user.login)) throw IllegalArgumentException("A user with this email already exists")
                else map[user.login] = user
            }
    }

    fun registerUserByPhone(fullName: String, rawPhone: String): User {
        return User.makeUser(fullName, phone = rawPhone)
            .also { user ->
                if (map.containsKey(user.login)) throw IllegalArgumentException("A user with this phone already exists")
                else map[user.login] = user
            }
    }

    fun loginUser(login: String, password: String): String? {
        return map[login.trim()]?.let { user ->
            if (user.checkPassword(password)) return user.userInfo
            else null
        }
    }

    fun requestAccessCode(login: String) {
        map[login.trim()]?.let { user ->
            user.accessCode ?: return
            val generateAccessCode = user.generateAccessCode()
            user.changePassword(user.accessCode!!, generateAccessCode)
            user.accessCode = generateAccessCode
        }
    }

    fun importUsers(list: List<String>): List<User> {

    }
}