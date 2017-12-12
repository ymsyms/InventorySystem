package sg.edu.iss.inventory.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderDetailId  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Column(name="orderId")
	 int orderId;
	@Column(name="partNo")
	 String partNo;
	public OrderDetailId() {
		super();
	}
	public OrderDetailId(int orderId, String partNo) {
		this.orderId = orderId;
		this.partNo = partNo;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + orderId;
		result = prime * result + ((partNo == null) ? 0 : partNo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDetailId other = (OrderDetailId) obj;
		if (orderId != other.orderId)
			return false;
		if (partNo == null) {
			if (other.partNo != null)
				return false;
		} else if (!partNo.equals(other.partNo))
			return false;
		return true;
	}
	
	
}
