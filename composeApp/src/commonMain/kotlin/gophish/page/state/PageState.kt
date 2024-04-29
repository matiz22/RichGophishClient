package gophish.page.state

data class PageState(
    val currentPage: Int = 1,
    val maxPage: Int
) {
    companion object {
        const val itemsPerPage = 10
        fun calculatePages(totalItems: Int): Int {
            val totalPages = totalItems / itemsPerPage
            val remainingItems = totalItems % itemsPerPage
            return if (remainingItems > 0) {
                totalPages + 1
            } else {
                totalPages
            }
        }
    }
}
