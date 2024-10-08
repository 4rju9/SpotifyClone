package app.netlify.dev4rju9.spotifyclone.other

class Event<out T> (private val data: T) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled (): T? {
        return if (hasBeenHandled) null
        else {
            hasBeenHandled = true
            data
        }
    }

    fun peekContent () = data

}