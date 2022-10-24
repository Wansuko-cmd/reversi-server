sealed class DomainException(
    override val message: String,
    override val cause: Throwable? = null,
) : Exception() {
    // リクエストの方法を間違えている時の例外
    class RequestValidationException(
        override val message: String,
    ) : DomainException(message)

    // DBに指定されたカラムがない時の例外
    class NoSuchElementException(
        override val message: String,
    ) : DomainException(message)

    // 駒が置ける場所ではない時の例外
    class NotPlaceableCoordinateException(
        override val message: String,
    ) : DomainException(message)

    // 既にゲームが終了している時の例外
    class FinishedGameException(
        override val message: String,
    ) : DomainException(message)

    // 対戦相手待ちのユーザーが足りていない時の例外
    class NoMoreWaitMattingUserException(
        override val message: String,
    ) : DomainException(message)

    // サーバー側の予期しない例外
    class SystemException(
        override val message: String,
        override val cause: Throwable?,
    ) : DomainException(message, cause)
}
