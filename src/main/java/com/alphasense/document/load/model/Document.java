package com.alphasense.document.load.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity(name = "FAILED_DOCUMENTS")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String documentId;

    private String documentName;

    private String companyName;

    private String reporterName;

    private Date createdDate;

    private String createdBy;

    private boolean isDuplicate;

    public boolean isDuplicate() {
        return isDuplicate;
    }

    public void setDuplicate(boolean duplicate) {
        isDuplicate = duplicate;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document that = (Document) o;
        return id.equals(that.id) && documentId.equals(that.documentId) && documentName.equals(that.documentName) && Objects.equals(companyName, that.companyName) && Objects.equals(reporterName, that.reporterName) && Objects.equals(createdDate, that.createdDate) && Objects.equals(createdBy, that.createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documentId, documentName, companyName, reporterName, createdDate, createdBy);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Document{");
        sb.append("id=").append(id);
        sb.append(", documentId='").append(documentId).append('\'');
        sb.append(", documentName='").append(documentName).append('\'');
        sb.append(", companyName='").append(companyName).append('\'');
        sb.append(", reporterName='").append(reporterName).append('\'');
        sb.append(", createdDate=").append(createdDate);
        sb.append(", createdBy='").append(createdBy).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
