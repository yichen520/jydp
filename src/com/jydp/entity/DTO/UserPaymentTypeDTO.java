package com.jydp.entity.DTO;

import java.sql.Timestamp;

/**
 * 用户收款方式
 * @author hz
 */
public class UserPaymentTypeDTO {
	private String paymentAccount; //收款账号
	private String bankName; //收款银行
	private String bankBranch; //收款支行
	private String paymentName; //收款人姓名
	private String paymentPhone; //收款人手机号
	private String paymentImage; //二维码地址

	/**
	 * 获取 收款账号
	 * @return paymentAccount
	 */
	public String getPaymentAccount() {
		return paymentAccount;
	}

	/**
	 * 设置 收款账号
	 * @param paymentAccount 收款账号
	 */
	public void setPaymentAccount(String paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	/**
	 * 获取 收款银行
	 * @return bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * 设置 收款银行
	 * @param bankName 收款银行
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * 获取 收款支行
	 * @return bankBranch
	 */
	public String getBankBranch() {
		return bankBranch;
	}

	/**
	 * 设置 收款支行
	 * @param bankBranch 收款支行
	 */
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	/**
	 * 获取 收款人姓名
	 * @return paymentName
	 */
	public String getPaymentName() {
		return paymentName;
	}

	/**
	 * 设置 收款人姓名
	 * @param paymentName 收款人姓名
	 */
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	/**
	 * 获取 收款人手机号
	 * @return paymentPhone
	 */
	public String getPaymentPhone() {
		return paymentPhone;
	}

	/**
	 * 设置 收款人手机号
	 * @param paymentPhone 收款人手机号
	 */
	public void setPaymentPhone(String paymentPhone) {
		this.paymentPhone = paymentPhone;
	}

	/**
	 * 获取 二维码地址
	 * @return paymentImage
	 */
	public String getPaymentImage() {
		return paymentImage;
	}

	/**
	 * 设置 二维码地址
	 * @param paymentImage 二维码地址
	 */
	public void setPaymentImage(String paymentImage) {
		this.paymentImage = paymentImage;
	}
}
