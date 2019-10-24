package data.pdf;

import java.io.Serializable;

public class Column implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float width;
	private String name;

	public Column(float width, String name) {
		this.width = width;
		this.name = name;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
