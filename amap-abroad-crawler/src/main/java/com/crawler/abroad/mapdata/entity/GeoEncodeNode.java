package com.crawler.abroad.mapdata.entity;

import java.io.Serializable;

/**地理编码实体类，用于获取location tree对应的结构化地址
 * Created By giser on 2019-04-22
 */
public class GeoEncodeNode implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String  countryName;//当前节点所属国家
	private String  countryCode;//
	private String  addressID;//地址编号
	private String  nodeName;//当前节点名，地址名称
	private String  nodeParentID;//上级节点
	private String  nodePath;//当前节点全路径
	private Integer nodeLevel;//节点级别
	private String  activeStatus;//active状态
	private String	createdAt;//创建日期
	private String  updatedAt;//更新日期
	private String  deletedAt;//删除日期
	
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getAddressID() {
		return addressID;
	}
	public void setAddressID(String addressID) {
		this.addressID = addressID;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeParentID() {
		return nodeParentID;
	}
	public void setNodeParentID(String nodeParentID) {
		this.nodeParentID = nodeParentID;
	}
	public String getNodePath() {
		return nodePath;
	}
	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}
	public Integer getNodeLevel() {
		return nodeLevel;
	}
	public void setNodeLevel(Integer nodeLevel) {
		this.nodeLevel = nodeLevel;
	}
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(String deletedAt) {
		this.deletedAt = deletedAt;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activeStatus == null) ? 0 : activeStatus.hashCode());
		result = prime * result + ((addressID == null) ? 0 : addressID.hashCode());
		result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
		result = prime * result + ((countryName == null) ? 0 : countryName.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((deletedAt == null) ? 0 : deletedAt.hashCode());
		result = prime * result + ((nodeLevel == null) ? 0 : nodeLevel.hashCode());
		result = prime * result + ((nodeName == null) ? 0 : nodeName.hashCode());
		result = prime * result + ((nodeParentID == null) ? 0 : nodeParentID.hashCode());
		result = prime * result + ((nodePath == null) ? 0 : nodePath.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
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
		GeoEncodeNode other = (GeoEncodeNode) obj;
		if (activeStatus == null) {
			if (other.activeStatus != null)
				return false;
		} else if (!activeStatus.equals(other.activeStatus))
			return false;
		if (addressID == null) {
			if (other.addressID != null)
				return false;
		} else if (!addressID.equals(other.addressID))
			return false;
		if (countryCode == null) {
			if (other.countryCode != null)
				return false;
		} else if (!countryCode.equals(other.countryCode))
			return false;
		if (countryName == null) {
			if (other.countryName != null)
				return false;
		} else if (!countryName.equals(other.countryName))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (deletedAt == null) {
			if (other.deletedAt != null)
				return false;
		} else if (!deletedAt.equals(other.deletedAt))
			return false;
		if (nodeLevel == null) {
			if (other.nodeLevel != null)
				return false;
		} else if (!nodeLevel.equals(other.nodeLevel))
			return false;
		if (nodeName == null) {
			if (other.nodeName != null)
				return false;
		} else if (!nodeName.equals(other.nodeName))
			return false;
		if (nodeParentID == null) {
			if (other.nodeParentID != null)
				return false;
		} else if (!nodeParentID.equals(other.nodeParentID))
			return false;
		if (nodePath == null) {
			if (other.nodePath != null)
				return false;
		} else if (!nodePath.equals(other.nodePath))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}
	public GeoEncodeNode() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GeoEncodeNode(String countryName, String countryCode, String addressID, String nodeName, String nodeParentID,
			String nodePath, Integer nodeLevel, String activeStatus, String createdAt, String updatedAt,
			String deletedAt) {
		super();
		this.countryName = countryName;
		this.countryCode = countryCode;
		this.addressID = addressID;
		this.nodeName = nodeName;
		this.nodeParentID = nodeParentID;
		this.nodePath = nodePath;
		this.nodeLevel = nodeLevel;
		this.activeStatus = activeStatus;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}
	@Override
	public String toString() {
		return "GeoEncodeNode [countryName=" + countryName + ", countryCode=" + countryCode + ", addressID=" + addressID
				+ ", nodeName=" + nodeName + ", nodeParentID=" + nodeParentID + ", nodePath=" + nodePath
				+ ", nodeLevel=" + nodeLevel + ", activeStatus=" + activeStatus + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt + "]";
	}
	
}
