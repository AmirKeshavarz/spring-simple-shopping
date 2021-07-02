package ir.keshavarzreza.simpleshopping.domain.dto;


public class ApiPagination {
	private int page;
	private int size;
	private boolean hasPreviousPage;
	private boolean hasNextPage;
	private boolean hasFirstPage;
	private boolean hasLastPage;
	private int offset;
	private String inputSortString;

	{
		this.page = 0;
		this.size = 10;

	}

	public ApiPagination(int page, int size, String inputSortString) {
		this.page = page;
		this.size = size;
		this.inputSortString = inputSortString;
	}

	public ApiPagination() {
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public boolean isHasFirstPage() {
		return hasFirstPage;
	}

	public void setHasFirstPage(boolean hasFirstPage) {
		this.hasFirstPage = hasFirstPage;
	}

	public boolean isHasLastPage() {
		return hasLastPage;
	}

	public void setHasLastPage(boolean hasLastPage) {
		this.hasLastPage = hasLastPage;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getInputSortString() {
		return inputSortString;
	}

	public void setInputSortString(String inputSortString) {
		this.inputSortString = inputSortString;
	}
}
