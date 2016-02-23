package save;

public class Source {
private String title;
public Source(String title, String content) {
	super();
	this.title = title;
	this.content = content;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getContent() {
	return content;
}
public Source() {
	super();
}
public void setContent(String content) {
	this.content = content;
}
private String content;
}
