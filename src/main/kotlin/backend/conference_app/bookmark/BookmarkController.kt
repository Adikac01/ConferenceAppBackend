package backend.conference_app.bookmark

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bookmarks")
class BookmarkController(private val bookmarkService: BookmarkService) {

	@GetMapping("/{userId}")
	fun getBookmarks(@PathVariable userId: Long): List<Bookmark> =
		bookmarkService.getBookmarksForUser(userId)

	@PostMapping
	fun addBookmark(@RequestParam userId: Long, @RequestParam eventId: Long): Bookmark =
		bookmarkService.addBookmark(userId, eventId)

	@DeleteMapping
	fun removeBookmark(@RequestParam userId: Long, @RequestParam eventId: Long) {
		bookmarkService.removeBookmark(userId, eventId)
	}
}