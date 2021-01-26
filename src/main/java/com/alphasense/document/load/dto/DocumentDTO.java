package com.alphasense.document.load.dto;

import java.util.Objects;

public class DocumentDTO {

    private String documentId;

    private String documentName;

    private String companyName;

    private String reporterName;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DocumentDTO{");
        sb.append("documentId=").append(documentId);
        sb.append(", documentName='").append(documentName).append('\'');
        sb.append(", companyName='").append(companyName).append('\'');
        sb.append(", reporterName='").append(reporterName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentDTO documentDTO = (DocumentDTO) o;
        return documentId.equals(documentDTO.documentId) && documentName.equals(documentDTO.documentName) && Objects.equals(companyName, documentDTO.companyName) && Objects.equals(reporterName, documentDTO.reporterName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId, documentName, companyName, reporterName);
    }
}
