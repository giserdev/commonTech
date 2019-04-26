package com.crawler.abroad.mapdata.entity;

import java.io.Serializable;

import com.crawler.abroad.mapdata.utils.AMapFileUtils;
import com.crawler.abroad.mapdata.utils.PropertyUtils;

/**
 * 坐标配置信息
 * 
 * @author wb-hyg545156
 *
 */
public class CoordinationConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	private String target;// 目标国
	private Double minStep;// 最小步长
	private Double maxStep;// 最大步长
	private Double stepRate;// 比率
	private Integer stepScale;// 尺度换算
	private Double startCoordLng;// 起始经度
	private Double startCoordLat;// 起始纬度
	private Double currentStep;// 当前步长
	private Integer quota;// 提取次数
	private String preContent;//
	private Double initMaxStep;// 初始最大步长
	private Double initMinStep;// 初始最小步长
	private Double maxLng;
	private Double minLng;
	private Double maxLat;
	private Double minLat;
	private Double constLatStep;
	private String rawDataStorageFile;// 数据存储文件
	private String uniqDataStorageFile;// 数据存储文件

	public CoordinationConfig() {
		this.target = PropertyUtils.getStringByKey("TARGET");
		this.minStep = Double.valueOf(PropertyUtils.getStringByKey("minStep", "10"));
		this.maxStep = Double.valueOf(PropertyUtils.getStringByKey("maxStep", "500"));
		this.stepRate = Double.valueOf(PropertyUtils.getStringByKey("stepRate", "0.0117"));
		this.stepScale = Integer.valueOf(PropertyUtils.getStringByKey("stepScale"));
		this.startCoordLng = Double.valueOf(PropertyUtils.getStringByKey("startCoordLng"));
		this.startCoordLat = Double.valueOf(PropertyUtils.getStringByKey("startCoordLat"));
		this.currentStep = Double.valueOf(PropertyUtils.getStringByKey("minStep", "10"));
		this.quota = Integer.valueOf(PropertyUtils.getStringByKey("QUOTA", "100000"));
		this.preContent = PropertyUtils.getStringByKey("preContent", "100000");
		this.initMaxStep = maxStep * stepRate / stepScale;// 最大步长
		this.initMinStep = minStep * stepRate / stepScale;// 最小步长
		this.maxLng = Double.valueOf(PropertyUtils.getStringByKey("maxLng", "104.104204"));
		this.minLng = Double.valueOf(PropertyUtils.getStringByKey("minLng", "103.543163"));
		this.maxLat = Double.valueOf(PropertyUtils.getStringByKey("maxLat", "1.156753"));
		this.minLat = Double.valueOf(PropertyUtils.getStringByKey("minLat", "1.466964"));
		this.constLatStep = Double.valueOf(PropertyUtils.getStringByKey("constLatStep", "0.0001"));
		this.rawDataStorageFile = AMapFileUtils.getResultFile("./rawData_Mapbox_",this.target,".log");
		this.uniqDataStorageFile = AMapFileUtils.getResultFile("./uniqData_Mapbox_", this.target, ".log");
	}

	public String getRawDataStorageFile() {
		return rawDataStorageFile;
	}

	public void setRawDataStorageFile(String rawDataStorageFile) {
		this.rawDataStorageFile = rawDataStorageFile;
	}

	public String getUniqDataStorageFile() {
		return uniqDataStorageFile;
	}

	public void setUniqDataStorageFile(String uniqDataStorageFile) {
		this.uniqDataStorageFile = uniqDataStorageFile;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Double getMinStep() {
		return minStep;
	}

	public void setMinStep(Double minStep) {
		this.minStep = minStep;
	}

	public Double getMaxStep() {
		return maxStep;
	}

	public void setMaxStep(Double maxStep) {
		this.maxStep = maxStep;
	}

	public Double getStepRate() {
		return stepRate;
	}

	public void setStepRate(Double stepRate) {
		this.stepRate = stepRate;
	}

	public Integer getStepScale() {
		return stepScale;
	}

	public void setStepScale(Integer stepScale) {
		this.stepScale = stepScale;
	}

	public Double getStartCoordLng() {
		return startCoordLng;
	}

	public void setStartCoordLng(Double startCoordLng) {
		this.startCoordLng = startCoordLng;
	}

	public Double getStartCoordLat() {
		return startCoordLat;
	}

	public void setStartCoordLat(Double startCoordLat) {
		this.startCoordLat = startCoordLat;
	}

	public Double getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(Double currentStep) {
		this.currentStep = currentStep;
	}

	public Integer getQuota() {
		return quota;
	}

	public void setQuota(Integer quota) {
		this.quota = quota;
	}

	public String getPreContent() {
		return preContent;
	}

	public void setPreContent(String preContent) {
		this.preContent = preContent;
	}

	public Double getInitMaxStep() {
		return initMaxStep;
	}

	public void setInitMaxStep(Double initMaxStep) {
		this.initMaxStep = initMaxStep;
	}

	public Double getInitMinStep() {
		return initMinStep;
	}

	public void setInitMinStep(Double initMinStep) {
		this.initMinStep = initMinStep;
	}

	public Double getMaxLng() {
		return maxLng;
	}

	public void setMaxLng(Double maxLng) {
		this.maxLng = maxLng;
	}

	public Double getMinLng() {
		return minLng;
	}

	public void setMinLng(Double minLng) {
		this.minLng = minLng;
	}

	public Double getMaxLat() {
		return maxLat;
	}

	public void setMaxLat(Double maxLat) {
		this.maxLat = maxLat;
	}

	public Double getMinLat() {
		return minLat;
	}

	public void setMinLat(Double minLat) {
		this.minLat = minLat;
	}

	public Double getConstLatStep() {
		return constLatStep;
	}

	public void setConstLatStep(Double constLatStep) {
		this.constLatStep = constLatStep;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((constLatStep == null) ? 0 : constLatStep.hashCode());
		result = prime * result + ((currentStep == null) ? 0 : currentStep.hashCode());
		result = prime * result + ((initMaxStep == null) ? 0 : initMaxStep.hashCode());
		result = prime * result + ((initMinStep == null) ? 0 : initMinStep.hashCode());
		result = prime * result + ((maxLat == null) ? 0 : maxLat.hashCode());
		result = prime * result + ((maxLng == null) ? 0 : maxLng.hashCode());
		result = prime * result + ((maxStep == null) ? 0 : maxStep.hashCode());
		result = prime * result + ((minLat == null) ? 0 : minLat.hashCode());
		result = prime * result + ((minLng == null) ? 0 : minLng.hashCode());
		result = prime * result + ((minStep == null) ? 0 : minStep.hashCode());
		result = prime * result + ((preContent == null) ? 0 : preContent.hashCode());
		result = prime * result + ((quota == null) ? 0 : quota.hashCode());
		result = prime * result + ((rawDataStorageFile == null) ? 0 : rawDataStorageFile.hashCode());
		result = prime * result + ((startCoordLat == null) ? 0 : startCoordLat.hashCode());
		result = prime * result + ((startCoordLng == null) ? 0 : startCoordLng.hashCode());
		result = prime * result + ((stepRate == null) ? 0 : stepRate.hashCode());
		result = prime * result + ((stepScale == null) ? 0 : stepScale.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		result = prime * result + ((uniqDataStorageFile == null) ? 0 : uniqDataStorageFile.hashCode());
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
		CoordinationConfig other = (CoordinationConfig) obj;
		if (constLatStep == null) {
			if (other.constLatStep != null)
				return false;
		} else if (!constLatStep.equals(other.constLatStep))
			return false;
		if (currentStep == null) {
			if (other.currentStep != null)
				return false;
		} else if (!currentStep.equals(other.currentStep))
			return false;
		if (initMaxStep == null) {
			if (other.initMaxStep != null)
				return false;
		} else if (!initMaxStep.equals(other.initMaxStep))
			return false;
		if (initMinStep == null) {
			if (other.initMinStep != null)
				return false;
		} else if (!initMinStep.equals(other.initMinStep))
			return false;
		if (maxLat == null) {
			if (other.maxLat != null)
				return false;
		} else if (!maxLat.equals(other.maxLat))
			return false;
		if (maxLng == null) {
			if (other.maxLng != null)
				return false;
		} else if (!maxLng.equals(other.maxLng))
			return false;
		if (maxStep == null) {
			if (other.maxStep != null)
				return false;
		} else if (!maxStep.equals(other.maxStep))
			return false;
		if (minLat == null) {
			if (other.minLat != null)
				return false;
		} else if (!minLat.equals(other.minLat))
			return false;
		if (minLng == null) {
			if (other.minLng != null)
				return false;
		} else if (!minLng.equals(other.minLng))
			return false;
		if (minStep == null) {
			if (other.minStep != null)
				return false;
		} else if (!minStep.equals(other.minStep))
			return false;
		if (preContent == null) {
			if (other.preContent != null)
				return false;
		} else if (!preContent.equals(other.preContent))
			return false;
		if (quota == null) {
			if (other.quota != null)
				return false;
		} else if (!quota.equals(other.quota))
			return false;
		if (rawDataStorageFile == null) {
			if (other.rawDataStorageFile != null)
				return false;
		} else if (!rawDataStorageFile.equals(other.rawDataStorageFile))
			return false;
		if (startCoordLat == null) {
			if (other.startCoordLat != null)
				return false;
		} else if (!startCoordLat.equals(other.startCoordLat))
			return false;
		if (startCoordLng == null) {
			if (other.startCoordLng != null)
				return false;
		} else if (!startCoordLng.equals(other.startCoordLng))
			return false;
		if (stepRate == null) {
			if (other.stepRate != null)
				return false;
		} else if (!stepRate.equals(other.stepRate))
			return false;
		if (stepScale == null) {
			if (other.stepScale != null)
				return false;
		} else if (!stepScale.equals(other.stepScale))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		if (uniqDataStorageFile == null) {
			if (other.uniqDataStorageFile != null)
				return false;
		} else if (!uniqDataStorageFile.equals(other.uniqDataStorageFile))
			return false;
		return true;
	}

	public CoordinationConfig(String target, Double minStep, Double maxStep, Double stepRate, Integer stepScale,
			Double startCoordLng, Double startCoordLat, Double currentStep, Integer quota, String preContent,
			Double initMaxStep, Double initMinStep, Double maxLng, Double minLng, Double maxLat, Double minLat,
			Double constLatStep, String rawDataStorageFile, String uniqDataStorageFile) {
		super();
		this.target = target;
		this.minStep = minStep;
		this.maxStep = maxStep;
		this.stepRate = stepRate;
		this.stepScale = stepScale;
		this.startCoordLng = startCoordLng;
		this.startCoordLat = startCoordLat;
		this.currentStep = currentStep;
		this.quota = quota;
		this.preContent = preContent;
		this.initMaxStep = initMaxStep;
		this.initMinStep = initMinStep;
		this.maxLng = maxLng;
		this.minLng = minLng;
		this.maxLat = maxLat;
		this.minLat = minLat;
		this.constLatStep = constLatStep;
		this.rawDataStorageFile = rawDataStorageFile;
		this.uniqDataStorageFile = uniqDataStorageFile;
	}

}
