package model;

import java.time.LocalDateTime;

public class CommitMessageByData {
	private String msg;
	private LocalDateTime data;
	
	public CommitMessageByData(String msg, LocalDateTime data) {
		this.msg = msg;
		this.data = data;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	
	
}
