package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ORDERS database table.
 * 
 */
@Entity
@Table(name="ORDERS", schema="GUITARSHOP1")
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ORDER_ID")
	private long orderId;

	@Column(name="BILLING_ADDRESS_ID")
	private BigDecimal billingAddressId;

	@Column(name="CARD_EXPIRES")
	private String cardExpires;

	@Column(name="CARD_NUMBER")
	private String cardNumber;

	@Column(name="CARD_TYPE")
	private String cardType;

	@Temporal(TemporalType.DATE)
	@Column(name="ORDER_DATE")
	private Date orderDate;

	@Column(name="SHIP_ADDRESS_ID")
	private BigDecimal shipAddressId;

	@Column(name="SHIP_AMOUNT")
	private BigDecimal shipAmount;

	@Temporal(TemporalType.DATE)
	@Column(name="SHIP_DATE")
	private Date shipDate;

	@Column(name="TAX_AMOUNT")
	private BigDecimal taxAmount;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="CUSTOMER_ID")
	private Customer customer;

	//bi-directional many-to-one association to OrderItem
	@OneToMany(mappedBy="order")
	private List<OrderItem> orderItems;

	public Order() {
	}

	public long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getBillingAddressId() {
		return this.billingAddressId;
	}

	public void setBillingAddressId(BigDecimal billingAddressId) {
		this.billingAddressId = billingAddressId;
	}

	public String getCardExpires() {
		return this.cardExpires;
	}

	public void setCardExpires(String cardExpires) {
		this.cardExpires = cardExpires;
	}

	public String getCardNumber() {
		return this.cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public BigDecimal getShipAddressId() {
		return this.shipAddressId;
	}

	public void setShipAddressId(BigDecimal shipAddressId) {
		this.shipAddressId = shipAddressId;
	}

	public BigDecimal getShipAmount() {
		return this.shipAmount;
	}

	public void setShipAmount(BigDecimal shipAmount) {
		this.shipAmount = shipAmount;
	}

	public Date getShipDate() {
		return this.shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	public BigDecimal getTaxAmount() {
		return this.taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<OrderItem> getOrderItems() {
		return this.orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public OrderItem addOrderItem(OrderItem orderItem) {
		getOrderItems().add(orderItem);
		orderItem.setOrder(this);

		return orderItem;
	}

	public OrderItem removeOrderItem(OrderItem orderItem) {
		getOrderItems().remove(orderItem);
		orderItem.setOrder(null);

		return orderItem;
	}

}