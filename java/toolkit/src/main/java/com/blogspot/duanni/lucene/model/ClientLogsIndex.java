package com.blogspot.duanni.lucene.model;

/**
 * 客户端日志文件索引信息.
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class ClientLogsIndex {
	public static final int STATE_UNINDEX = 0;
	public static final int STATE_INDEXED = 1;
	private int id;
	private String file;
	private String backupFile;
	private int state;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getBackupFile() {
		return backupFile;
	}

	public void setBackupFile(String backupFile) {
		this.backupFile = backupFile;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
