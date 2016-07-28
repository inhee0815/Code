package project2;

public class HtmlDataVO {
	private String u_title;
	private String u_writer;
	private String u_date;

	public HtmlDataVO(String u_title, String u_writer, String u_date) {
		super();
		this.u_title = u_title;
		this.u_writer = u_writer;
		this.u_date = u_date;
	}

	public void setU_title(String u_title) {
		this.u_title = u_title;
	}

	public void setU_writer(String u_writer) {
		this.u_writer = u_writer;
	}

	public void setU_date(String u_date) {
		this.u_date = u_date;
	}
}
