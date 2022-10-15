sealed class DomainException(
    override val message: String,
    override val cause: Throwable? = null,
) : Exception() {
    class NoSuchElementException(
        override val message: String,
    ) : DomainException(message)

    class ValidationException(
        override val message: String,
    ) : DomainException(message)

    class SystemException(
        override val message: String,
        override val cause: Throwable?,
    ) : DomainException(message, cause)
}
