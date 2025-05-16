package backend.conference_app.bookmark

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/protected/bookmarks")
class BookmarkController(private val bookmarkService: BookmarkService) {

	@GetMapping("/{userId}")
	fun getBookmarks(@PathVariable userId: Long): ResponseEntity<List<BookmarkResponse>> =
		ResponseEntity.ok(bookmarkService.getBookmarksForUser(userId))

	@PostMapping
	fun addBookmark(
		@RequestParam userId: Long,
		@RequestParam eventId: Long
	): ResponseEntity<BookmarkResponse> {
		val created = bookmarkService.addBookmark(userId, eventId)
		return ResponseEntity.status(201).body(created)
	}

	@DeleteMapping
	fun removeBookmark(@RequestParam userId: Long, @RequestParam eventId: Long): ResponseEntity<Void> {
		bookmarkService.removeBookmark(userId, eventId)
		return ResponseEntity.noContent().build()
	}
}