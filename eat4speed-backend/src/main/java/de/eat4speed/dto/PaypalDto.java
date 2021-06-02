package de.eat4speed.dto;

public class PaypalDto {

    public String approveLink;
    public String captureLink;
    public String updateLink;
    public String selfLink;
    public String orderId;
    public int bestellungsId;

    public PaypalDto() {
    }

    public String getApproveLink() {
        return approveLink;
    }

    public void setApproveLink(String approveLink) {
        this.approveLink = approveLink;
    }

    public String getCaptureLink() {
        return captureLink;
    }

    public void setCaptureLink(String captureLink) {
        this.captureLink = captureLink;
    }

    public String getUpdateLink() {
        return updateLink;
    }

    public void setUpdateLink(String updateLink) {
        this.updateLink = updateLink;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getBestellungId() {
        return bestellungsId;
    }

    public void setBestellungId(int bestellungId) {
        this.bestellungsId = bestellungId;
    }
}
