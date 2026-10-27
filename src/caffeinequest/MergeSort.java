package caffeinequest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Stable, recursive merge sort: O(n log n) time and O(n) auxiliary space. */
public final class MergeSort {
	private MergeSort() {
	}

	/** Sort in place and return the number of comparator calls. No library sort. */
	public static <T> long sort(List<T> items, Comparator<? super T> order) {
		List<T> buffer = new ArrayList<>(items);
		return sortRange(items, buffer, order, 0, items.size());
	}

	// Each range is half-open: [start, end).
	private static <T> long sortRange(List<T> items, List<T> buffer, Comparator<? super T> order, int start, int end) {
		if (end - start < 2) {
			return 0;
		}
		int middle = start + (end - start) / 2;
		long comparisons = sortRange(items, buffer, order, start, middle)
				+ sortRange(items, buffer, order, middle, end);

		return comparisons + merge(items, buffer, order, start, middle, end);
	}

	/** Both halves are already sorted: [start, middle) and [middle, end). */
	private static <T> long merge(List<T> items, List<T> buffer, Comparator<? super T> order, int start, int middle,
			int end) {
		int left = start;
		int right = middle;
		int destination = start;
		long comparisons = 0;

		// TODO A: While BOTH halves have items, compare their current elements.
		// Count each comparator call. Copy the earlier item into buffer.
		// On equal keys, take the left item to preserve stability.
		// Advance the selected source index and the destination index.
		while (left < middle && right < end) {
			comparisons++;
			// returns -1, 0, or 1 depending on which number is smaller, equal, or bigger
			if (order.compare(items.get(left), items.get(right)) <= 0) {
				// merge the items on the left
				buffer.set(destination, items.get(left));
				left++; // increment left index to the next item
			} else {
				// merge the right item because the right item is less than the left item
				// means the 'if' statement was false
				buffer.set(destination, items.get(right));
				right++;
			}
			// getting ready to insert the next element
			destination++;
		}

		// TODO B: Copy any remaining elements from the left half into buffer.
		while (left < middle) {
			buffer.set(destination, items.get(left));
			left++; // increment left index to the next item
			destination++;
		}

		// TODO C: Copy any remaining elements from the right half into buffer.
		while (right < end) {
			buffer.set(destination, items.get(right));
			right++; // increment left index to the next item
			destination++;
		}

		// TODO D: Copy buffer[start..end) back into items[start..end).
		
		for (int index = start; index < end; index++) {
			items.set(index, buffer.get(index));
		}
		
		
		// Return the number of comparisons performed by THIS merge.
		return comparisons;
		// Replace the exception below with your implementation and return.
		// throw new UnsupportedOperationException("Complete TODO A-D in MergeSort.merge().");

	}
}
