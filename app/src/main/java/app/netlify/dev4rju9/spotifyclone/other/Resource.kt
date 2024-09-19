package app.netlify.dev4rju9.spotifyclone.other

data class Resource<out T> (val status: Status, val data: T? = null, val message: String? = null) {

    companion object {

        fun <T> success (data: T?) = Resource(Status.SUCCESS, data)

        fun <T> loading (data: T? = null) = Resource(Status.LOADING, data)

        fun <T> error (message: String, data: T?) = Resource(Status.ERROR, data, message)

    }

}

enum class Status {
    SUCCESS,
    LOADING,
    ERROR
}