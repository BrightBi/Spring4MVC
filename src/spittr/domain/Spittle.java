package spittr.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Spittle {

	private Long id;
	@NotNull
	@Size(min=3, max=20)
	private String message;
	private Date time;
	private Double latitude;
	public Spittle() {
	}
	public Spittle(Long id, @NotNull @Size(min = 3, max = 20) String message, Date time, Double latitude) {
		this.id = id;
		this.message = message;
		this.time = time;
		this.latitude = latitude;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	@Override
	public String toString() {
		return "Spittle [id=" + id + ", message=" + message + ", time=" + time + ", latitude=" + latitude + "]";
	}
}
