package com.crawler.abroad.mapdata.entity;

import java.io.Serializable;

/**
 * @author wb-hyg545156 经纬度坐标
 */
public class Coordination implements Serializable {
	private static final long serialVersionUID = 1L;

	private Coordination preCoordination;// 前一个点坐标
	private Double longitude;// 经度
	private Double latitude;// 维度
	private CoordinationConfig coordinationConfig;// 当前坐标点配置信息


	public Coordination getPreCoordination() {
		return preCoordination;
	}

	public void setPreCoordination(Coordination preCoordination) {
		this.preCoordination = preCoordination;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public CoordinationConfig getCoordinationConfig() {
		return coordinationConfig;
	}

	public void setCoordinationConfig(CoordinationConfig coordinationConfig) {
		this.coordinationConfig = coordinationConfig;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordinationConfig == null) ? 0 : coordinationConfig.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((preCoordination == null) ? 0 : preCoordination.hashCode());
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
		Coordination other = (Coordination) obj;
		if (coordinationConfig == null) {
			if (other.coordinationConfig != null)
				return false;
		} else if (!coordinationConfig.equals(other.coordinationConfig))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (preCoordination == null) {
			if (other.preCoordination != null)
				return false;
		} else if (!preCoordination.equals(other.preCoordination))
			return false;
		return true;
	}

	public Coordination(Coordination preCoordination, Double longitude, Double latitude,
			CoordinationConfig coordinationConfig) {
		super();
		this.preCoordination = preCoordination;
		this.longitude = longitude;
		this.latitude = latitude;
		this.coordinationConfig = coordinationConfig;
	}
	
	public Coordination(Double longitude, Double latitude, CoordinationConfig coordinationConfig) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.coordinationConfig = coordinationConfig;
	}
	
	public Coordination(Double longitude, Double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public Coordination() {
		super();
	}
	
	@Override
	public String toString() {
		return "[" + longitude + ", " + latitude + "]";
	}

}