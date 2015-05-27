package be.nss.vit2print.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "mail", "export", "approve", "lightbox", "zoom",
		"disapprove", "editinfo", "excel", "download", "similarsearch",
		"print", "delete", "basket", "viewinfo", "move", "copy" })
public class AssetAction {

	@JsonProperty("mail")
	private String mail;

	@JsonProperty("export")
	private String export;

	@JsonProperty("approve")
	private String approve;

	@JsonProperty("lightbox")
	private String lightbox;

	@JsonProperty("zoom")
	private String zoom;

	@JsonProperty("disapprove")
	private String disapprove;

	@JsonProperty("editinfo")
	private String editinfo;

	@JsonProperty("excel")
	private String excel;

	@JsonProperty("download")
	private String download;

	@JsonProperty("similarsearch")
	private String similarsearch;

	@JsonProperty("print")
	private String print;

	@JsonProperty("delete")
	private String delete;

	@JsonProperty("basket")
	private String basket;

	@JsonProperty("viewinfo")
	private String viewinfo;

	@JsonProperty("move")
	private String move;

	@JsonProperty("copy")
	private String copy;

	public AssetAction() {
		// Default constructor
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getExport() {
		return export;
	}

	public void setExport(String export) {
		this.export = export;
	}

	public String getApprove() {
		return approve;
	}

	public void setApprove(String approve) {
		this.approve = approve;
	}

	public String getLightbox() {
		return lightbox;
	}

	public void setLightbox(String lightbox) {
		this.lightbox = lightbox;
	}

	public String getZoom() {
		return zoom;
	}

	public void setZoom(String zoom) {
		this.zoom = zoom;
	}

	public String getDisapprove() {
		return disapprove;
	}

	public void setDisapprove(String disapprove) {
		this.disapprove = disapprove;
	}

	public String getEditinfo() {
		return editinfo;
	}

	public void setEditinfo(String editinfo) {
		this.editinfo = editinfo;
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

	public String getSimilarsearch() {
		return similarsearch;
	}

	public void setSimilarsearch(String similarsearch) {
		this.similarsearch = similarsearch;
	}

	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	public String getBasket() {
		return basket;
	}

	public void setBasket(String basket) {
		this.basket = basket;
	}

	public String getViewinfo() {
		return viewinfo;
	}

	public void setViewinfo(String viewinfo) {
		this.viewinfo = viewinfo;
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
}