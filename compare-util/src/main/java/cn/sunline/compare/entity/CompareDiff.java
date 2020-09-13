package cn.sunline.compare.entity;

import java.io.Serializable;

/**
 * compare_diff
 * @author 
 */
public class CompareDiff implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 交易名
     */
    private String transName;

    /**
     * 流水号
     */
    private String flowNum;

    /**
     * 新系统key
     */
    private String newKey;

    /**
     * 新系统值
     */
    private String newValue;

    /**
     * 旧系统key
     */
    private String oldKey;

    /**
     * 旧系统值
     */
    private String oldValue;

    /**
     * 差异类型
     */
    private String diffType;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public String getFlowNum() {
        return flowNum;
    }

    public void setFlowNum(String flowNum) {
        this.flowNum = flowNum;
    }

    public String getNewKey() {
        return newKey;
    }

    public void setNewKey(String newKey) {
        this.newKey = newKey;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getOldKey() {
        return oldKey;
    }

    public void setOldKey(String oldKey) {
        this.oldKey = oldKey;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getDiffType() {
        return diffType;
    }

    public void setDiffType(String diffType) {
        this.diffType = diffType;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CompareDiff other = (CompareDiff) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTransName() == null ? other.getTransName() == null : this.getTransName().equals(other.getTransName()))
            && (this.getFlowNum() == null ? other.getFlowNum() == null : this.getFlowNum().equals(other.getFlowNum()))
            && (this.getNewKey() == null ? other.getNewKey() == null : this.getNewKey().equals(other.getNewKey()))
            && (this.getNewValue() == null ? other.getNewValue() == null : this.getNewValue().equals(other.getNewValue()))
            && (this.getOldKey() == null ? other.getOldKey() == null : this.getOldKey().equals(other.getOldKey()))
            && (this.getOldValue() == null ? other.getOldValue() == null : this.getOldValue().equals(other.getOldValue()))
            && (this.getDiffType() == null ? other.getDiffType() == null : this.getDiffType().equals(other.getDiffType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTransName() == null) ? 0 : getTransName().hashCode());
        result = prime * result + ((getFlowNum() == null) ? 0 : getFlowNum().hashCode());
        result = prime * result + ((getNewKey() == null) ? 0 : getNewKey().hashCode());
        result = prime * result + ((getNewValue() == null) ? 0 : getNewValue().hashCode());
        result = prime * result + ((getOldKey() == null) ? 0 : getOldKey().hashCode());
        result = prime * result + ((getOldValue() == null) ? 0 : getOldValue().hashCode());
        result = prime * result + ((getDiffType() == null) ? 0 : getDiffType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", transName=").append(transName);
        sb.append(", flowNum=").append(flowNum);
        sb.append(", newKey=").append(newKey);
        sb.append(", newValue=").append(newValue);
        sb.append(", oldKey=").append(oldKey);
        sb.append(", oldValue=").append(oldValue);
        sb.append(", diffType=").append(diffType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}