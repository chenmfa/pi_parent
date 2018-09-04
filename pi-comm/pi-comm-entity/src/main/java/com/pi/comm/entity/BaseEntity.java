/**
 * 
 */
package com.pi.comm.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author haodch
 * @ 2018年3月13日
 */
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 4639808960801837880L;

    private Long              id;
    private Date              createDate;
    private Date              updateDate;
    private Long              version          = 300L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
