package cn.sunline.compare.entity;

import java.io.Serializable;

/**
 * position_flag
 * @author 
 */
public class PositionFlag implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 记录回放的位置 对应请求记录表的 id
     */
    private Integer position;

    /**
     * 业务标记
     */
    private String flag;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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
        PositionFlag other = (PositionFlag) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPosition() == null ? other.getPosition() == null : this.getPosition().equals(other.getPosition()))
            && (this.getFlag() == null ? other.getFlag() == null : this.getFlag().equals(other.getFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPosition() == null) ? 0 : getPosition().hashCode());
        result = prime * result + ((getFlag() == null) ? 0 : getFlag().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", position=").append(position);
        sb.append(", flag=").append(flag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}