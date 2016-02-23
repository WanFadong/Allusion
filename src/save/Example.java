package save;

public class Example {
	private String poet;
	private String title;
	private String verse;

	public Example() {
		super();
	}

	public Example(String poet, String title, String verse) {
		super();
		this.poet = poet;
		this.title = title;
		this.verse = verse;
	}

	public String getPoet() {
		return poet;
	}

	public void setPoet(String poet) {
		this.poet = poet;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVerse() {
		return verse;
	}

	public void setVerse(String verse) {
		this.verse = verse;
	}

}
