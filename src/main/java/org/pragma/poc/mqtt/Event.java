/**
 * 
 */
package org.pragma.poc.mqtt;

/**
 * @author Davide Antelmo
 *
 */
public class Event {

	public String title;
	public String start;
	public String end;
	private Boolean generated = false;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	
	
	public Boolean getGenerated() {
		return generated;
	}
	public void setGenerated(Boolean generated) {
		this.generated = generated;
	}
	@Override
	public String toString() {
		return "Event [title=" + title + ", start=" + start + ", end=" + end
				+ "]";
	}
	
	
}
