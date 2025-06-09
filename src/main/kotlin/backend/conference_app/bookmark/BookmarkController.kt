package backend.conference_app.bookmark

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class BookmarkController(private val bookmarkService: BookmarkService) {

	@GetMapping("/protected/bookmarks/{userId}")
	fun getBookmarks(@PathVariable userId: Long): ResponseEntity<List<BookmarkResponse>> =
		ResponseEntity.ok(bookmarkService.getBookmarksForUser(userId))

	@PostMapping("/protected/bookmarks")
	fun addBookmark(
		@RequestParam userId: Long,
		@RequestParam eventId: Long
	): ResponseEntity<BookmarkResponse> {
		val created = bookmarkService.addBookmark(userId, eventId)
		return ResponseEntity.status(201).body(created)
	}

	@DeleteMapping("/protected/bookmarks")
	fun removeBookmark(@RequestParam userId: Long, @RequestParam eventId: Long): ResponseEntity<Void> {
		bookmarkService.removeBookmark(userId, eventId)
		return ResponseEntity.noContent().build()
	}

	@GetMapping("/restricted/bookmarks/count")
	fun countBookmarks(): ResponseEntity<List<EventBookmarkResponse>> =
		ResponseEntity.ok(bookmarkService.countBookmarksForEvents())
}