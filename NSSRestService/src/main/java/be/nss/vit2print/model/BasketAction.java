package be.nss.vit2print.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "mail", "approve", "export", "disapprove", "delete",
		"excel", "download", "upload", "print", "move", "copy" })
public class BasketAction {

	@JsonProperty("mail")
	private String mail;

	@JsonProperty("approve")
	private String approve;

	@JsonProperty("export")
	private String export;

	@JsonProperty("disapprove")
	private String disapprove;

	@JsonProperty("delete")
	private String delete;

	@JsonProperty("excel")
	private String excel;

	@JsonProperty("download")
	private String download;

	@JsonProperty("upload")
	private String upload;

	@JsonProperty("print")
	private String print;

	@JsonProperty("move")
	private String move;

	@JsonProperty("copy")
	private String copy;

	@JsonIgnore
	private String basketActionValue;

	public BasketAction() {
		// Default Constructor
	}

	public BasketAction(String mail, String approve, String export,
			String disapprove, String delete, String excel, String download,
			String upload, String print, String move, String copy) {
		super();
		this.mail = mail;
		this.approve = approve;
		this.export = export;
		this.disapprove = disapprove;
		this.delete = delete;
		this.excel = excel;
		this.download = download;
		this.upload = upload;
		this.print = print;
		this.move = move;
		this.copy = copy;
	}

	public BasketAction(String basketActionValue) {
		super();
		this.mail = basketActionValue;
		this.approve = basketActionValue;
		this.export = basketActionValue;
		this.disapprove = basketActionValue;
		this.delete = basketActionValue;
		this.excel = basketActionValue;
		this.download = basketActionValue;
		this.upload = basketActionValue;
		this.print = basketActionValue;
		this.move = basketActionValue;
		this.copy = basketActionValue;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getApprove() {
		return approve;
	}

	public void setApprove(String approve) {
		this.approve = approve;
	}

	public String getExport() {
		return export;
	}

	public void setExport(String export) {
		this.export = export;
	}

	public String getDisapprove() {
		return disapprove;
	}

	public void setDisapprove(String disapprove) {
		this.disapprove = disapprove;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	public String getExcel() {
		return excel;
	}

	public void setExcel(String excel) {
		this.excel = excel;
	}

	public String getDownload() {
		return download;
	}

	public void setDownload(String download) {
		this.download = download;
	}

	public String getUpload() {
		return upload;
	}

	public void setUpload(String upload) {
		this.upload = upload;
	}

	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public String getMove() {
		return move;
	}

	public void setMove(String move) {
		this.move = move;
	}

	public String getCopy() {
		return copy;
	}

	public void setCopy(String copy) {
		this.copy = copy;
	}

	public String getBasketActionValue() {
		return basketActionValue;
	}

	public void setBasketActionValue(String basketActionValue) {
		this.basketActionValue = basketActionValue;
	}
}