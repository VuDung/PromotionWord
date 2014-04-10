package covisoft.android.promotionword.model;

import java.io.Serializable;

public class CardInfomation implements Serializable {
	public final static String CARDINFORMATION = "cardInformation";
	private String cardNumberOrEmail;
	private String dateOfBirth;
	private String responseCode;
	private String member_id;
	private String spentAmount;
	private String savedAmount;
	private String redeemedPoints;
	private String remainingPoints;
	
	
	public CardInfomation(String responseCode, String spentAmount,
			String savedAmount, String redeemedPoints, String remainingPoints) {
		super();
		this.responseCode = responseCode;
		this.spentAmount = spentAmount;
		this.savedAmount = savedAmount;
		this.redeemedPoints = redeemedPoints;
		this.remainingPoints = remainingPoints;
	}
	public CardInfomation(String cardNumberOrEmail, String dateOfBirth,
			String responseCode, String member_id, String spentAmount,
			String savedAmount, String redeemedPoints, String remainingPoints) {
		super();
		this.cardNumberOrEmail = cardNumberOrEmail;
		this.dateOfBirth = dateOfBirth;
		this.responseCode = responseCode;
		this.member_id = member_id;
		this.spentAmount = spentAmount;
		this.savedAmount = savedAmount;
		this.redeemedPoints = redeemedPoints;
		this.remainingPoints = remainingPoints;
	}
	public CardInfomation(String cardNumberOrEmail, String dateOfBirth,
			String responseCode, String spentAmount, String savedAmount,
			String redeemedPoints, String remainingPoints) {
		super();
		this.cardNumberOrEmail = cardNumberOrEmail;
		this.dateOfBirth = dateOfBirth;
		this.responseCode = responseCode;
		this.spentAmount = spentAmount;
		this.savedAmount = savedAmount;
		this.redeemedPoints = redeemedPoints;
		this.remainingPoints = remainingPoints;
	}
	public String getCardNumberOrEmail() {
		return cardNumberOrEmail;
	}
	public void setCardNumberOrEmail(String cardNumberOrEmail) {
		this.cardNumberOrEmail = cardNumberOrEmail;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getSpentAmount() {
		return spentAmount;
	}
	public void setSpentAmount(String spentAmount) {
		this.spentAmount = spentAmount;
	}
	public String getSavedAmount() {
		return savedAmount;
	}
	public void setSavedAmount(String savedAmount) {
		this.savedAmount = savedAmount;
	}
	public String getRedeemedPoints() {
		return redeemedPoints;
	}
	public void setRedeemedPoints(String redeemedPoints) {
		this.redeemedPoints = redeemedPoints;
	}
	public String getRemainingPoints() {
		return remainingPoints;
	}
	public void setRemainingPoints(String remainingPoints) {
		this.remainingPoints = remainingPoints;
	}
	
	
}
